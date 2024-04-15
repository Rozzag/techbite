package com.example.demo;

import com.example.demo.supportclasses.Booking;
import com.example.demo.supportclasses.Staff;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    @FXML
    private TextArea bookingAdditionalInfo;

    @FXML
    private DatePicker bookingDate;

    @FXML
    private TextField bookingName;

    @FXML
    private TextField bookingPhnNumber;

    @FXML
    private ComboBox<String> bookingTime;

    @FXML
    private ComboBox<Integer> bookingDiners;

    @FXML
    private ToggleButton bookingWheelchair;

    @FXML
    private Button submitButton;

    @FXML
    private AnchorPane tablesLayout;

    @FXML
    private Label errorLabel;

    @FXML
    private TableView<Booking> bookingTables;

    @FXML
    private TableColumn<Booking, Integer> bookingIDs;

    @FXML
    private TableColumn<Booking, String> bookingNames;

    @FXML
    private TableColumn<Booking, String> bookingPhones;

    @FXML
    private TableColumn<Booking, String> bookingTimes;

    @FXML
    private TableColumn<Booking, Integer> bookingGuests;

    @FXML
    private DatePicker searchBookingDate;

    @FXML
    private Button findBookingButton;

    @FXML
    private Button cancelBookingButton;

    private Booking selectedBooking;

    private boolean wheel = false;
    private ObservableList<Booking> bookings;


    @FXML
    void handleWheelToggle(ActionEvent event) {
        wheel = !wheel;
    }

    @FXML
    private void handleSubmitButton() throws SQLException {
        int wheelchair = wheel ? 1 : 0;
        boolean allEmpty = bookingDate.getValue() == null ||
                bookingName.getText().isEmpty() ||
                bookingPhnNumber.getText().isEmpty() ||
                bookingTime.getValue() == null ||
                bookingDiners.getValue() == null;


        if(!(allEmpty)) {
            System.out.println();

            Booking.addBooking(bookingName.getText(),
                    bookingPhnNumber.getText(),
                    bookingDate.getValue() + " " + bookingTime.getValue(),
                    bookingDiners.getValue(),
                    bookingAdditionalInfo.getText(),
                    wheelchair,
            );

            bookingName.clear();
            bookingPhnNumber.clear();
            bookingDate.cancelEdit();
            bookingDiners.cancelEdit();
            bookingAdditionalInfo.clear();
        } else{
            errorLabel.setText("Not all fields have been filled!");

            PauseTransition disappearingMessage = new PauseTransition(Duration.seconds(1));
            disappearingMessage.setOnFinished(event -> errorLabel.setText(""));
            disappearingMessage.play();
        }

        bookingTables.getItems().clear();
        seeBookingTable();
    }

    @FXML
    private void handleFindBookingButton() throws SQLException {
        bookingTables.getItems().clear();
        seeBookingTable();
    }

    @FXML
    private void handleCancelBookingButton() throws SQLException {
        bookings.remove(selectedBooking);
        selectedBooking.cancelBooking();

        bookingTables.getItems().clear();
        seeBookingTable();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> numberGuests = FXCollections.observableArrayList();

        for (int i = 1; i < 7; i++){
            numberGuests.add(i);
        }
        bookingDiners.setItems(numberGuests);


        ObservableList<String> times = FXCollections.observableArrayList();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.of(0, 0); // Start at midnight
        LocalTime endTime = LocalTime.of(23, 30); // End at 11:30 PM

        while (startTime.isBefore(endTime)) {
            times.add(startTime.format(timeFormatter));
            startTime = startTime.plusMinutes(30); // Add 30 minutes
        }
        bookingTime.setItems(times);

        // If nothing entered, setting date to today
        searchBookingDate.setValue(LocalDate.now());

        try {
            seeBookingTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void seeBookingTable() throws SQLException {
        bookings = FXCollections.observableArrayList(Booking.getBookings(searchBookingDate.getValue().toString()));

        //writing rows
        bookingIDs.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("BookingID"));
        bookingNames.setCellValueFactory(new PropertyValueFactory<Booking, String>("name"));
        bookingPhones.setCellValueFactory(new PropertyValueFactory<Booking, String>("customerPhoneNum"));
        bookingTimes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDt().split(" ")[1]));
        bookingGuests.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("numOfDiners"));

        bookingTables.setItems(bookings);

        bookingTables.setOnMouseClicked(event -> {
            int selectedIndex = bookingTables.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                selectedBooking = bookingTables.getItems().get(selectedIndex);
            }
        });
    }
}
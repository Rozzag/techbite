package com.example.demo;

import com.example.demo.supportclasses.Booking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    private boolean wheel = false;

    @FXML
    void handleWheelToggle(ActionEvent event) {
        wheel = !wheel;
    }

    @FXML
    private void handleSubmitButton() throws SQLException {
        int wheelchair = wheel ? 1 : 0;
        boolean allEmpty = bookingDate.getValue() == null &&
                bookingName.getText().isEmpty() &&
                bookingPhnNumber.getText().isEmpty() &&
                bookingTime.getValue() == null &&
                bookingDiners.getValue() == null;


        if(!(allEmpty)) {
            System.out.println();

            Booking.addBooking(bookingName.getText(),
                    bookingPhnNumber.getText(),
                    bookingDate.getValue() + " " + bookingTime.getValue(),
                    bookingDiners.getValue(),
                    bookingAdditionalInfo.getText(),
                    wheelchair
            );

            bookingName.clear();
            bookingPhnNumber.clear();
            bookingDate.cancelEdit();
            bookingDiners.cancelEdit();
            bookingAdditionalInfo.clear();
        } else{

        }
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
    }
}

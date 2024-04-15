package com.example.demo;

import com.example.demo.connectivity.Database;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class BookingController  {

    @FXML
    private ComboBox<String> table1;

    @FXML
    private ComboBox<String> table2;

    @FXML
    private ComboBox<String> table3;

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

    public void initialize() {
        // User can't set table number until number of guests has been decided
        table1.setDisable(true);
        table3.setDisable(true);
        table2.setDisable(true);

        bookingDiners.valueProperty().addListener((observable, oldValue, newValue) -> {

            // clear entries for previous ones on the fields
            table3.getItems().clear();
            table2.getItems().clear();
            table1.getItems().clear();
        });

        ObservableList<Integer> numberGuests = FXCollections.observableArrayList();

        // numbers of customers that can be accomodated
        for (int i = 1; i < 7; i++){
            numberGuests.add(i);
        }
        bookingDiners.setItems(numberGuests);

        ObservableList<String> times = FXCollections.observableArrayList();

        // Code generated with Gemini to prompt the user with times with 30 mins intervals
        // PROMPT: Write me an algorithm in Java that returns times "HH:mm" format with 30 mins interval
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


    // Toggles the value of the toggle button
    @FXML
    void handleWheelToggle(ActionEvent event) {
        wheel = !wheel;
    }

    @FXML
    private void handleSubmitButton() throws SQLException {
        int wheelchair = wheel ? 1 : 0;
        // Checks if any required field is missing
        boolean allEmpty = bookingDate.getValue() == null ||
                bookingName.getText().isEmpty() ||
                bookingPhnNumber.getText().isEmpty() ||
                bookingTime.getValue() == null ||
                bookingDiners.getValue() == null;

        // If no required field missing creates a new booking
        // otherwise, allow the user to change those fields
        if(!(allEmpty)) {

            int guests = bookingDiners.getValue();

            List<Integer> tableIds = new LinkedList<>();
            tableIds.add(Integer.valueOf(table1.getValue()));
            if (guests > 2) {
                tableIds.add(Integer.valueOf(table2.getValue()));
            }
            if (guests > 4) {
                tableIds.add(Integer.valueOf(table3.getValue()));
            }
            for (int id : tableIds) {
                System.out.println(id);
            }

            if (bookingAdditionalInfo.getText().isEmpty()) {
                bookingAdditionalInfo.setText("");
            }


            Booking.addBooking(bookingName.getText(),
                    bookingPhnNumber.getText(),
                    bookingDate.getValue() + " " + bookingTime.getValue(),
                    bookingDiners.getValue(),
                    bookingAdditionalInfo.getText(),
                    wheelchair, tableIds
            );

            // Clears required fields after booking has been made
            bookingName.clear();
            bookingPhnNumber.clear();
            bookingDate.cancelEdit();
            bookingDiners.cancelEdit();
            bookingAdditionalInfo.clear();
            bookingAdditionalInfo.clear();;
            table3.setValue(null);
            table2.setValue(null);
            table1.setValue(null);
        } else{
            // lets user know about error
            errorLabel.setText("Not all fields have been filled!");

            PauseTransition disappearingMessage = new PauseTransition(Duration.seconds(1));
            disappearingMessage.setOnFinished(event -> errorLabel.setText(""));
            disappearingMessage.play();
        }

        // updates the table view of the bookings
        bookingTables.getItems().clear();
        seeBookingTable();
    }

    @FXML
    private void handleFindBookingButton() throws SQLException {
        // updates the table view of the bookings
        bookingTables.getItems().clear();
        seeBookingTable();
    }

    @FXML
    private void handleCancelBookingButton() throws SQLException {
        // removes the booking from the db and updates the table
        bookings.remove(selectedBooking);
        selectedBooking.cancelBooking();

        bookingTables.getItems().clear();
        seeBookingTable();
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

        // gets the selected booking object
        bookingTables.setOnMouseClicked(event -> {
            int selectedIndex = bookingTables.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                selectedBooking = bookingTables.getItems().get(selectedIndex);
            }
        });
    }

    public void handleDiners(){
        // get the number of guests first
        int numGuests = bookingDiners.getValue();

        // enable the initial combo box to be used
        table1.setDisable(false);
        // use the database to get the empty tables
        try {
            // we can only add the tables that are not booked
            Database db = new Database();
            String bookedTables = "select table_id from Grouping as g join Booking as b on b.booking_id = g.booking_id WHERE CURRENT_TIMESTAMP between time(b.booking_date_time) and date_add(time(b.booking_date_time), INTERVAL 30 minute) and date(b.booking_date_time)=CURRENT_DATE;";
            ArrayList<ArrayList<String>> returnedValues = db.selectValues(bookedTables);

            // store the tables which haven't been booked
            ArrayList<String> emptyTables = new ArrayList<>();
            for (int i = 1; i <= 15; i++) {
                emptyTables.add(String.valueOf(i));
            }

            // remove the tables which have already been booked
            for (ArrayList<String> table : returnedValues) {
                emptyTables.remove(table.get(0));
            }

            for (String tables : emptyTables) {
                table1.getItems().add(tables);
            }
            db.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void handleTable1() {
        // get the tables for the initial field and the guest field
        int numGuests = bookingDiners.getValue();
        String initialTable = table1.getValue();

        if (numGuests > 2) {
            table2.setDisable(false);

            // do the same as before but check the additional input for the first combo box
            // use the database to get the empty tables
            try {
                // we can only add the tables that are not booked
                Database db = new Database();
                String bookedTables = "select table_id from Grouping as g join Booking as b on b.booking_id = g.booking_id WHERE CURRENT_TIMESTAMP between time(b.booking_date_time) and date_add(time(b.booking_date_time), INTERVAL 30 minute) and date(b.booking_date_time)=CURRENT_DATE;";
                ArrayList<ArrayList<String>> returnedValues = db.selectValues(bookedTables);

                // store the tables which haven't been booked
                ArrayList<String> emptyTables = new ArrayList<>();
                for (int i = 1; i <= 15; i++) {
                    emptyTables.add(String.valueOf(i));
                }


                // remove the tables which have already been booked
                for (ArrayList<String> table : returnedValues) {
                    emptyTables.remove(table.get(0));
                }

                for (String tables : emptyTables) {
                    System.out.println(numGuests);
                    // don't add the table which has been added as the initial table
                    if (!tables.equals(initialTable)) {
                        table2.getItems().add(tables);
                    }
                }
                db.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void handleTable2() {
        // for the second do the same thing but check that the number of guests is above 4
        int numGuests = bookingDiners.getValue();
        String initialTable = table1.getValue();
        String secondTable = table2.getValue();

        if (numGuests > 4) {
            table3.setDisable(false);

            // do the same as before but check the additional input for the first combo box
            // use the database to get the empty tables
            try {
                // we can only add the tables that are not booked
                Database db = new Database();
                String bookedTables = "select table_id from Grouping as g join Booking as b on b.booking_id = g.booking_id WHERE CURRENT_TIMESTAMP between time(b.booking_date_time) and date_add(time(b.booking_date_time), INTERVAL 30 minute) and date(b.booking_date_time)=CURRENT_DATE;";
                ArrayList<ArrayList<String>> returnedValues = db.selectValues(bookedTables);

                // store the tables which haven't been booked
                ArrayList<String> emptyTables = new ArrayList<>();
                for (int i = 1; i <= 15; i++) {
                    emptyTables.add(String.valueOf(i));
                }

                // remove the tables which have already been booked
                for (ArrayList<String> table : returnedValues) {
                    emptyTables.remove(table.get(0));
                }

                for (String tables : emptyTables) {
                    if (!tables.equals(initialTable) && !tables.equals(secondTable)) {
                        table3.getItems().add(tables);
                    }
                }
                db.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void handleTable3() {

    }

}
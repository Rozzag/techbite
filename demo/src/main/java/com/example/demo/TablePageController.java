package com.example.demo;

import com.example.demo.connectivity.Database;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class TablePageController {

    @FXML
    private TextArea additionalInfoField;

    @FXML
    private Button allocateButton;

    @FXML
    private ComboBox<String> guestsField;

    @FXML
    private ComboBox<String> initialTableField;

    @FXML
    private ComboBox<String> secondTableField;

    @FXML
    private ComboBox<String> thirdTableField;


    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumberField;

    public void initialize() {
        // disable the allocate button
        allocateButton.setDisable(true);

        // set the combo box for the guests
        guestsField.getItems().addAll(FXCollections.observableArrayList("1","2", "3", "4", "5", "6"));

        // disable all the other combo boxes
        secondTableField.setDisable(true);
        thirdTableField.setDisable(true);
        initialTableField.setDisable(true);

        guestsField.valueProperty().addListener((observable, oldValue, newValue) -> {

            // clear entries for previous ones on the fields
            secondTableField.getItems().clear();
            thirdTableField.getItems().clear();
            initialTableField.getItems().clear();
        });

}

    public void checkGuests() {
        // get the number of guests first
        int numGuests = Integer.parseInt(guestsField.getValue());

            // enable the initial combo box to be used
            initialTableField.setDisable(false);
            // use the database to get the empty tables
            try {
                // we can only add the tables that are not booked
                Database db = new Database();
                String bookedTables = "select table_id from Orders as o join Booking as b on b.booking_id = o.booking_id WHERE CURRENT_TIMESTAMP between time(b.booking_date_time) and date_add(time(b.booking_date_time), INTERVAL 30 minute) and date(b.booking_date_time)=CURRENT_DATE;";
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
                    initialTableField.getItems().add(tables);
                }
                db.close();

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
    }

    public void checkInitial(ActionEvent actionEvent) {
        // get the tables for the initial field and the guest field
        int numGuests = Integer.parseInt(guestsField.getValue());
        String initialTable = initialTableField.getValue();

        if (numGuests > 2 && numGuests <= 4) {
            secondTableField.setDisable(false);

            // do the same as before but check the additional input for the first combo box
            // use the database to get the empty tables
            try {
                // we can only add the tables that are not booked
                Database db = new Database();
                String bookedTables = "select table_id from Orders as o join Booking as b on b.booking_id = o.booking_id WHERE CURRENT_TIMESTAMP between time(b.booking_date_time) and date_add(time(b.booking_date_time), INTERVAL 30 minute) and date(b.booking_date_time)=CURRENT_DATE;";
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
                        secondTableField.getItems().add(tables);
                    }
                }
                db.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    public void checkSecond(ActionEvent actionEvent) {

        // for the second do the same thing but check that the number of guests is above 4
        int numGuests = Integer.parseInt(guestsField.getValue());
        String initialTable = initialTableField.getValue();
        String secondTable = secondTableField.getValue();

        if (numGuests > 4) {
            thirdTableField.setDisable(false);

            // do the same as before but check the additional input for the first combo box
            // use the database to get the empty tables
            try {
                // we can only add the tables that are not booked
                Database db = new Database();
                String bookedTables = "select table_id from Orders as o join Booking as b on b.booking_id = o.booking_id WHERE CURRENT_TIMESTAMP between time(b.booking_date_time) and date_add(time(b.booking_date_time), INTERVAL 30 minute) and date(b.booking_date_time)=CURRENT_DATE;";
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
                        thirdTableField.getItems().add(tables);
                    }
                }
                db.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void checkThird(ActionEvent actionEvent) {
    }
}

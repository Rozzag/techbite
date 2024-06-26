package com.example.demo;

import com.example.demo.connectivity.Database;
import com.example.demo.supportclasses.Booking;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// controls the functionality for the table page which shows the table layout as wells
// as the form for handling walk-in bookings
public class TablePageController {

    TableController tableController;

    @FXML
    private RadioButton wheelChair;

    @FXML
    private Label allocateSuccess;
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

    private List<Integer> tables = new LinkedList<>();

    public void setTableController(TableController tableController) {
        this.tableController = tableController;
    }

    public String getGuestNumber() {
        return guestsField.getValue();
    }

    public List<Integer> returnTables() {
        return tables;
    }



    // initializes the buttons and combo boxes to disabled if necessary.
    // also clears the user input so that the user doesn't submit the previous input
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

    // the button sends the information to the database and also sends the notification
    // to the actual page to reset the layout and assign the table as booked
     public void allocationButton() throws SQLException {

        allocateSuccess.setText("Customer has been seated!");
         PauseTransition disappearingMessage = new PauseTransition(Duration.seconds(1));
         disappearingMessage.setOnFinished(event -> allocateSuccess.setText(""));
         disappearingMessage.play();

         // insert information to the table
         Database db = new Database("in2033t01_a", "CtYS1azKU-8");

         String name = nameField.getText();
         String phoneNumber = phoneNumberField.getText();
         int guests = Integer.parseInt(guestsField.getValue());
         String additionalInfo = additionalInfoField.getText();
         int wheelChairNeeded = 0;
         if (wheelChair.isSelected()) {
             wheelChairNeeded = 1;
         }

         LocalDateTime now = LocalDateTime.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         String formattedDateTime = now.format(formatter);

         List<Integer> tableIds = new LinkedList<>();
             tableIds.add(Integer.valueOf(initialTableField.getValue()));
             tables.add(Integer.valueOf(initialTableField.getValue()));
         if (guests > 2) {
             tableIds.add(Integer.valueOf(secondTableField.getValue()));
             tables.add(Integer.valueOf(secondTableField.getValue()));
         }
         if (guests > 4) {
             tableIds.add(Integer.valueOf(thirdTableField.getValue()));
             tables.add(Integer.valueOf(thirdTableField.getValue()));
         }

         if (additionalInfoField.getText().isEmpty()) {
             additionalInfo = "";
         }

         Booking.addBooking(name, phoneNumber, formattedDateTime,guests,additionalInfo, wheelChairNeeded, tables);
         tableController.assignTableAvailability();
         System.out.println("assign the table availability again");

         nameField.clear();
         phoneNumberField.clear();
         guestsField.setValue(null);
         initialTableField.setValue(null);
         secondTableField.setValue(null);
         thirdTableField.setValue(null);
         additionalInfoField.clear();


     }



     // the method checks when the user inputted a value for the number of guests
    // to then handle whether the disabled combo boxes are enabled or not
    public void checkGuests() {
        // get the number of guests first
        int numGuests = Integer.parseInt(guestsField.getValue());

            // enable the initial combo box to be used
            initialTableField.setDisable(false);
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
                    initialTableField.getItems().add(tables);
                }
                db.close();

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
    }

    // this checks if the second combo box is enabled or not and also ensures that the
    // options for the second one doesn't overlap with the database and the option for
    // the first inputted box
    public void checkInitial(ActionEvent actionEvent) {
        // get the tables for the initial field and the guest field
        int numGuests = Integer.parseInt(guestsField.getValue());
        String initialTable = initialTableField.getValue();

        if (numGuests > 2) {
            secondTableField.setDisable(false);

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
                        secondTableField.getItems().add(tables);
                    }
                }
                db.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } else {
            buttonEnabled();
        }
    }

    // does the same functionality but for the third combo box.
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
                        thirdTableField.getItems().add(tables);
                    }
                }
                db.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } else {
            buttonEnabled();
        }
    }

    // after the third combo box has been filled in, the button is enabled to allow
    // the user to submit the information
    public void checkThird(ActionEvent actionEvent) {
        buttonEnabled();
     }

     // checks with all the components if the button should be enabled or not
     public void buttonEnabled() {
         boolean disable = nameField.getText().isEmpty() || nameField.getText().trim().isEmpty()
                 || phoneNumberField.getText().isEmpty() || phoneNumberField.getText().trim().isEmpty();
         allocateButton.setDisable(disable);
     }

     // from the perspective of the text field for the name, it checks whether the button
    // can be enabled
     public void fullFormName() {
        // check if the other forms are filled in
         boolean disable = false;

         int numGuests = Integer.parseInt(guestsField.getValue());
         switch(numGuests) {
             case 1, 2 -> {
                 disable = phoneNumberField.getText().isEmpty() || phoneNumberField.getText().trim().isEmpty()
                 || initialTableField.getValue().isEmpty() || initialTableField.getValue().trim().isEmpty();
             }
             case 3 , 4 -> {
                 disable = phoneNumberField.getText().isEmpty() || phoneNumberField.getText().trim().isEmpty()
                         || initialTableField.getValue().isEmpty() || initialTableField.getValue().trim().isEmpty()
                         || secondTableField.getValue().isEmpty() || secondTableField.getValue().trim().isEmpty();
             }
             case 5,6 ->{ disable = phoneNumberField.getText().isEmpty() || phoneNumberField.getText().trim().isEmpty()
                     || initialTableField.getValue().isEmpty() || initialTableField.getValue().trim().isEmpty()
                     || secondTableField.getValue().isEmpty() || secondTableField.getValue().trim().isEmpty()
                     || thirdTableField.getValue().isEmpty() || thirdTableField.getValue().trim().isEmpty();
         }}
         allocateButton.setDisable(disable);
     }

     // same feature as the above method but for the phone number field
     public void fullFormNumber() {
         // check if the other forms are filled in
         boolean disable = false;

         int numGuests = Integer.parseInt(guestsField.getValue());
         switch(numGuests) {
             case 1, 2 -> {
                 disable = nameField.getText().isEmpty() || nameField.getText().trim().isEmpty()
                         || initialTableField.getValue().isEmpty() || initialTableField.getValue().trim().isEmpty();
             }
             case 3 , 4 -> {
                 disable = nameField.getText().isEmpty() || nameField.getText().trim().isEmpty()
                         || initialTableField.getValue().isEmpty() || initialTableField.getValue().trim().isEmpty()
                         || secondTableField.getValue().isEmpty() || secondTableField.getValue().trim().isEmpty();
             }
             case 5,6 -> {
                 disable = nameField.getText().isEmpty() || nameField.getText().trim().isEmpty()
                     || initialTableField.getValue().isEmpty() || initialTableField.getValue().trim().isEmpty()
                     || secondTableField.getValue().isEmpty() || secondTableField.getValue().trim().isEmpty()
                     || thirdTableField.getValue().isEmpty() || thirdTableField.getValue().trim().isEmpty();
             }

         }
         allocateButton.setDisable(disable);

     }

     @FXML
    public void needWheelChair(ActionEvent actionEvent) {
    }
}

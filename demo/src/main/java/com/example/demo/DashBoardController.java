package com.example.demo;

import com.example.demo.connectivity.Database;
import com.example.demo.supportclasses.Booking;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class DashBoardController {

    @FXML
    private LineChart lineChart;

    @FXML
    private Button submitButton;

    @FXML
    private Button resetButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button left;

    @FXML
    private Button right;

    @FXML
    private Label time;

    @FXML
    private Label bookingId;

    @FXML
    private Label guests;

    @FXML
    private Label wheelchair;

    @FXML
    private Label requirement;
    @FXML
    private Label revenueStr;
    @FXML
    private Label numBookings;

    @FXML
    private Label errorMessage;

    private LocalDate date;

    // we will use the string to get the bookings and swipe through them
    private ArrayList<Booking> bookings = new ArrayList<>();

    // the index of the booking being swiped
    private int index = 0;


    public void initialize() throws SQLException {
        LocalDate today = LocalDate.now(); // Get today's date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);
        date = LocalDate.parse(formattedDate, formatter);
        datePicker.setValue(date);

        // for each selected date we will extract the time and the total number of guests during that period
        Database database = new Database();

        //Get total revenue for the day
        ArrayList<ArrayList<String>> query4revenue = database.selectValues(String.format(
                "SELECT SUM(Payment.total_amount) " +
                "FROM Payment, Orders, Booking " +
                        "WHERE Payment.order_id = Orders.order_id AND " +
                        "Orders.booking_id = Booking.booking_id AND " +
                        "DATE(Booking.booking_date_time) = '%s'".formatted(formattedDate)));

        // Displaying total revenue on the screen
        revenueStr.setText("Today's revenue: £" + query4revenue.get(0).get(0));

        // Get total number of bookings
        ArrayList<ArrayList<String>> query4bookings = database.selectValues(String.format("SELECT COUNT(booking_date_time) FROM Booking WHERE DATE(booking_date_time) = '%s'".formatted(formattedDate)));

        // Displaying total count of number of bookings for the day
        numBookings.setText("Bookings today: " + query4bookings.get(0).get(0));

        String query = "SELECT TIME(booking_date_time) AS booking_time, SUM(number_of_guests) AS total_guests FROM Booking WHERE DATE(booking_date_time) = '%s' GROUP BY TIME(booking_date_time) ORDER BY booking_time;".formatted(Date.valueOf(date));
        ArrayList<ArrayList<String>> graphValues = database.selectValues(query);

        // add labels for the x and y-axis
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Time Intervals");

        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Guests");

        // add all the x-y values to a collection to then be added to the line chart
        XYChart.Series<String, Number> xyValues = new XYChart.Series<>();
        lineChart.setTitle("Total number of guests for each 30 minute time interval");


        for (ArrayList<String> row : graphValues) {
            String time = row.get(0);
            int guests = Integer.parseInt(row.get(1));
            xyValues.getData().add(new XYChart.Data<>(time, guests));
        }

        // add the values to the line chart
        lineChart.getData().add(xyValues);
        submit();
    }

    public void submit() throws SQLException {
        // clear the previous data to add the new data
        lineChart.getData().clear();
        bookings.clear();
        revenueStr.setText("");
        numBookings.setText("");
        bookingId.setText("");
        wheelchair.setText("");
        guests.setText("");
        requirement.setText("");
        time.setText("");

        // get the date inputted into the date picker
        date = datePicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        try {
            date = LocalDate.parse(formattedDate, formatter);
        } catch (DateTimeParseException e) {
            errorMessage.setText("Please enter a valid date");
            PauseTransition disappearingMessage = new PauseTransition(Duration.seconds(2));
            disappearingMessage.setOnFinished(event -> errorMessage.setText(""));
            disappearingMessage.play();
            return;
        }

        if (date == null) {
            errorMessage.setText("Date input is empty");
            PauseTransition disappearingMessage = new PauseTransition(Duration.seconds(2));
            disappearingMessage.setOnFinished(event -> errorMessage.setText(""));
            disappearingMessage.play();
            return;
        }




        Database database = new Database();

        ArrayList<ArrayList<String>> query4revenue = database.selectValues(String.format(
                "SELECT SUM(Payment.total_amount) " +
                        "FROM Payment, Orders, Booking " +
                        "WHERE Payment.order_id = Orders.order_id AND " +
                        "Orders.booking_id = Booking.booking_id AND " +
                        "DATE(Booking.booking_date_time) = '%s'".formatted(date)));

        if (query4revenue.isEmpty()) {
            errorMessage.setText("There are no bookings for: " + date.toString());
            PauseTransition disappearingMessage = new PauseTransition(Duration.seconds(2));
            disappearingMessage.setOnFinished(event -> errorMessage.setText(""));
            disappearingMessage.play();
        } else {
            // Displaying total revenue on the screen
            revenueStr.setText("Today's revenue: £" + query4revenue.get(0).get(0));
        }


        // Get total number of bookings
        ArrayList<ArrayList<String>> query4bookings = database.selectValues(String.format("SELECT COUNT(booking_date_time) FROM Booking WHERE DATE(booking_date_time) = '%s'".formatted(date)));

        if (query4bookings.isEmpty()) {
            errorMessage.setText("There are no bookings for: " + date.toString());
            PauseTransition disappearingMessage = new PauseTransition(Duration.seconds(2));
            disappearingMessage.setOnFinished(event -> errorMessage.setText(""));
            disappearingMessage.play();
        } else {
            // Displaying total count of number of bookings for the day
            numBookings.setText("Bookings today: " + query4bookings.get(0).get(0));
        }





        String datePicker = "SELECT * FROM Booking WHERE DATE(booking_date_time) = '%s' ORDER BY DATE(booking_date_time);".formatted(date);

        ArrayList<ArrayList<String>> values = database.selectValues(datePicker);



            if (!values.isEmpty()) {
                for (ArrayList<String> rows : values) {
                    int bookingId = Integer.parseInt(rows.get(0));
                    int custId = Integer.parseInt(rows.get(1));
                    String bookingDateTime = rows.get(2).split(" ")[1];
                    int numCustomers = Integer.parseInt(rows.get(3));
                    int wheelChair = Integer.parseInt(rows.get(4));
                    String requirements = rows.get(5);
                    Booking booking = new Booking(bookingId, custId, bookingDateTime, numCustomers, wheelChair, requirements);
                    bookings.add(booking);
                }
                addBookingForm();
            } else {
                errorMessage.setText("There are no bookings for: " + date.toString());
                PauseTransition disappearingMessage = new PauseTransition(Duration.seconds(2));
                disappearingMessage.setOnFinished(event -> errorMessage.setText(""));
                disappearingMessage.play();
            }


        database.close();

    }

    public void addBookingForm() throws SQLException {
        Database database = new Database();
        // for each booking, we will add the details to the labels in the grid pane section
        bookingId.setText(String.valueOf(bookings.get(index%bookings.size()).getBookingID()));
        guests.setText(String.valueOf(bookings.get(index%bookings.size()).getNumOfDiners()));
        int wheelChair = bookings.get(index%bookings.size()).getWheelchair();
        if (wheelChair == 1) {
            wheelchair.setText("Yes");
        } else if (wheelChair == 0) {
            wheelchair.setText("No");
        }
        requirement.setText(bookings.get(index%bookings.size()).getSpecialRequest());
        time.setText(bookings.get(index%bookings.size()).getDt());

        // now we add the values to the line chart

        String query = "SELECT TIME(booking_date_time) AS booking_time, SUM(number_of_guests) AS total_guests FROM Booking WHERE DATE(booking_date_time) = '%s' GROUP BY TIME(booking_date_time) ORDER BY booking_time;".formatted(Date.valueOf(date));
        ArrayList<ArrayList<String>> graphValues = database.selectValues(query);

        XYChart.Series<String, Number> xyValues = new XYChart.Series<>();

        for (ArrayList<String> row : graphValues) {
            String time = row.get(0);
            int guests = Integer.parseInt(row.get(1));
            xyValues.getData().add(new XYChart.Data<>(time, guests));
        }

        lineChart.getData().add(xyValues);

        database.close();



    }

    @FXML
    public void swipeRight() throws SQLException {
        index++;
        addBookingForm();
    }

    @FXML
    public void swipeLeft() throws SQLException {
        if (index == 0) {
            index = bookings.size()-1;
        }
        index--;
        addBookingForm();
    }

    public void reset(MouseEvent mouseEvent) {
        // reset all the components on the dashboard page
        lineChart.getData().clear();
        bookingId.setText("");
        guests.setText("");
        wheelchair.setText("");
        requirement.setText("");
        time.setText("00:00");
    }
}

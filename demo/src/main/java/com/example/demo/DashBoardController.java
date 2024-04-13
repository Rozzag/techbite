package com.example.demo;

import com.example.demo.connectivity.Database;
import com.example.demo.supportclasses.Booking;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

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

        // check the DatePicker input is not empty and if it is, the buttons are disabled
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            handleButtons();
        });

        // check that the date picker input is not empty
        datePicker.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                handleButtons();
            }
        }));

        // for each selected date we will extract the time and the total number of guests during that period
        Database database = new Database();

        //Get total revenue for the day
        ArrayList<ArrayList<String>> query4revenue = database.selectValues(String.format(
                "SELECT SUM(Payment.total_amount) " +
                "FROM Payment, Orders, Booking " +
                        "WHERE Payment.order_id = Orders.order_id AND " +
                        "Orders.booking_id = Booking.booking_id AND " +
                        "DATE(Booking.booking_date_time) = '%s'", formattedDate));

        // Displaying total revenue on the screen
        revenueStr.setText("Today's revenue: £" + query4revenue.get(0).get(0));

        // Get total number of bookings
        ArrayList<ArrayList<String>> query4bookings = database.selectValues(String.format("SELECT COUNT(booking_date_time) FROM Booking WHERE DATE(booking_date_time) = '%s'", formattedDate));

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

        // add the values to the linechart
        lineChart.getData().add(xyValues);
    }

    // parsing the date input from string to date
    public LocalDate parseDate(String text) {
        try {
            String DATE_FORMAT = "yyyy-MM-dd";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            return LocalDate.parse(text, formatter);
        } catch (DateTimeParseException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }


    public void handleButtons() {
        // we need to also ensure that the inputted text is in the date format
        LocalDate date = datePicker.getValue();
        boolean disableButtons = date == null;
        submitButton.setDisable(disableButtons);
        resetButton.setDisable(disableButtons);
    }
    public void submit() throws SQLException {
        LocalDate date = datePicker.getValue();


        String query = "SELECT * FROM Booking WHERE DATE(booking_date_time) = '%s' ORDER BY DATE(booking_date_time);".formatted(date);
        System.out.println(query);
        Database db = new Database();

        ArrayList<ArrayList<String>> values = db.selectValues(query);

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

        // for each booking, we will add the details to the labels in the grid pane section
        bookingId.setText(String.valueOf(bookings.get(index).getBookingID()));
        guests.setText(String.valueOf(bookings.get(index).getNumOfDiners()));
        int wheelChair = bookings.get(index).getWheelchair();
        if (wheelChair == 1) {
            wheelchair.setText("Yes");
        } else if (wheelChair == 0) {
             wheelchair.setText("No");
        }
        requirement.setText(bookings.get(index).getSpecialRequest());
        time.setText(bookings.get(index).getDt());



    }
}

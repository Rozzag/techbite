package com.example.demo;

import com.example.demo.connectivity.Database;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

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

    private LocalDate date;

    public void initialize() throws SQLException {



        date = LocalDate.of(2024,04,11);
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
        String input = datePicker.getEditor().getText();
        // we need to also ensure that the inputted text is in the date format
        LocalDate date = parseDate(input);
        boolean disableButtons = date == null;
        submitButton.setDisable(disableButtons);
        resetButton.setDisable(disableButtons);
    }
    public void submit() {

    }
}

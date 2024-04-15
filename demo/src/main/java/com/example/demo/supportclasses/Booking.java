package com.example.demo.supportclasses;

import com.example.demo.connectivity.Database;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    private int BookingID;
    private String dt;
    private int numOfDiners;
    private int wheelchair;
    private String specialRequest;
    private String name;
    private String customerPhoneNum;

    public Booking(int bookingId, String customerPhoneNum, String bookingDateTime, int numCustomers, int wheelChair, String requirements, String name) {
        this.BookingID = bookingId;
        this.customerPhoneNum = customerPhoneNum;
        this.dt = bookingDateTime;
        this.numOfDiners = numCustomers;
        this.wheelchair = wheelChair;
        this.specialRequest = requirements;
        this.name = name;
    }

    public Booking(int bookingId, String customerPhoneNum, String bookingDateTime, int numCustomers, int wheelChair, String requirements) {
        this.BookingID = bookingId;
        this.customerPhoneNum = customerPhoneNum;
        this.dt = bookingDateTime;
        this.numOfDiners = numCustomers;
        this.wheelchair = wheelChair;
        this.specialRequest = requirements;
    }

    public static void addGrouping(int bookingID, List<Integer> tableIds) throws SQLException {


        for (int id : tableIds) {
            String query = "INSERT INTO Grouping VALUES(%d,%d,'Unavailable');".formatted(bookingID, id);
            Database db = new Database();
            db.justExecute(query);
            db.close();
        }
    }

    public static LocalTime roundDownToNearestHalfHour(LocalTime time) {
        int minute = time.getMinute();
        int minuteToSubtract = minute % 30; // Get the minutes past the nearest half hour
        return time.minusMinutes(minuteToSubtract); // Subtract those minutes to round down
    }

    public static LocalDateTime parseAndRoundDateTime(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter); // Parse the datetime string
        LocalTime roundedTime = roundDownToNearestHalfHour(dateTime.toLocalTime()); // Round down the time
        return dateTime.withHour(roundedTime.getHour()).withMinute(roundedTime.getMinute()).withSecond(0); // Return new LocalDateTime
    }



    // Add bookings especially for walk-in customers
    public static void addBooking(String name, String customerNum, String dt, int numOfDiners, String specialRequest, int wheelchair, List<Integer> tableIds) throws SQLException {
        Database c = new Database();

        int bookingId = Integer.parseInt(new Database().selectValues("SELECT MAX(booking_id) FROM Booking").get(0).get(0)) + 1;

        LocalDateTime parsedDate = parseAndRoundDateTime(dt);

        String bookingQuery = "INSERT INTO Booking VALUES (%d, '%s', '%s', %d, %d, '%s');".formatted(bookingId, customerNum, dt, numOfDiners, wheelchair, specialRequest);
        String customerQuery = "INSERT INTO Customer VALUES ('%s', '%s')".formatted(name, customerNum);
        String checkExistingCust = "SELECT phone_number FROM Customer WHERE phone_number='%s'".formatted(customerNum);



        try {
            ArrayList<ArrayList<String>> checkCust = c.selectValues(checkExistingCust);
            // Create new customer record if they dont exist already
            if (checkCust.isEmpty()){
                c.insertValues(customerQuery);
            }
            c.insertValues(bookingQuery);
            addGrouping(bookingId, tableIds);
        } catch (SQLException e) {
            System.err.println("There was an error inserting values into the booking table:" + e.getMessage());
        }
    }

    // Get a list of bookings and then loop through the bookings to show the booking details
    // These bookings are after current datetime
    public static ArrayList<Booking> getBookings(String datetime) throws SQLException {
        ArrayList<Booking> bookings = new ArrayList<>();

        String query = String.format("SELECT * FROM Booking AS B JOIN Customer ON B.phone_number = Customer.phone_number WHERE DATE(booking_date_time) = '%s'", datetime);
        Database b = new Database();

        ArrayList<ArrayList<String>> bookingValues = b.selectValues(query);

        // insert into a list of bookings
        for (ArrayList<String> rows : bookingValues) {
            int bookingId = Integer.parseInt(rows.get(0));
            String phnNum = rows.get(1);
            String bookingDateTime = rows.get(2);
            int numCustomers = Integer.parseInt(rows.get(3));
            int wheelChair = Integer.parseInt(rows.get(4));
            String requirements = rows.get(5);
            String Name = rows.get(6);
            Booking booking = new Booking(bookingId, phnNum, bookingDateTime, numCustomers, wheelChair, requirements, Name);
            bookings.add(booking);
        }
        return bookings;
    }

    public void cancelBooking() throws SQLException {
        String cancelQuery = String.format("DELETE FROM Booking WHERE booking_id=%d", getBookingID());

        Database db = new Database("in2033t01_a", "CtYS1azKU-8");

        db.justExecute(cancelQuery);

        db.close();
    }

    public int getBookingID() {
        return BookingID;
    }

    public String getDt() {
        return dt;
    }

    public int getNumOfDiners() {
        return numOfDiners;
    }

    public int getWheelchair() {
        return wheelchair;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public String getName() {
        return name;
    }

    public String getCustomerPhoneNum() {
        return customerPhoneNum;
    }
}
package com.example.demo.supportclasses;

import com.example.demo.connectivity.Database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Booking {
    private int BookingID;
    private int CustomerID;
    private String dt;
    private int numOfDiners;
    private int wheelchair;
    private String specialRequest;
    private String name;
    private String customerPhoneNum;

    public static int bookingId = 17;
    public static int customerId = 17;

    private int custId;

    public Booking(int bookingID, int customerID, String dt, int numOfDiners, int wheelchair, String specialRequest, String name, String customerPhoneNum) {
        BookingID = bookingID;
        CustomerID = customerID;
        this.dt = dt;
        this.numOfDiners = numOfDiners;
        this.wheelchair = wheelchair;
        this.specialRequest = specialRequest;
        this.name = name;
        this.customerPhoneNum = customerPhoneNum;
    }

    public Booking(int bookingId, int custId, String bookingDateTime, int numCustomers, int wheelChair, String requirements) {
        this.BookingID = bookingId;
        this.custId = custId;
        this.dt = bookingDateTime;
        this.numOfDiners = numCustomers;
        this.wheelchair = wheelChair;
        this.specialRequest = requirements;
    }


    // Add bookings especially for walk-in customers
    public static void addBooking(String name, String customerNum, int numOfDiners, String specialRequest, int wheelchair) throws SQLException {
        Database c = new Database();

        String query = "INSERT INTO Booking VALUES (%d, %d, '%s', %d, %d, '%s');".formatted(bookingId, customerId, LocalDate.now(), numOfDiners, wheelchair, specialRequest);

        try {
            boolean updateTable = c.insertValues(query);
        } catch (SQLException e) {
            System.err.println("There was an error inserting values into the booking table:" + e.getMessage());
        }


    }

    // Get a list of bookings and then loop through the bookings to show the booking details
    // These bookings are after current datetime
    public static List<Booking> getBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();

        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dt.format(formatter);

        String query = String.format("SELECT * FROM Booking AS B JOIN Customer ON B.customer_id = Customer.customer_id WHERE booking_date_time >= '%s' ORDER BY booking_date_time", formattedDateTime);
        Database b = new Database();

        ArrayList<ArrayList<String>> bookingValues = b.selectValues(query);

        // insert into a list of bookings
        for (ArrayList<String> rows : bookingValues) {
            int bookingId = Integer.parseInt(rows.get(0));
            int custId = Integer.parseInt(rows.get(1));
            String bookingDateTime = rows.get(2);
            int numCustomers = Integer.parseInt(rows.get(3));
            int wheelChair = Integer.parseInt(rows.get(4));
            String requirements = rows.get(5);
            Booking booking = new Booking(bookingId, custId, bookingDateTime, numCustomers, wheelChair, requirements);
            bookings.add(booking);
        }
        return bookings;
    }

    public int getCustId() {
        return custId;
    }

    public int getBookingID() {
        return BookingID;
    }

    public int getCustomerID() {
        return CustomerID;
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

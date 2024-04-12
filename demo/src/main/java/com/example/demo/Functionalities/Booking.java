package com.example.demo.Functionalities;

import java.sql.*;
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

    // Add bookings especially for walk-in customers
    public static void addBooking(String name, String customerNum, int numOfDiners, String specialRequest, int wheelchair) throws SQLException {
        Connectivity c = new Connectivity();
        Connection conn = c.getConnection();
        Statement stm = conn.createStatement();

        // First checks if the customer's details have been stored already
        AdminConnectivity ac = new AdminConnectivity();
        Connection adminConn = ac.getConnection();
        Statement adminStm = adminConn.createStatement();

        ResultSet rs = stm.executeQuery(String.format("SELECT customer_id FROM Customer WHERE name='%s' AND phone_number='%s'", name, customerNum));

        int customerID = 0;
        int BookingID = 0;

        if(rs.next()){
            customerID = rs.getInt("customer_id");
        }else{
            rs = stm.executeQuery("SELECT MAX(customer_id) AS max_id FROM Customer");
            if(rs.next()) {
                customerID = rs.getInt("max_id") + 1;
                // Else write customer details
                String insertQuery = String.format("INSERT INTO Customer VALUES (%d, '%s', '%s')", customerID, name, customerNum);
                adminStm.executeUpdate(insertQuery);
            }
        }

        // Assuming 'booking_id' is set to auto-increment in your database
        rs = stm.executeQuery("SELECT MAX(booking_id) AS max_id FROM Booking");
        if (rs.next()) {
            BookingID = rs.getInt("max_id") + 1;
        } else {
            BookingID = 0;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        adminStm.executeUpdate(String.format("INSERT INTO Booking VALUES (%d, %d, '%s', %d, %d, '%s')", BookingID, customerID, formattedDateTime, numOfDiners, wheelchair, specialRequest));
    }

    // Get a list of bookings and then loop through the bookings to show the booking details
    // These bookings are after current datetime
    public static List<Booking> getBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();

        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dt.format(formatter);

        String query = String.format("SELECT * FROM Booking AS B JOIN Customer ON B.customer_id = Customer.customer_id WHERE booking_date_time >= '%s' ORDER BY booking_date_time", formattedDateTime);
        Connectivity c = new Connectivity();
        Connection conn = c.getConnection();
        Statement stm = conn.createStatement();

        ResultSet rs = stm.executeQuery(query);

        while(rs.next()){
            int bID = rs.getInt("booking_id");
            int cID = rs.getInt("customer_id");
            String datetime = rs.getString("booking_date_time");
            int numGuests = rs.getInt("number_of_guests");
            int wheel = rs.getInt("wheelchair_accessibility");
            String spReq = rs.getString("special_requests");
            String Name = rs.getString("name");
            String phnNum = rs.getString("phone_number");
            Booking book = new Booking(bID, cID, datetime, numGuests, wheel, spReq, Name, phnNum);
            bookings.add(book);
        }

        return bookings;
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

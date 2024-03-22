import kitchen.Connectivity;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws SQLException {
       // Working JDBC to the SQL database

        String CONN_STRING = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2033t01";
        String userName = "in2033t01_d";
        String passWord = "9XDKGxcQhhI";

        LocalDateTime localTime = LocalDateTime.of(2024, 4, 22, 0, 0);
        Timestamp time = Timestamp.valueOf(localTime);
        String requests = "This is a test value";

        Connection conn = DriverManager.getConnection(CONN_STRING, userName, passWord);
        String customerQuery = "INSERT INTO Customer VALUES (1, 'Test', '1234567890');";
        System.out.println(customerQuery);
        Statement customerStm = conn.createStatement();
        int insertCustomer = customerStm.executeUpdate(customerQuery);

        String bookingQuery = "INSERT INTO Booking VALUES (1, 1, " + "'" + time + "'" + ", 2, 0," + "'" + requests + "'" + ");";
        System.out.println(bookingQuery);
        Statement bookingStm = conn.createStatement();
        int insertion = bookingStm.executeUpdate(bookingQuery);



    }
}

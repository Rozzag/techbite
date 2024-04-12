import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Booking {
    private int BookingID;
    private String name;
    private String customerNum;
    private int customerID;
    private int numOfDiners;
    private int wheelchair;
    private String specialRequest;
    private Connection conn;

    public Booking(String name, String customerNum, int numOfDiners, int wheelchair, String specialRequest) throws SQLException {
        this.name = name;
        this.customerNum = customerNum;
        this.numOfDiners = numOfDiners;
        this.specialRequest = specialRequest;
        this.wheelchair = wheelchair;

        Connectivity c = new Connectivity();
        conn = c.getConnection();
        Statement stm = conn.createStatement();

        // First checks if the customer's details have been stored already
        ResultSet rs = stm.executeQuery(String.format("SELECT customer_id FROM Customer WHERE name='%s' AND phone_number='%s'", this.name, this.customerNum));

        AdminConnectivity ac = new AdminConnectivity();
        Connection adminConn = ac.getConnection();
        Statement adminStm = adminConn.createStatement();

        if(rs.next()){
            this.customerID = rs.getInt("customer_id");
        }else{
            rs = stm.executeQuery("SELECT MAX(customer_id) AS max_id FROM Customer");
            if(rs.next()) {
                this.customerID = rs.getInt("max_id") + 1;
                // Else write customer details
                String insertQuery = String.format("INSERT INTO Customer VALUES (%d, '%s', '%s')", this.customerID, this.name, this.customerNum);
                adminStm.executeUpdate(insertQuery);
            }
        }

        // Assuming 'booking_id' is set to auto-increment in your database
        rs = stm.executeQuery("SELECT MAX(booking_id) AS max_id FROM Booking");
        if (rs.next()) {
            this.BookingID = rs.getInt("max_id") + 1;
        } else {
            this.BookingID = 0;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        adminStm.executeUpdate(String.format("INSERT INTO Booking VALUES (%d, %d, '%s', %d, %d, '%s')", this.BookingID, this.customerID, formattedDateTime, this.numOfDiners, this.wheelchair, this.specialRequest));
    }
}

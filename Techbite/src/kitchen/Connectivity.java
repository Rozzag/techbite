package kitchen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectivity {

    private static final String CONN_STRING = "jdbc:mysql://server smcse-stuproj00.city.ac.uk:3306/in2033t01";
    private static final String userName = "in2033t01_d";
    private static final String passWord = "9XDKGxcQhhI";

    public boolean connect() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        boolean isConnected = false;
        Connection connection = DriverManager.getConnection(
                CONN_STRING, userName, passWord
        );
        System.out.println("Connection successful!");
        isConnected = true;
        return isConnected;
    }
}

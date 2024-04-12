import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectivity {
    private static final String CONN_STRING = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2033t01";
    private static final String userName = "in2033t01_d";
    private static final String passWord = "9XDKGxcQhhI";

    private Connection connection = null;

    public Connectivity () {
        try {
            connection = DriverManager.getConnection(CONN_STRING, userName, passWord);
        } catch (SQLException e) {
            System.err.println("There was an error connecting to the database" + e.getMessage());
        }
    }

    public Connection getConnection(){return connection;}

    public boolean close() throws SQLException {
        if (!connection.isClosed()) {
            connection.close();
            return true;
        }
        return false;

    }
}

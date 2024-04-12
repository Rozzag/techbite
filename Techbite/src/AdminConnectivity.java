import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AdminConnectivity {
    private static final String CONN_STRING = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2033t01";
    private static final String userName = "in2033t01_a";
    private static final String passWord = "CtYS1azKU-8";

    private Connection connection = null;

    public AdminConnectivity () {
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

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class Connectivity {

    private static final String CONN_STRING = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2033t01/";
    private static final String userName = "in2033t01_d";
    private static final String passWord = "9XDKGxcQhhI";


     public boolean getConnection() throws SQLException {



         boolean isConnected = false;

         try {
             Properties connectionProps = new Properties();
             connectionProps.put("user", userName);
             connectionProps.put("password", passWord);
             String serverName = "smcse-stuproj00.city.ac.uk";
             int portNumber = 3306;
             String dbName = "in2033t01";
             Connection con = DriverManager.getConnection("jdbc:mysql://"
                     + "localhost" + ":" + portNumber
                     + "/" + dbName, connectionProps);
             isConnected = true;
             System.out.println("Connected");
         }
         catch(SQLException sqle) {
               throw new SQLException("Wasn't able to connect to database");
         }

         return isConnected;
    }
}

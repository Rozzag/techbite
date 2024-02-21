package kitchen;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connectivity conn = new Connectivity();
        conn.connect();
    }
}

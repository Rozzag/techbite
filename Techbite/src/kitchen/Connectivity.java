package kitchen;

import java.sql.*;

public class Connectivity {

    private static final String CONN_STRING = "jdbc:mysql://server smcse-stuproj00.city.ac.uk:3306/in2033t01";
    private static final String userName = "in2033t01_d";
    private static final String passWord = "9XDKGxcQhhI";

    public boolean connect() throws SQLException {

        boolean isConnected = false;


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


            Connection connection = DriverManager.getConnection(
                    CONN_STRING, userName, passWord
            );
            System.out.println("Connection successful!");
            isConnected = true;

        return isConnected;
    }

    public boolean printTable(ResultSet rs) throws SQLException {

        boolean found = false;
        var meta = rs.getMetaData();

        for (int i=1; i<=meta.getColumnCount(); i++) {
            System.out.printf("%-15s", meta.getColumnName(i).toUpperCase());
        }

        System.out.println();

        for (int i=1; i<=meta.getColumnCount(); i++) {
            System.out.printf("%-15s", rs.getString(i));
        }

        System.out.println();

        found = true;

        return found;
    }

    public boolean selectValues(Statement stm, String tableName,
                                String columnName, String columnValue) throws SQLException {

        String query = "SELECT * FROM %s WHERE %s='s'"
                .formatted(tableName, columnName, columnValue);

        // now we will execute the sql statement using the .executeQuery method
        var rs = stm.executeQuery(query);
        if (rs != null) {
            return printTable(rs);
        } return false;

    }

    public boolean insertValues(Statement stm, String tableName, String[] columnNames,
                                String[] columnValues) throws SQLException {

        // join the arrays into a list separated by commas for them to be
        // added as code in SQL
        String colNames = String.join(",", columnNames);
        String colValues = String.join(",", columnValues);

        // now define the qeury using the values inputted into the method
        String query = "INSERT into %s (%s) VALUES ('%s')"
                .formatted(tableName, colNames, colValues);

        // execute the query into the schema
        var rs = stm.executeQuery(query);

        // we will return true if the table was updated and false if not

        int updatedTable = stm.getUpdateCount();

        // let's also print the row added to the table
        if (updatedTable > 0) {
            selectValues(stm, tableName, columnNames[0], columnValues[0]);
        }

        return updatedTable > 0;
    }
}

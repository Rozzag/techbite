package management;

import java.sql.*;

/**
 * @author Adam Rezzag Salem
 * @date 22nd February 2024
 * @description The class will be used to connect to the schema and retrieve date in the interface implementation
 */
public class Connectivity {

    private static final String CONN_STRING = "jdbc:mysql://server smcse-stuproj00.city.ac.uk:3306/in2033t01";
    private static final String userName = "in2033t01_d";
    private static final String passWord = "9XDKGxcQhhI";

    /**
     *
     * @return Connection object which is used to declare statements to query the SQL database
     * @throws SQLException
     */
    public Connection connect() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = DriverManager.getConnection(
                CONN_STRING, userName, passWord
        );
        System.out.println("Connection successful!");

        return connection;
    }

    /**
     *
     * @param rs which is a ResultSet object that returns after the query is made
     * @return boolean that lets us know that the connection with the database has been made successfully
     * @throws SQLException
     */
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

    /**
     *
     * @param stm which is created from declaring a statement using the Connection object
     * @param tableName of type String
     * @param columnName of type String
     * @param columnValue of type String
     * @return boolean value for successful querying database
     * @throws SQLException
     */
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

    /**
     *
     * @param stm type Statement
     * @param tableName type String
     * @param columnNames type String[] in order to allow multiple values to be inserted
     * @param columnValues type String[] to specify all the columns in which the values will be inserted into
     * @return
     * @throws SQLException
     */
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

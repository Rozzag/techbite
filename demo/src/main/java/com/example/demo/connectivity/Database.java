package com.example.demo.connectivity;


import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

/**
 * The class will allow for the interface methods to run and connect successfully with the database from the front house
 * @author Adam Rezzag Salem
 * @date 22nd February 2024
 */
public class Database {

    private static final String CONN_STRING = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2033t01";
    private static final String userName = "in2033t01_d";
    private static final String passWord = "9XDKGxcQhhI";

    private Connection connection = null;

    public Database () {
        try {
            connection = DriverManager.getConnection(CONN_STRING, userName, passWord);
        } catch (SQLException e) {
            System.err.println("There was an error connecting to the database" + e.getMessage());
        }
    }

    public Database (String user, String pass) {
        try {
            connection = DriverManager.getConnection(CONN_STRING, user, pass);
        } catch (SQLException e) {
            System.err.println("There was an error connecting to the database" + e.getMessage());
        }
    }

    /**
     * prints out a table to the console from a given query
     * @param rs type ResultSet
     * @return boolean
     * @throws SQLException
     */
    public ArrayList<ArrayList<String>> printTable(ResultSet rs) throws SQLException {

        ArrayList<ArrayList<String>> results = new ArrayList<>();

        boolean found = false;
        var meta = rs.getMetaData();

        while (rs.next()) {
            ArrayList<String> row = new ArrayList<>();
            for (int i=1; i<=meta.getColumnCount(); i++) {
                row.add(rs.getString(i));
            }
            results.add(row);
        }

        found = true;

        return results;
    }



    /**
     * prints out the row of a given table with some conditions
     * @param tableName
     * @param columnName
     * @param columnValue
     * @return boolean
     * @throws SQLException
     */
    public ArrayList<ArrayList<String>> selectValues(String tableName,
                                                     String columnName, String columnValue) throws SQLException {

        String query = "SELECT * FROM %s WHERE %s='s'"
                .formatted(tableName, columnName, columnValue);

        PreparedStatement pstm = connection.prepareStatement(query);

        // now we will execute the sql statement using the .executeQuery method
        var rs = pstm.executeQuery(query);
        if (rs != null) {
            return printTable(rs);
        } return null;

    }

    public boolean justExecute(String query) throws SQLException {
        PreparedStatement pstm = connection.prepareStatement(query);

        int update = pstm.executeUpdate(query);

        if (update >= 1) {
            return true;
        }
        return false;
    }

    public boolean insertValues(String query) throws SQLException {

        PreparedStatement pstm = connection.prepareStatement(query);

        // now we will execute the sql statement using the .executeQuery method
        try {
            int rs = pstm.executeUpdate(query);
            if (rs == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    return false;


    }


    public ArrayList<ArrayList<String>> selectValues(String query) throws SQLException {
        PreparedStatement pstm = connection.prepareStatement(query);

        // now we will execute the sql statement using the .executeQuery method
        var rs = pstm.executeQuery(query);
        if (rs != null) {
            return printTable(rs);
        } return null;

    }

    /**
     * allows for the insertion of values into a relation
     * @param tableName
     * @param columnNames
     * @param columnValues
     * @return boolean
     * @throws SQLException
     */
    public boolean insertValues(String tableName, String[] columnNames,
                                String[] columnValues) throws SQLException {

        // join the arrays into a list separated by commas for them to be
        // added as code in SQL
        String colNames = String.join(",", columnNames);
        String colValues = String.join(",", columnValues);

        // now define the qeury using the values inputted into the method
        String query = "INSERT into %s (%s) VALUES ('%s')"
                .formatted(tableName, colNames, colValues);

        Statement stm = connection.createStatement();

        // execute the query into the schema
        var rs = stm.executeQuery(query);

        // we will return true if the table was updated and false if not

        int updatedTable = stm.getUpdateCount();

        return updatedTable > 0;
    }

    public boolean close() throws SQLException {
        if (!connection.isClosed()) {
            connection.close();
            return true;
        }
        return false;

    }

}

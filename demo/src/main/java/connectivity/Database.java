package connectivity;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private Connection connection;
    private static final String CONN_STRING = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2033t01";
    private static final String userNameDatabase = "in2033t01_d";
    private static final String passWordDatabase = "9XDKGxcQhhI";

    public Database() throws SQLException {
        this.connection = DriverManager.getConnection(CONN_STRING, userNameDatabase, passWordDatabase);
    }

    public ArrayList<String> queryTable(String[] columnQuery,String tableName, String columnName, String columnValue) throws SQLException {
        String query = "SELECT " + String.join(", ", columnQuery) + " FROM " + tableName + " WHERE " + columnName +" =?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, columnValue);

        var rs = preparedStatement.executeQuery();

        ArrayList<String> results = new ArrayList<>();

        var meta = rs.getMetaData();

        while(rs.next()) {
            for (int i=1; i<=meta.getColumnCount(); i++) {
                results.add(rs.getString(i));
            }
        }

        return results;

    }

    public ArrayList<String> queryTable(String columnQuery, String tableName) throws SQLException {
        String query = "SELECT " + String.join(", ", columnQuery) + " FROM " + tableName;

        PreparedStatement preparedStatement = connection.prepareStatement(query);


        var rs = preparedStatement.executeQuery();

        ArrayList<String> results = new ArrayList<>();

        var meta = rs.getMetaData();

        while(rs.next()) {
            for (int i=1; i<=meta.getColumnCount(); i++) {
                results.add(rs.getString(i));
            }
        }

        return results;
    }

    public boolean insertValues(String tableName, String[] columnNames,
                                String[] columnValues) throws SQLException {

        // join the arrays into a list separated by commas for them to be
        // added as code in SQL
        String colNames = String.join(",", columnNames);
        String colValues = String.join(",", columnValues);



        String query = "INSERT INTO " + tableName + " (" + colNames + ") " + " VALUES (" + colValues + ";";

        // execute the query into the schema

        PreparedStatement pstm = connection.prepareStatement(query);
        var rs = pstm.executeQuery(query);

        // we will return true if the table was updated and false if not

        int updatedTable = pstm.getUpdateCount();


        return updatedTable > 0;
    }



    public boolean closeConnection() throws SQLException {
        connection.close();
        return true;
    }
}

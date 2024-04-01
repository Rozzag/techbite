package kitchen;


import java.sql.*;

/**
 * The class will allow for the interface methods to run and connect successfully with the database from the front house
 * @author Adam Rezzag Salem
 * @date 22nd February 2024
 */
public class Connectivity {

    private static final String CONN_STRING = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2033t01";
    private static final String userName = "in2033t01_d";
    private static final String passWord = "9XDKGxcQhhI";


    /**
     * This will connect to the database and return an object of type Connection to then be
     * used for querying
     * @return Connection object
     * @throws SQLException
     */
    public Connection connect()  {


      try {
          Connection connection = DriverManager.getConnection(CONN_STRING, userName, passWord);

          System.out.println("Successful connection with database!");

          return connection;
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }



    }

    /**
     * prints out a table to the console from a given query
     * @param rs type ResultSet
     * @return boolean
     * @throws SQLException
     */
    public boolean printTable(ResultSet rs) throws SQLException {

        boolean found = false;
        var meta = rs.getMetaData();

        for (int i=1; i<=meta.getColumnCount(); i++) {
            System.out.printf("%-15s", meta.getColumnName(i).toUpperCase());
        }

        System.out.println();
        
        while (rs.next()) {
            for (int i=1; i<=meta.getColumnCount(); i++) {
                System.out.printf("%-15s", rs.getString(i));
            }
        }


        System.out.println();

        found = true;

        return found;
    }

    /**
     * prints out the row of a given table with some conditions
     * @param stm
     * @param tableName
     * @param columnName
     * @param columnValue
     * @return boolean
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
     * allows for the insertion of values into a relation
     * @param stm
     * @param tableName
     * @param columnNames
     * @param columnValues
     * @return boolean
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

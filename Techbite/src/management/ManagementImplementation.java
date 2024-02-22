package management;

import java.sql.*;
import java.util.List;

public class ManagementImplementation implements ManagementInterface {

    @Override
    public Order getOrder(int orderId) {


        try {
            // create a Connection by calling the Connectivity class provided in the package
            Connectivity conn = new Connectivity();

            // establish a statement object which is derived from the connection
            Statement stm = conn.connect().createStatement();

            // use the method select query to select the row with the specific orderId and print to the console
            conn.selectValues(stm, "Orders", "order_id", "orderId");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new Order(orderId);
    }

    @Override
    public List<String> getPopularDishes() throws SQLException {

        // returning the top 5 most popular dishes
        Connectivity conn = new Connectivity();

        try {

            Statement stm = conn.connect().createStatement();

            // the query will return all the dishes for the order ID given from the OrderDetails table

            String query = ("SELECT MenuItem.name, OrderDetails.quantity as amount FROM MenuItem INNER JOIN OrderDetails ON " +
                    "MenuItem.item_id=OrderDetails.item_id ORDER BY amount DESC LIMIT 5");

            // execute the query

            ResultSet rs = stm.executeQuery(query);

            // instantiate a list of type string and add all the dishes to the list
            List<String> dishes = null;
            while (rs.next()) {
                dishes.add(rs.getString(1));
            }

            // return the list
            return dishes;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.connect().close();
        }
    }

    @Override
    public double getAmount(int orderId) throws SQLException {
        // this is done by querying the Payment table and totalling the payments for the day

        double amount = 0;

        Connectivity conn = new Connectivity();
        try {

          Statement stm = conn.connect().createStatement();

          // define the query for selecting the amount paid for a given order id
            String query = "SELECT total_amount FROM Payment INNER JOIN OrderDetails ON Payment.OrderDetailsID = OrderDetails.OrderDetailsID WHERE order_id='%s' "
                    .formatted(orderId);

            ResultSet rs = stm.executeQuery(query);

            amount = Integer.parseInt(rs.getString(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.connect().close();
        }
        return amount;
    }

    @Override
    public double getTotal(String date) throws SQLException {

        double total = 0;

        Connectivity conn = new Connectivity();
        try {


            // define the string which will total the amount of money made in a day
            String query = "SELECT SUM(total_amount) FROM Payment WHERE payment_date= ?";

            PreparedStatement pst = conn.connect().prepareStatement(query);

            Timestamp time = Timestamp.valueOf(date);
            pst.setTimestamp(1, time);

            ResultSet rs = pst.executeQuery();


            total = Integer.parseInt(rs.getString(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.connect().close();
        }
        return total;
    }

    @Override
    public int getCustomers(int bookingId) throws SQLException {

        // return the number of customers being served for a given order
        int total = 0;

        Connectivity conn = new Connectivity();
        try {


            // define the string which will total the amount of money made in a day
            String query = "SELECT number_ofguests FROM Booking WHERE booking_id=?";

            PreparedStatement pst = conn.connect().prepareStatement(query);

            pst.setInt(1,bookingId);

            ResultSet rs = pst.executeQuery();

            total = rs.getInt(1);




        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.connect().close();
        }
        return total;

    }

    @Override
    public List<String> getCustomerDishes(int orderId) throws SQLException {
        // returning all the dishes for an order
        Connectivity conn = new Connectivity();


        try {

            Statement stm = conn.connect().createStatement();

            // the query will return all the dishes for the order ID given from the OrderDetails table

            String query = ("SELECT MenuItem.name as name FROM MenuItem INNER JOIN OrderDetails ON " +
                    "MenuItem.item_id=OrderDetails.item_id");

            // execute the query

            ResultSet rs = stm.executeQuery(query);

            // instantiate a list of type string and add all the dishes to the list
            List<String> dishes = null;
            while (rs.next()) {
                dishes.add(rs.getString(1));
            }

            // return the list
            return dishes;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.connect().close();
        }
    }
}

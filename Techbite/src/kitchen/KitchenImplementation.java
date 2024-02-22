package kitchen;

import kitchen.Connectivity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitchenImplementation implements KitchenInterface{


    @Override
    public Map<String, Integer> getDishesAndQuantity(int orderId) throws SQLException {

        // returning the top 5 most popular dishes
        kitchen.Connectivity conn = new Connectivity();

        Map<String,Integer> dishes = new HashMap<>();


        try {


            // the query will return all the dishes for the order ID given from the OrderDetails table

            String query = ("SELECT MenuItem.name as name, OrderDetails.quantity as quantity FROM MenuItem INNER JOIN OrderDetails ON " +
                    "MenuItem.item_id=OrderDetails.item_id WHERE OrderDetials.order_id=? GROUP BY MenuItem.name ");

            PreparedStatement pst = conn.connect().prepareStatement(query);

            // add the value of the order id into the query

            pst.setInt(1, orderId);

            // execute the query

            ResultSet rs = pst.executeQuery();

            // instantiate a list of type string and add all the dishes to the list
            while (rs.next()) {
                dishes.put(rs.getString("name"), Integer.parseInt(rs.getString("quantity")));
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
    public int getOrderId(String customerName) throws SQLException {

        // return the order id for the given customer
        int id = 0;

        Connectivity conn = new Connectivity();
        try {


            // define the string which will total the amount of money made in a day
            String query = "SELECT Visit.order_id FROM Visit JOIN Customer ON Visit.customer_id = Customer.customer_id WHERE " +
                    "Customer.name = ?";

            PreparedStatement pst = conn.connect().prepareStatement(query);

            pst.setString(1,customerName);

            ResultSet rs = pst.executeQuery();

            id = rs.getInt(1);




        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.connect().close();
        }
        return id;
    }

    @Override
    public String additionalRequirements(int orderId) throws SQLException {

        // return the order id for the given customer
        String requirements;

        Connectivity conn = new Connectivity();
        try {


            // define the string which will total the amount of money made in a day
            String query = "SELECT special_requests FROM Orders WHERE order_id=?";

            PreparedStatement pst = conn.connect().prepareStatement(query);

            pst.setInt(1,orderId);

            ResultSet rs = pst.executeQuery();

            requirements = rs.getString(1);




        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.connect().close();
        }
        return requirements;
    }
}

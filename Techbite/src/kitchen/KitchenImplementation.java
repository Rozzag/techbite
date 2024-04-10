package kitchen;

import kitchen.Connectivity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitchenImplementation implements KitchenInterface {


    @Override
    public Map<String, Integer> getDishesAndQuantity(int orderId) throws SQLException {

        Connectivity connectivity = new Connectivity();

        String query = "SELECT MI.name AS DishName, O.quantity AS Quantity FROM Orders AS O JOIN MenuItem AS MI ON O.item_id = MI.item_id WHERE O.order_id = %s;".formatted(orderId);

        Map<String, Integer> dishes = new HashMap<>();

        ArrayList<ArrayList<String>> values = connectivity.selectValues(query);


       ArrayList<String> dish = values.get(0);


       for (int i=0; i<dish.size()-1; i++) {
           dishes.put(dish.get(i), Integer.valueOf(dish.get(i+1)));
       }

       if (!dishes.isEmpty()) {
           connectivity.close();
           return dishes;
       }

        System.out.println("The order ID " + orderId + " has no dishes");
        connectivity.close();
        return null;
    }

    @Override
    public int getOrderId(String customerName) throws SQLException {
            Connectivity connectivity = new Connectivity();

            String query = "SELECT O.order_id" +
                    " FROM Customer AS C" +
                    " JOIN Visit AS V ON C.customer_id = V.customer_id " +
                    "JOIN Orders AS O ON V.table_id = O.table_id" +
                    " WHERE C.name = '%s';".formatted(customerName);

            ArrayList<ArrayList<String>> orderIds = connectivity.selectValues(query);

            if (!orderIds.isEmpty()) {
                connectivity.close();
                return Integer.parseInt(orderIds.get(0).get(0));
            }

        System.out.println("The order id for customer " + customerName + " doesn't exist");
            connectivity.close();
            return 0;

    }

    @Override
    public String additionalRequirements(int orderId) throws SQLException {

        Connectivity connectivity = new Connectivity();

        ArrayList<ArrayList<String>> requirements = connectivity.selectValues("Orders", "order_id", String.valueOf(orderId));
        if (!requirements.isEmpty()) {
            connectivity.close();
            return requirements.get(0).get(5);
        }

        System.out.println("The additional requirements for the order ID " + orderId + " was not found");
        connectivity.close();
        return null;

    }
}

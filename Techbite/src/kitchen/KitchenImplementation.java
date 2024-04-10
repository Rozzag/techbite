package kitchen;

import kitchen.Connectivity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public List<Integer> getOrderId() throws SQLException {
            Connectivity connectivity = new Connectivity();

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatDate = today.format(formatter);

            String query = "SELECT Orders.order_id" +
                    " FROM Orders, Booking " +
                    "WHERE Orders.booking_id = Booking.booking_id AND DATE(Booking.booking_date_time) = '%s';".formatted(formatDate);

            ArrayList<Integer> orderIds = connectivity.getIDs(query);

            if (!orderIds.isEmpty()) {
                connectivity.close();
                return orderIds;
            }

        System.out.println("There are currently no more orders today");
            connectivity.close();
            return new ArrayList<Integer>();

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

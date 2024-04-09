package management;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagementImplementation implements ManagementInterface {

    @Override
    public Order getOrder(int orderId) throws SQLException {

        Connectivity connectivity = new Connectivity();

        ArrayList<ArrayList<String>> orderTable = connectivity.selectValues("Orders", "order_id", String.valueOf(orderId));

        // the first arraylist will be the order_id, the second the table number ...
        String id = orderTable.get(0).get(0);
        String tableNumber = orderTable.get(1).get(0);
        String orderDate = orderTable.get(2).get(0);
        String requirements = orderTable.get(3).get(0);

        connectivity.close();
        return new Order(Integer.parseInt(id), Integer.parseInt(tableNumber), orderDate, requirements);
    }

    @Override
    public List<String> getPopularDishes() throws SQLException {
        Connectivity connectivity = new Connectivity();

        String query = "SELECT MI.name, COUNT(*) AS order_count\n" +
                "FROM MenuItem MI\n" +
                "JOIN Orders O ON MI.item_id = O.item_id\n" +
                "GROUP BY MI.item_id\n" +
                "ORDER BY order_count DESC\n" +
                "LIMIT 5;";

        ArrayList<ArrayList<String>> popularDishes = connectivity.selectValues(query);

        List<String> popDishes = popularDishes.get(0);

        connectivity.close();
        return popDishes;

    }

    @Override
    public double getAmount(int orderId) throws SQLException {

        Connectivity connectivity = new Connectivity();

        String query = "SELECT total_amount\n" +
                "FROM Payment\n" +
                "WHERE order_id = %s;\n".formatted(orderId);

        ArrayList<ArrayList<String>> totAmount = connectivity.selectValues(query);
        connectivity.close();
        return Double.parseDouble(totAmount.get(0).get(0));
    }

    @Override
    /**
     * @params date should be in YYYY-MM-DD
     */
    public double getTotal(String date) throws SQLException {

        Connectivity connectivity = new Connectivity();
        String query = "SELECT SUM(P.total_amount) AS total_sales\n" +
                "FROM Payment P\n" +
                "JOIN Orders O ON P.order_id = O.order_id\n" +
                "JOIN Booking B ON O.booking_id = B.booking_id\n" +
                "WHERE DATE(B.booking_date_time) = %s;\n".formatted(date);

        ArrayList<ArrayList<String>> sum = connectivity.selectValues(query);

        connectivity.close();
        return Double.parseDouble(sum.get(0).get(0));
    }

    @Override
    public int getCustomers(int bookingId) throws SQLException {

        Connectivity connectivity = new Connectivity();

        String query = "SELECT COUNT(*) AS customer_count\n" +
                "FROM Booking\n" +
                "WHERE booking_id = %s;\n".formatted(bookingId);

        ArrayList<ArrayList<String>> numCustomers = connectivity.selectValues(query);

        connectivity.close();
        return Integer.parseInt(numCustomers.get(0).get(0));

    }

    @Override
    public List<String> getCustomerDishes(int orderId) throws SQLException {
            Connectivity connectivity = new Connectivity();

            String query = "SELECT MI.name AS DishName\n" +
                    "FROM Orders O\n" +
                    "JOIN MenuItem MI ON O.item_id = MI.item_id\n" +
                    "WHERE O.order_id = %s;\n".formatted(orderId);

            ArrayList<ArrayList<String>> dishes = connectivity.selectValues(query);

            connectivity.close();
            return dishes.get(0);
    }
}

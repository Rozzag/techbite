package management;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ManagementImplementation implements ManagementInterface {

    @Override
    public Order getOrder(int orderId) throws SQLException {

        Connectivity connectivity = new Connectivity();

        ArrayList<ArrayList<String>> orderTable;
        String query = "SELECT * FROM Orders WHERE order_id=%s;".formatted(orderId);
        System.out.println(query);
        orderTable = connectivity.selectValues(query);


        // the first arraylist will be the order_id, the second the table number ...
        if (!orderTable.isEmpty()) {
            String id = orderTable.get(0).get(0);
            String tableNumber = orderTable.get(0).get(2);
            int quantity = Integer.parseInt(orderTable.get(0).get(4));
            String requirements = orderTable.get(0).get(5);
            connectivity.close();
            return new Order(Integer.parseInt(id), Integer.parseInt(tableNumber), quantity, requirements);

        }
        connectivity.close();
        System.out.println("There are no orders for ID " + orderId);
        return null;

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

        ArrayList<ArrayList<String>> popularDishes;
        popularDishes = connectivity.selectValues(query);

        List<String> popDishes = new LinkedList<>();

        for (int i=0; i<popularDishes.size(); i++) {
            popDishes.add(popularDishes.get(i).get(0));
        }

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
    public String getTotal(String date) throws SQLException {


        Connectivity connectivity = new Connectivity();
        String query = "SELECT SUM(Payment.total_amount) AS daily_revenue FROM Booking JOIN Payment ON Booking.booking_id = Payment.order_id WHERE DATE(Booking.booking_date_time) = '%s';".formatted(Date.valueOf(date));

        ArrayList<ArrayList<String>> sum = connectivity.selectValues(query);

        if (sum != null) {
            connectivity.close();
            try {
                return sum.get(0).get(0);
            } catch (NullPointerException | NumberFormatException e) {
                System.err.println("There was an issue with the query: " + e.getMessage());
            }
        }
        connectivity.close();
        return null;
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

package management;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Adam Rezzag Salem
 * @date 22nd February 2024
 * This interface will give the management team the necessary information to continue with running their system
 */
public interface ManagementInterface {

    /**
     * Gets the order details for the inputted order ID
     * @param orderId
     * @return Order object which will give the details required from the order
     * @throws SQLException
     */
    public Order getOrder(int orderId) throws SQLException;

    /**
     * The method returns a list of the top 5 most popular dishes in the restaurant
     * @return List<String>
     * @throws SQLException
     */
    public List<String> getPopularDishes() throws SQLException;

    /**
     * Method which returns the total amount spent on a given order
     * @param orderId
     * @return the amount paid for a given order
     * @throws SQLException
     */
    public double getAmount(int orderId) throws SQLException;

    /**
     * This gives the total amount the restaurant gained on a given day
     *
     * @param date
     * @return the total made in a single day at the restaurant
     * @throws SQLException
     */
    public String getTotal(String date) throws SQLException;

    /**
     * Total number of customers for a booking that was made
     * @param bookingId
     * @return this will give the total number of customers for a single booking
     * @throws SQLException
     */
    public int getCustomers(int bookingId) throws SQLException;

    /**
     * A list of all the dishes ordered for a given booking
     * @param orderId
     * @return the list of dishes for a given order at a table(s)
     * @throws SQLException
     */
    public List<String> getCustomerDishes(int orderId) throws SQLException;

}

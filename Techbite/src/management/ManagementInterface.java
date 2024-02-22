package management;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Adam Rezzag Salem
 * @date 22nd February 2024
 * @description This interface will give the management team the necessary information to continue with running their system
 */
public interface ManagementInterface {

    /**
     *
     * @param orderId
     * @return Order object which will give the details required from the order
     * @throws SQLException
     */
    public Order getOrder(int orderId) throws SQLException;

    /**
     *
     * @return List<String> of the top 5 most popular dishes in the restaurant
     * @throws SQLException
     */
    public List<String> getPopularDishes() throws SQLException;

    /**
     *
     * @param orderId
     * @return the amount paid for a given order
     * @throws SQLException
     */
    public double getAmount(int orderId) throws SQLException;

    /**
     *
     * @param date
     * @return the total made in a single day at the restaurant
     * @throws SQLException
     */
    public double getTotal(String date) throws SQLException;

    /**
     *
     * @param bookingId
     * @return this will give the total number of customers for a single booking
     * @throws SQLException
     */
    public int getCustomers(int bookingId) throws SQLException;

    /**
     *
     * @param orderId
     * @return the list of dishes for a given order at a table(s)
     * @throws SQLException
     */
    public List<String> getCustomerDishes(int orderId) throws SQLException;

}

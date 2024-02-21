import management.Order;

import java.util.Date;

/**
 * @author Adam Rezzag Salem
 * @version 1.0
 * @since 16th February 2024
 */
public interface FrontHouseInterface {

    /*
    The other systems require the information on the customer and their details,
    the orders made and the total amount made in the evening
     */

    /**
     *
     * @param name
     * @param phoneNumber
     * @return returns a Customer object
     */
    public Customer getCustomer(int customerId);

    public Customers getAllCustomers();

    public Order getOrder(int orderId);

    public DailySale getDailySale(Date date);
}

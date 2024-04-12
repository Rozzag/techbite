package kitchen;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * The interface will allow the kitchen staff to access details which are important for them to
 * run as effectively as possible
 *
 * @author Adam Rezzag Salem
 * @date 22nd February 2024
 *
 */
public interface KitchenInterface {

    /**
     * This method allows the kitchen staff to know how much of each dish they will make
     * @param orderId type int
     * @return A map which will give the dishes required alongside the quantities needed to be made
     */
    public Map<String,Integer> getDishesAndQuantity(int orderId) throws SQLException;

    /**
     * Returns a list of orderIDs for the current day
     * @param
     * @return List<Integer>
     * @throws SQLException
     */
    public List<Integer> getOrderId() throws SQLException;

    /**
     * Any additional requirements from the customer's side are met
     * @param orderId
     * @return String
     * @throws SQLException
     */
    public String additionalRequirements(int orderId) throws SQLException;

}

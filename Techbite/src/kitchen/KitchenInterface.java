package kitchen;

import java.sql.SQLException;
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
     * Returns the orderId of a given customer for further information to be gotten
     * @param customerName
     * @return int
     * @throws SQLException
     */
    public int getOrderId(String customerName) throws SQLException;

    /**
     * Any additional requirements from the customer's side are met
     * @param orderId
     * @return String
     * @throws SQLException
     */
    public String additionalRequirements(int orderId) throws SQLException;

}

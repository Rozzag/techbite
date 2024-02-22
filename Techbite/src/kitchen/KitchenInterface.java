package kitchen;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface KitchenInterface {

    public Map<String,Integer> getDishesAndQuantity(int orderId) throws SQLException;

    public int getOrderId(String customerName) throws SQLException;

    public String additionalRequirements(int orderId) throws SQLException;

}

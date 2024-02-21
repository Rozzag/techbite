package kitchen;

import java.util.List;

public interface KitchenInterface {

    public List<String> getDishes(int orderId);

    public int getQuantity(int orderId);

    public int getOrderId();

    public String additionalRequirements(int orderId);

    public Order getAllOrders();
}

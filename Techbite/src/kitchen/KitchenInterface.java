package kitchen;

public interface KitchenInterface {

    public String[] getDishes(int orderId);

    public int getQuantity(int orderId);

    public int getOrderId();

    public String additionalRequirements(int orderId);

    public Order getAllOrders();
}

package kitchen;

import java.util.List;

public class KitchenImplementation implements KitchenInterface{

    @Override
    public List<String> getDishes(int orderId) {
        return null;
    }

    @Override
    public int getQuantity(int orderId) {
        return 0;
    }

    @Override
    public int getOrderId() {
        return 0;
    }

    @Override
    public String additionalRequirements(int orderId) {
        return null;
    }

    @Override
    public Order getAllOrders() {
        return null;
    }
}

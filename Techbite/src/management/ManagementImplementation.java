package management;

public class ManagementImplementation implements ManagementInterface {

    @Override
    public Order getOrder(int orderId) {
        return null;
    }

    @Override
    public String[] getDishes(int orderId) {
        return new String[0];
    }

    @Override
    public double getAmount(int orderId) {
        return 0;
    }

    @Override
    public int getCustomers(int orderId) {
        return 0;
    }
}

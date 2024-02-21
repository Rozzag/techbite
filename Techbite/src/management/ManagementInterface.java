package management;

public interface ManagementInterface {

    public Order getOrder(int orderId);

    public String[] getDishes(int orderId);

    public double getAmount(int orderId);

    public int getCustomers(int orderId);

}

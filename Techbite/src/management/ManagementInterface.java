package management;

import java.util.List;

public interface ManagementInterface {

    public Order getOrder(int orderId);

    public List<String> getPopularDishes();

    public double getAmount(int orderId);

    public double getTotal(String date);

    public int getCustomers(int bookingId);

}

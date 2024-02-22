package management;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ManagementInterface {

    public Order getOrder(int orderId);

    public List<String> getPopularDishes() throws SQLException;

    public double getAmount(int orderId) throws SQLException;

    public double getTotal(String date) throws SQLException;

    public int getCustomers(int bookingId) throws SQLException;

    public List<String> getCustomerDishes(int orderId) throws SQLException;

}

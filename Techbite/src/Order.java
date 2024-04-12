import java.util.List;

public class Order {
    int orderID;
    Table table;
    List<OrderItem> orderItems;
    boolean orderStatus;

    public Order(Table table, List<OrderItem> orderItems) {
        this.table = table;
        this.orderItems = orderItems;
    }
}

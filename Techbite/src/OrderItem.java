public class OrderItem {
    SingularMenuItem menuItem;
    int quantity;
    String additionalInfo;

    public OrderItem(int quantity, String additionalInfo, SingularMenuItem menuItem) {
        this.quantity = quantity;
        this.additionalInfo = additionalInfo;
        this.menuItem = menuItem;
    }
}

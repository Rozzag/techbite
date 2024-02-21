import java.util.Date;
import java.util.List;
import java.util.Random;

public class Order {

        private int orderId;
        private int tableNumber;
        private Date orderDate;
        private List<String> specialRequest;

    public Order(int tableNumber) {
        this.tableNumber = tableNumber;
        generateId();
    }

    public Order(int tableNumber, List<String> specialRequest) {
        this.tableNumber = tableNumber;
        this.specialRequest = specialRequest;
        generateId();
    }

    private void generateId() {
      // query for the last id and increment by 1
        // assign id to this number
    }



}

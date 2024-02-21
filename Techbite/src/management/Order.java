package management;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class Order {

        private int orderId;
        private int tableNumber;
        private Date orderDate;
        private List<String> specialRequest;

    public Order(int orderId) {
        Random random = new Random();
        this.tableNumber = random.nextInt(15);
        generateId();
    }

    public Order(int orderId, List<String> specialRequest) {
        Random random = new Random();
        this.tableNumber = random.nextInt(15);
        this.specialRequest = specialRequest;
        generateId();
    }

    private void generateId() {
      // query for the last id and increment by 1
        // assign id to this number
    }



}

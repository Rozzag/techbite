package management;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author Adam Rezzag Salem
 * @date 22nd February 2024
 * @description This gives the details of a guest's order
 */
public class Order {

        private int orderId;
        private int tableNumber;
        private String orderDate;
        private List<String> specialRequest;

    public Order(int orderId) {
        Random random = new Random();
        this.tableNumber = random.nextInt(15);
        generateId();
    }

    public Order(int orderId, int tableNumber, String orderDate, List<String> specialRequest) {
        Random random = new Random();
        this.tableNumber = this.tableNumber;
        this.specialRequest = specialRequest;
        generateId();
    }

    private void generateId() {
      // query for the last id and increment by 1
        // assign id to this number
    }



}

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
        private int quantity;
        private String specialRequest;

        public static int id = 1;

        public Order(int orderId, int tableNumber, int quantity, String specialRequest) {
            this.orderId = orderId;
            tableNumber = tableNumber;
            this.quantity = quantity;
            this.specialRequest = specialRequest;
        }

    public Order(int orderId) {
        Random random = new Random();
        this.tableNumber = random.nextInt(15);
        generateId();
    }



    private void generateId() {
      // query for the last id and increment by 1
        // assign id to this number
        id++;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public static int getId() {
        return id;
    }
}

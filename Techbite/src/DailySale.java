import java.util.Date;
import java.util.HashMap;

public class DailySale {

    private Date date;
    private double total;
    private int numCustomers;
    private HashMap<String, Integer> orders;

    public Date getDate() {
        return date;
    }

    public double getTotal() {
        return total;
    }

    public int getNumCustomers() {
        return numCustomers;
    }

    public HashMap<String, Integer> getOrders() {
        return orders;
    }

    // The DailySales will connect to the database in order to read from the other tables and then
    // write it on to the DailySales relation.
}

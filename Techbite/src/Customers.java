import java.util.Set;

public class Customers {

    private Set<Customer> customers;

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer c) {
        this.customers.add(c);
    }
}

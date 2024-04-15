import java.util.Date;

public class Customer {

    private String name;
    private int customerId;
    private boolean payCard;
    private String phoneNumber;


    // staff member takes all the information from the customer when first registering to the restaurant
    public Customer(String name, String phoneNumber, boolean payCard, int customerId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.payCard = payCard;
        this.cardNumber = cardNumber;
    }

    // another constructor for when the costumer is already registered and so no need for extra details
    public Customer(String name, boolean payCard, int customerId) {
        this.name = name;
        this.payCard = payCard;
        this.customerId = customerId;
    }

    // customer may want to change their card payment preferences
    public boolean updateCardDetails(int cardNumber, Date expiryDate){

        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;

        return true;
    }


    // customer may change their mind for the method of payment
    public void updatePaymentMethod(boolean payCard) {
        this.payCard = payCard;

        if (this.payCard == true) {
            System.out.println("Customer " + this.customerId + " has selected to pay by card.");
        } else {
            System.out.println("Customer" + this.customerId + " has selected to pay by cash.");
        }
    }

    public String getName() {
        return name;
    }

    public int getCustomerId() {
        return customerId;
    }

    public boolean isPayCard() {
        return payCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}

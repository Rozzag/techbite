public interface FrontHouseInterface {

    /*
    The other systems require the information on the customer and their details,
    the orders made and the total amount made in the evening
     */

    // I may change this to reflect whether a collection class is more suitable to be used to access the customer details
    public Customer getCustomer(String name, String phoneNumber);

    public Booking getBooking(int bookingId);
}

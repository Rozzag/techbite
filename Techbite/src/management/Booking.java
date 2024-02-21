package management;

import java.util.Date;

public class Booking {

    /*
    This will allow staff to add details pertaining to the nature of the booking and whether
    certain facilities will be required.
     */

    private int bookingId;
    private boolean requiresWheelChair;
    private Date bookingDate;
    private String bookingTime;
    private int numCustomers;

    // this variable may changed in the future but it encompasses the extra things the customers may need
    private String additionalDetails;

    public Booking(int bookingId, boolean requiresWheelChair, Date bookingDate,
                   String bookingTime, int numCustomers, String additionalDetails) {
        this.bookingId = bookingId;
        this.requiresWheelChair = requiresWheelChair;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.numCustomers = numCustomers;
        this.additionalDetails = additionalDetails;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public boolean isRequiresWheelChair() {
        return requiresWheelChair;
    }

    public void setRequiresWheelChair(boolean requiresWheelChair) {
        this.requiresWheelChair = requiresWheelChair;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public int getNumCustomers() {
        return numCustomers;
    }

    public void setNumCustomers(int numCustomers) {
        this.numCustomers = numCustomers;
    }
}

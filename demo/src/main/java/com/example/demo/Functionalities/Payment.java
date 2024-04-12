package com.example.demo.Functionalities;

import java.util.Date;

public class Payment {

    /*
    CREATE TABLE Payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    payment_date_and_time TIMESTAMP NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);
     */

    private int paymentId;
    private int orderId;
    private boolean isCard;
    private double amount;
    private Date date;

    public Payment(int paymentId, int orderId, boolean isCard, double amount, Date date) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.isCard = isCard;
        this.amount = amount;
        this.date = date;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public boolean isCard() {
        return isCard;
    }

    public void setCard(boolean card) {
        isCard = card;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

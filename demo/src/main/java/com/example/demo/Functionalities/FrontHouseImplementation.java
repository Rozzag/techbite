package com.example.demo.Functionalities;

import com.example.demo.Functionalities.Customer;
import com.example.demo.Functionalities.FrontHouseInterface;
import management.Order;

import java.util.Date;

public class FrontHouseImplementation implements FrontHouseInterface {


    @Override
    public Customer getCustomer(int customerId) {
        return null;
    }

    @Override
    public Customers getAllCustomers() {
        return new Customers();
    }


    @Override
    public Order getOrder(int orderId) {
        Order order = new Order(orderId);
        return order;
    }

    @Override
    public DailySale getDailySale(Date date) {
        return new DailySale();
    }
}

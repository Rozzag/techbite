import kitchen.Connectivity;
import kitchen.KitchenImplementation;
import management.ManagementImplementation;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws SQLException {

        ManagementImplementation MI = new ManagementImplementation();
        KitchenImplementation KI = new KitchenImplementation();
        System.out.println(MI.getCustomers(1));
        System.out.println(MI.getAmount(1));
        System.out.println(MI.getCustomerDishes(1));
        System.out.println(MI.getOrder(1));
        System.out.println(MI.getPopularDishes());
        System.out.println(MI.getTotal("2024-04-11"));

        System.out.println(KI.getOrderId("Michael Johnson"));
        System.out.println(KI.additionalRequirements(2));
        System.out.println(KI.getDishesAndQuantity(2));


    }
}

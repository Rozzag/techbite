package com.example.demo.supportclasses;

import java.util.ArrayList;
import java.util.HashMap;

// the order class contains the information for each order which can be accessed from the
// database
public class Order {
    private Dish d;
    private HashMap<String, String> ingredientsQuant;
    private String additionalInfo;

    // the order constructor receives a dish, ingredients and any other additional informatinn
    // to allows for easy data manipulation of the database
    public Order(Dish d, HashMap<String, String> ingredientsQuant, String additionalInfo) {
        this.d = d;
        this.ingredientsQuant = ingredientsQuant;
        this.additionalInfo = additionalInfo;
    }

    public Dish getD() {
        return d;
    }

    public HashMap<String, String> getIngredientsQuant() {
        return ingredientsQuant;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}

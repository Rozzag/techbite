package com.example.demo.supportclasses;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    private Dish d;
    private HashMap<String, String> ingredientsQuant;
    private String additionalInfo;

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

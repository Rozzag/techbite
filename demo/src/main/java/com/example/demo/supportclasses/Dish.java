package com.example.demo.supportclasses;

import com.example.demo.connectivity.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dish {
    private int itemID;
    private String name;
    private String ingredients;
    private double price;
    private String allergens;
    private int availability;
    private Database db;

    public Dish(){this.db = new Database();}

    public void getDishByName(String name) throws SQLException {
        ArrayList<ArrayList<String>> dishValues = this.db.selectValues(String.format("SELECT * FROM MenuItem WHERE name='%s'", name));

        for(ArrayList<String> arr : dishValues){
            this.itemID = Integer.parseInt(arr.get(0));
            this.name = arr.get(1);
            this.ingredients = arr.get(2);
            this.price = Double.parseDouble(arr.get(3));
            this.allergens = arr.get(4);
            this.availability = Integer.parseInt(arr.get(5));
        }
    }

    public void getDishById(int id) throws SQLException {
        ArrayList<ArrayList<String>> dishValues = this.db.selectValues(String.format("SELECT * FROM MenuItem WHERE item_id=%d", id));

        for(ArrayList<String> arr : dishValues){
            this.itemID = Integer.parseInt(arr.get(0));
            this.name = arr.get(1);
            this.ingredients = arr.get(2);
            this.price = Double.parseDouble(arr.get(3));
            this.allergens = arr.get(4);
            this.availability = Integer.parseInt(arr.get(5));
        }
    }

    // Checks if the dish is available
    public boolean isDishAvailable(){
        return availability == 1;
    }

    // Gets the price of the dish
    public double getPrice() {
        return price;
    }

    // Takes in a string and checks if the dish contains that allergen
    public boolean doesContain(String s){
        String input = allergens.replace("Contains ", "");
        String[] parts = input.split(",");

        // Start from index 1 to skip "Contains"
        List<String> allerg = new ArrayList<>(Arrays.asList(parts));

        return allerg.contains(s);

    }

    public String getName() {
        return name;
    }

    public String[] getIngredients() {
        String input = ingredients.replace("and", ",");
        String[] parts = input.split(", ");

        return parts;
    }
}

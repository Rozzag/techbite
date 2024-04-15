package com.example.demo.supportclasses;

import com.example.demo.connectivity.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Integer> itemIDs;
    private List<String> names;
    private List<String> ingredients;
    private List<Double> prices;
    private List<String> allergens;
    private int menuLength;
    private Connection conn;

    // Getting all menu items from database to then add to containers for the menu page arraylist
    public Menu() throws SQLException {
        itemIDs = new ArrayList<>();
        names = new ArrayList<>();
        ingredients = new ArrayList<>();
        prices = new ArrayList<>();
        allergens = new ArrayList<>();

        Database c = new Database();
        ArrayList<ArrayList<String>> menuValues = c.selectValues("SELECT * FROM MenuItem");

        for (ArrayList<String> rows : menuValues){
            itemIDs.add(Integer.parseInt(rows.get(0)));
            names.add(rows.get(1));
            ingredients.add(rows.get(2));
            prices.add(Double.parseDouble(rows.get(3)));
            allergens.add(rows.get(4));
        }
        menuLength = itemIDs.size();
    }

    public List<String> getNames() {
        return names;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public List<String> getAllergens() {
        return allergens;
    }
}

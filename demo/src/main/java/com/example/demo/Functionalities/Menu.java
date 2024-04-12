package com.example.demo.Functionalities;

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

    // Getting all menu items from db
    public Menu() throws SQLException {
        itemIDs = new ArrayList<>();
        names = new ArrayList<>();
        ingredients = new ArrayList<>();
        prices = new ArrayList<>();
        allergens = new ArrayList<>();

        Connectivity c = new Connectivity();
        conn = c.getConnection();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM MenuItem");

        while (rs.next()){
            itemIDs.add(rs.getInt("item_id"));
            names.add(rs.getString("name"));
            ingredients.add(rs.getString("description"));
            prices.add(rs.getDouble("price"));
            allergens.add(rs.getString("allergen_info"));
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

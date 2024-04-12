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
    private Connection conn;

    public Dish(){}

    public void getDishByName(String name) throws SQLException {
        Connectivity c = new Connectivity();
        Connection conn = c.getConnection();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(String.format("SELECT * FROM MenuItem WHERE name='%s'", name));

        while(rs.next()){
            itemID = rs.getInt("item_id");
            this.name = name;
            ingredients= rs.getString("description");
            price = rs.getDouble("price");
            allergens = rs.getString("allergen_info");
            availability = rs.getInt("availability");
        }
    }

    public void getDishById(int id) throws SQLException {
        Connectivity c = new Connectivity();
        Connection conn = c.getConnection();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(String.format("SELECT * FROM MenuItem WHERE item_id=%d", id));

        while(rs.next()){
            itemID = id;
            name = rs.getString("name");
            ingredients= rs.getString("description");
            price = rs.getDouble("price");
            allergens = rs.getString("allergen_info");
            availability = rs.getInt("availability");
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

    public String getIngredients() {
        return ingredients;
    }
}

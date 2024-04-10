import java.sql.*;
import java.util.List;

public class SingularMenuItem {
    int itemID;
    String name;
    String ingredients;
    double price;
    String allergens;
    Connection conn;

    public SingularMenuItem(int itemID, String name, String ingredients, double price, String allergens) {
        this.itemID = itemID;
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
        this.allergens = allergens;
    }
}

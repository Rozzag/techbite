import java.sql.*;
import java.util.List;

public class MenuItems {
    List<Integer> itemIDs;
    List<String> names;
    List<String> ingredients;
    List<Double> prices;
    List<String> allergens;
    int menuLength;
    Connection conn;

    public MenuItems() throws SQLException {
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


}

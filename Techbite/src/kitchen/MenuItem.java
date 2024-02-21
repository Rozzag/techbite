package kitchen;

public class MenuItem {

    private int itemId;
    private String name;
    private String description;
    private double price;
    private String allergen_info;
    private boolean available;

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getAllergen_info() {
        return allergen_info;
    }

    public boolean isAvailable() {
        // we will have to check from the required interfaces on whether the item
        // is available.
        return available;
    }
}

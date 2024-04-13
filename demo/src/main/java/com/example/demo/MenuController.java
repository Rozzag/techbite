package com.example.demo;

import com.example.demo.supportclasses.Dish;
import com.example.demo.supportclasses.Menu;
import com.example.demo.supportclasses.Order;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private ListView<String> menuItems;
    @FXML
    private VBox ingredients;
    @FXML
    private TextArea additionalInfo;
    @FXML
    private Button addButton;
    @FXML
    private VBox summary;
    @FXML
    private Button submitButton;
    @FXML
    private Label totalCost;

    private String currItem;
    private List<Order> orders = new ArrayList<>();
    private HashMap<String, String> ingredientsQuant;

    @FXML
    private void handleAddButtonAction() throws SQLException {
        // Add new order to the order list
        Dish d = new Dish();
        d.getDishByName(currItem);

        orders.add(new Order(d, ingredientsQuant, additionalInfo.getText()));
        updateOrderSummary();
    }

    // Show the new orders added
    private void updateOrderSummary(){
        summary.getChildren().clear();
        double cost = 0;
        for(Order o: orders){
            HBox row = new HBox();
            Label rowLabel = new Label(o.getD().getName());
            row.getChildren().add(rowLabel);

            Button removeButton = new Button("REMOVE");
            removeButton.setOnAction(event -> {
                orders.remove(o);
                updateOrderSummary();
            });

            row.getChildren().add(removeButton);

            cost += o.getD().getPrice();

            summary.getChildren().add(row);
        }

        totalCost.setText("Total cost: £" + cost);

    }

    @FXML
    private void handleSubmitButtonAction(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Menu menu = new Menu();
            String[] items = menu.getNames().toArray(new String[0]);
            menuItems.getItems().addAll(items);

            menuItems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                Dish d = new Dish();
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    ingredients.getChildren().clear();
                    currItem = menuItems.getSelectionModel().getSelectedItem();
                    ingredientsQuant = new HashMap<>();
                    try {
                        // Giving radio buttons to choose quantity for ingredients
                        d.getDishByName(currItem);
                        for(String ing: d.getIngredients()){
                            HBox row = new HBox();
                            Label rowLabel = new Label(ing);
                            row.getChildren().add(rowLabel);
                            String labelText = rowLabel.getText();

                            // Radio buttons with ToggleGroup
                            ToggleGroup group = new ToggleGroup();

                            RadioButton rb1 = new RadioButton("None");
                            RadioButton rb2 = new RadioButton("Regular");
                            RadioButton rb3 = new RadioButton("Extra");

                            rb1.setToggleGroup(group);
                            rb2.setToggleGroup(group);
                            rb3.setToggleGroup(group);

                            rb2.setSelected(true);
                            ingredientsQuant.put(labelText, "Regular");
                            // Relates the label with their corresponding radio button value
                            rb1.setOnAction(event -> ingredientsQuant.put(labelText, "None"));
                            rb2.setOnAction(event -> ingredientsQuant.put(labelText, "Regular"));
                            rb3.setOnAction(event -> ingredientsQuant.put(labelText, "Extra"));

                            row.getChildren().addAll(rb1, rb2, rb3);

                            ingredients.getChildren().add(row);

                        }

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

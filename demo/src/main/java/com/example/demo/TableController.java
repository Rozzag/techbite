package com.example.demo;

import com.example.demo.connectivity.Database;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// this controller handles the tables displayed on the table page to assign tables as either
// booked or not
public class TableController {

    @FXML
    private Pane pane;

    @FXML
    private Label label1;

    @FXML
    private Label label10;

    @FXML
    private Label label11;

    @FXML
    private Label label12;

    @FXML
    private Label label13;

    @FXML
    private Label label14;

    @FXML
    private Label label15;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;

    @FXML
    private Label label6;

    @FXML
    private Label label7;

    @FXML
    private Label label8;

    @FXML
    private Label label9;

    @FXML
    private Rectangle table1;

    @FXML
    private Rectangle table10;

    @FXML
    private Rectangle table11;

    @FXML
    private Rectangle table12;

    @FXML
    private Rectangle table13;

    @FXML
    private Rectangle table14;

    @FXML
    private Rectangle table15;

    @FXML
    private Rectangle table2;

    @FXML
    private Rectangle table3;

    @FXML
    private Rectangle table4;

    @FXML
    private Rectangle table5;

    @FXML
    private Rectangle table6;

    @FXML
    private Rectangle table7;

    @FXML
    private Rectangle table8;

    @FXML
    private Rectangle table9;

    private HashMap<String, Boolean> tableAvailability = new HashMap<>(15);

    private ArrayList<Rectangle> rectangles = new ArrayList<>();

    private TablePageController tablePageController;

    // initialises the table layout by showing the tables which are available for the current time
    public void initialize() throws SQLException {
     assignTableAvailability();

    }

    // all tables are assigned green as available except if the table id is found in the
    // Booking schema. If so, the table is assigned red as booked
    public void assignTableAvailability() throws SQLException {
        rectangles.add(table9);
        rectangles.add(table8);
        rectangles.add(table7);
        rectangles.add(table6);
        rectangles.add(table5);
        rectangles.add(table4);
        rectangles.add(table3);
        rectangles.add(table2);
        rectangles.add(table1);
        rectangles.add(table10);
        rectangles.add(table11);
        rectangles.add(table12);
        rectangles.add(table13);
        rectangles.add(table14);
        rectangles.add(table15);


        // initialize hashmap to true as all are available and false if found in the query

        for (int i = 1; i <= tableAvailability.size(); i++) {
            tableAvailability.put("table" + String.valueOf(i), true);
        }
        // query to get the availability of the tables
        String bookedTables = "select table_id from Grouping as g join Booking as b on b.booking_id = g.booking_id WHERE CURRENT_TIME between time(b.booking_date_time) and date_add(time(b.booking_date_time), INTERVAL 30 minute) and date(b.booking_date_time)=CURRENT_DATE;";

        Database db = new Database();

        ArrayList<ArrayList<String>> bookedValues = db.selectValues(bookedTables);

        if (bookedValues.isEmpty()) {
            db.close();
            return;
        }


        for (ArrayList<String> value : bookedValues) {
            String tableId = "table" + value.get(0);
            tableAvailability.put(tableId, false);
        }


        // assign the table to red if unavailable
        ArrayList<Rectangle> movingrectangle = new ArrayList<>();
        for (Map.Entry<String, Boolean> val : tableAvailability.entrySet()) {
            // get the table and check if it's available
            boolean available = val.getValue();
            if (!available) {
                String tableId = val.getKey();
                int i = 0;
                for (Rectangle rect : rectangles) {
                    if (tableId.equals(rect.getId())) {
                        rect.setFill(Color.RED);
                        movingrectangle.add(rect);
                    }
                }
            }
        }
        db.close();

    }


    // this method coordinates with the other controller in order to sync the
    // button with the updating of the availability of the table
    public void setTablePageController(TablePageController tablePageController) {
        this.tablePageController = tablePageController;
    }
}

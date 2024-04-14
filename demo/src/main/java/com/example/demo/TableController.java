package com.example.demo;

import com.example.demo.connectivity.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableController {

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

    public void initialize() throws SQLException {


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

        for (int i=1; i<=tableAvailability.size(); i++) {
            tableAvailability.put("table" + String.valueOf(i), true);
        }
        // query to get the availability of the tables
//        String bookedTables = "select table_id from Orders as o join Booking as b on b.booking_id = o.booking_id WHERE CURRENT_TIMESTAMP between time(b.booking_date_time) and date_add(b.booking_date_time, INTERVAL 30 minute) and b.booking_date_time=CURRENT_DATE;";
            String bookedTables = "select table_id from Orders as o join Booking as b on b.booking_id = o.booking_id WHERE time('20:00:00') between time(b.booking_date_time) and date_add(time(b.booking_date_time), INTERVAL 30 minute) and date(b.booking_date_time)='2024-04-13';";
        Database db = new Database();

        ArrayList<ArrayList<String>> bookedValues = db.selectValues(bookedTables);

        if (bookedValues.isEmpty()) {
            return;
        }

        for (ArrayList<String> value : bookedValues) {
            String tableId = "table"+value.get(0);
            tableAvailability.put(tableId, false);
        }

        // assign the table to red if unavailable
        for (Map.Entry<String, Boolean> val : tableAvailability.entrySet()) {
            // get the table and check if it's available
            boolean available = val.getValue();
            if (!available) {
                String tableId = val.getKey();
                for (Rectangle rect : rectangles) {
                    if (tableId.equals(rect.getId())) {
                        rect.setFill(Color.RED);
                    }
                }
            }
        }

        // only allow the available tables to be moved
        for (Rectangle rect : rectangles) {
            if (rect.getFill().equals(Color.GREEN)) {
                rect.setOnMousePressed(event -> {
                    rect.setUserData(new double[]{event.getX(), event.getY()});
                });

                rect.setOnMouseDragged(event -> {
                    double[] mousePos = (double[]) rect.getUserData();
                    double deltaX = event.getX() - mousePos[0];
                    double deltaY = event.getY() - mousePos[1];

                    System.out.println("Dragging " + rect.getId());

                    rect.setX(rect.getX() + deltaX);
                    rect.setY(rect.getY() + deltaY);

                    // Update the mouse position
                    mousePos[0] = event.getX();
                    mousePos[1] = event.getY();
                });

                rect.setOnMouseReleased(event -> {
                    double[] mousePos = (double[]) rect.getUserData();
                    double deltaX = event.getX() - mousePos[0];
                    double deltaY = event.getY() - mousePos[1];

                    rect.setX(mousePos[0] + deltaX);
                    rect.setY(mousePos[1] + deltaY);

                    // Update the mouse position
                    mousePos[0] = event.getX();
                    mousePos[1] = event.getY();
                });
            }
        }


    }

}

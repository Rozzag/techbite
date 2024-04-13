package com.example.demo;

import com.example.demo.supportclasses.Staff;
import javafx.beans.Observable;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {
    @FXML
    private TableColumn<Staff, String> names;

    @FXML
    private TableColumn<Staff, String> roles;

    @FXML
    private TableView<Staff> staffTable;

    private ObservableList<Staff> staff = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            List<Staff> staffList = Staff.getAllStaff();

            for(Staff s: staffList){
                staff.add(s);
            }

            names.setCellValueFactory(new PropertyValueFactory<Staff, String>("name")); // Assuming you have getName()
            roles.setCellValueFactory(new PropertyValueFactory<Staff, String>("role")); // Assuming you have getRole()

            staffTable.setItems(staff);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

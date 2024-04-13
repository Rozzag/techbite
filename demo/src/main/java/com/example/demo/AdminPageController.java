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
import org.w3c.dom.ls.LSOutput;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {
    @FXML
    private TableColumn<Staff, Integer> IDs;
    @FXML
    private TableColumn<Staff, String> names;

    @FXML
    private TableColumn<Staff, String> roles;

    @FXML
    private TableView<Staff> staffTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Staff> staffList = Staff.getAllStaff();
            ObservableList<Staff> staff = FXCollections.observableArrayList(staffList);

            for(Staff s: staffList){
                System.out.println(s.getRole());
                if(s.getRole() != "Maitre d"){
                    staff.add(s);
                }
            }

            IDs.setCellValueFactory(new PropertyValueFactory<Staff, Integer>("staffID")); // Assuming you have getName()
            names.setCellValueFactory(new PropertyValueFactory<Staff, String>("name")); // Assuming you have getName()
            roles.setCellValueFactory(new PropertyValueFactory<Staff, String>("role")); // Assuming you have getRole()

            staffTable.setItems(staff);

        } catch (SQLException e) {
            System.out.println(3);
            throw new RuntimeException(e);
        }
    }
}

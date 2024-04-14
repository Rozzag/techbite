package com.example.demo;

import com.example.demo.supportclasses.Staff;
import javafx.beans.Observable;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.ls.LSOutput;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
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
    @FXML
    private Label usernamePass;
    @FXML
    private Label usernameRole;
    @FXML
    private PasswordField newPassText;
    @FXML
    private ComboBox<String> roleDrop;
    @FXML
    private Button delButton;
    @FXML
    private Button changePass;
    @FXML
    private Button changeRole;
    @FXML
    private Button addMemberButton;
    @FXML
    private TextField newMName;
    @FXML
    private TextField newMUsername;
    @FXML
    private PasswordField newMPass;
    @FXML
    private ComboBox<String> newMRoleDrop;

    private List<Staff> staffList;
    private ObservableList<Staff> staff;

    private Staff selectedStaff;

    @FXML
    private void handleDelButton() throws SQLException {
        staff.remove(selectedStaff);
        selectedStaff.delUser();

        // TODO: Errors in when trying to delete staff with their staff_id in visits
        staffTable.getItems().clear();
        doTable();
    }

    @FXML
    private void handleChangePass() throws SQLException {
        selectedStaff.updatePassword(selectedStaff.getStaffID(), newPassText.getText());
        newPassText.clear();
    }

    @FXML
    private void handleChangeRole() throws SQLException {
        selectedStaff.updateRole(selectedStaff.getStaffID(), roleDrop.getValue());
        selectedStaff.setRole(roleDrop.getValue());

        staffTable.getItems().clear();

        doTable();
    }

    @FXML
    private void addNewMemberButton() throws SQLException {
        if (!newMName.getText().isEmpty() && !newMUsername.getText().isEmpty() && !newMPass.getText().isEmpty() && newMRoleDrop.getValue() != null){
            staff.add(Staff.addMember(newMName.getText(), newMUsername.getText(), newMPass.getText(), newMRoleDrop.getValue()));

            staffTable.getItems().clear();
            doTable();
        } else {
            System.out.println("Not all field have been filled!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            staffList = Staff.getAllStaff();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        doTable();
    }

    private void doTable(){
        staff = FXCollections.observableArrayList();

        for(Staff s: staffList){
            if(!s.getRole().equals("Maitre d")){
                staff.add(s);
            }
        }

        // writing rows in the table
        IDs.setCellValueFactory(new PropertyValueFactory<Staff, Integer>("staffID")); // Assuming you have getName()
        names.setCellValueFactory(new PropertyValueFactory<Staff, String>("name")); // Assuming you have getName()
        roles.setCellValueFactory(new PropertyValueFactory<Staff, String>("role")); // Assuming you have getRole()

        staffTable.setItems(staff);

        // Getting the selected staff memeber
        staffTable.setOnMouseClicked(event -> {
            int selectedIndex = staffTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                selectedStaff = staffTable.getItems().get(selectedIndex);
            }

            usernamePass.setText(selectedStaff.getName());
            usernameRole.setText(selectedStaff.getName());
        });

        ObservableList<String> roles = FXCollections.observableArrayList(new String[] {"Waiter", "Sommelier"});

        // Setting the drop-down menu for new roles
        roleDrop.setItems(roles);
        newMRoleDrop.setItems(roles);
    }


}

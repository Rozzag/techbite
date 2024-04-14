package com.example.demo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TablePageController {

    @FXML
    private TextArea additionalInfoField;

    @FXML
    private Button allocateButton;

    @FXML
    private ComboBox<String> guestsField;

    @FXML
    private ComboBox<String> initialTableField;

    @FXML
    private ComboBox<String> secondTableField;

    @FXML
    private ComboBox<String> thirdTableField;


    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumberField;

    public void initialize() {
        // disable the allocate button
        allocateButton.setDisable(true);

        // set the combo box for the guests
        guestsField.getItems().addAll(FXCollections.observableArrayList("1","2", "3", "4", "5", "6"));

        // disable all the other combo boxes
        secondTableField.dis
    }


}

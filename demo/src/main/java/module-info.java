module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.demo.supportclasses to javafx.base;



    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.connectivity;
    opens com.example.demo.connectivity to javafx.fxml;
}
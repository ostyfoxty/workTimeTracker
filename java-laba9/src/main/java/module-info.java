module com.example.javalaba9 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javalaba9 to javafx.fxml;
    exports com.example.javalaba9;
}
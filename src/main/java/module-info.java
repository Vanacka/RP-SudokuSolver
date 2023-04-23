module com.example.rpsudokusolver {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rpsudokusolver to javafx.fxml;
    exports com.example.rpsudokusolver;
}
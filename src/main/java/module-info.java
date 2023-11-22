module com.checkers.checkers {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dangnha.checkers to javafx.fxml;
    exports com.dangnha.checkers;
    exports com.dangnha.checkers.constants;
    opens com.dangnha.checkers.constants to javafx.fxml;
}
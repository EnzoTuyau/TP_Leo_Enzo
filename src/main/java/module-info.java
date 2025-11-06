module org.example.tp_leo_enzo {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tp_leo_enzo to javafx.fxml;
    exports org.example.tp_leo_enzo;
}
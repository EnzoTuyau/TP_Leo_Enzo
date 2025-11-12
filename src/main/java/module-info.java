module org.example.tp_leo_enzo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens org.example.tp_leo_enzo to javafx.fxml;
    exports org.example.tp_leo_enzo;
}
module auto.carrent {
    requires javafx.controls;
    requires javafx.fxml;



    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens auto.carrent to javafx.fxml;
    exports auto.carrent;
}
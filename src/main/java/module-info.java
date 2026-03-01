module ranks.restaurant {

    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens ranks.restaurant to javafx.fxml;
    exports ranks.restaurant;
}
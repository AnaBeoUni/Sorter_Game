module com.example.sorter_game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.sorter_game to javafx.fxml;
    exports com.example.sorter_game;
    exports com.example.sorter_game.fxControllers;
    opens com.example.sorter_game.fxControllers to javafx.fxml;
}
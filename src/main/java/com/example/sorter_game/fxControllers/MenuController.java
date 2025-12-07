package com.example.sorter_game.fxControllers;

import com.example.sorter_game.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Button startButton;

    @FXML
    private Button exitButton;

    @FXML
    public void initialize() {
        startButton.setOnAction(e -> switchToGame());
        exitButton.setOnAction(e -> System.exit(0));
    }

    private void switchToGame() {
        try {
            Stage stage = (Stage) startButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("hello-view.fxml")
            );
            Scene gameScene = new Scene(loader.load());

            HelloController controller = loader.getController();
            controller.initScene(gameScene);

            stage.setScene(gameScene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

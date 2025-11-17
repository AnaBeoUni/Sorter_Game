package com.example.sorter_game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        Scene menuScene = new Scene(loader.load(), 600, 600);

        menuScene.getStylesheets().add(
                getClass().getResource("/com/example/sorter_game/style.css").toExternalForm()
        );


        stage.setTitle("Hungry Bunny");
        stage.setScene(menuScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

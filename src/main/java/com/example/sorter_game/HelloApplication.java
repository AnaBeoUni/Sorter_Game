package com.example.sorter_game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 600;
    private static final String WINDOW_TITLE = "Hungry Bunny";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        Scene menuScene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);

        menuScene.getStylesheets().add(
                getClass().getResource("/com/example/sorter_game/style.css").toExternalForm()
        );

        stage.setTitle(WINDOW_TITLE);
        stage.setScene(menuScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

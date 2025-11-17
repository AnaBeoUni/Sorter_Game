package com.example.sorter_game.fxControllers;

import com.example.sorter_game.Models.*;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Objects;

public class HelloController {

    private Image closedMouthBunnySprite;
    private Image openMouthBunnySprite;
    private Image normalCarrotSprite;
    private Image rareCarrotSprite;
    private Image bombSprite;
    private Image bgImage;
    private Image lifeHearSprite;

    @FXML
    public HBox livesContainer;
    @FXML
    private Canvas gameCanvas;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label targetLabel;

    private Conveyor conveyor;
    private Game game;
    private int openLane = 0;

    private Timeline loop;

    // called by FXML after fields are injected
    @FXML
    public void initialize() {

        closedMouthBunnySprite = new Image(getClass().getResourceAsStream("/com/example/sorter_game/images/closedMouthBunny.GIF"));
        openMouthBunnySprite = new Image(getClass().getResourceAsStream("/com/example/sorter_game/images/openMouthBunny.GIF"));
        normalCarrotSprite = new Image(getClass().getResourceAsStream("/com/example/sorter_game/images/normalCarrot.PNG"));
        rareCarrotSprite = new Image(getClass().getResourceAsStream("/com/example/sorter_game/images/rareCarrot.GIF"));
        bombSprite = new Image(getClass().getResourceAsStream("/com/example/sorter_game/images/bomb.GIF"));
        lifeHearSprite = new Image(getClass().getResourceAsStream("/com/example/sorter_game/images/lifeHeart.gif"));
        bgImage = new Image(getClass().getResourceAsStream("/com/example/sorter_game/images/menuBG.PNG"));


        conveyor = new Conveyor();
        game = new Game();

        if (targetLabel != null) {
            targetLabel.setText("Target: " + game.getTargetScore());
        }

        loop = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            if (!game.isGameOver()) {
                conveyor.tick(openLane, game);
                draw();
            } else {
                drawGameOver();
                loop.stop();
            }
        }));
        loop.setCycleCount(Animation.INDEFINITE);
    }

    public void initScene(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.Q) openLane = 0;
            else if (code == KeyCode.W) openLane = 1;
            else if (code == KeyCode.E) openLane = 2;
            else if (code == KeyCode.X) scene.getWindow().hide();
        });

        loop.play();
    }

    private void draw() {
        GraphicsContext g = gameCanvas.getGraphicsContext2D();
        double width = gameCanvas.getWidth();
        double height = gameCanvas.getHeight();

        g.drawImage(bgImage, 0, 0, width, height);

        int rows = Conveyor.CONVEYOR_HEIGHT;
        int lanes = Conveyor.LANE_COUNT;

        double laneWidth = width / (double) lanes;
        double rowHeight = (height - 60) / (double) rows;

        // draw boxes
        for (Box b : conveyor.getBoxes()) {
            int lane = b.getLane();
            int row = b.getRow();

            double x = lane * laneWidth + laneWidth / 2;
            double y = row * rowHeight + rowHeight / 2 + 10;

            double boxW = laneWidth * 0.6;
            double boxH = rowHeight * 0.6;

            switch (b.getItemType()) {
                case NORMAL_CARROT:
                    g.drawImage(normalCarrotSprite, x - boxW / 2, y - boxH / 2, 120, 110);
                    break;
                case BOMB:
                    g.drawImage(bombSprite, x - boxW / 2, y - boxH / 2, 80, 70);
                    break;
                case RARE_CARROT:
                    g.drawImage(rareCarrotSprite, x - boxW / 2, y - boxH / 2, 120, 110);
                    break;
            }
        }

        for (int lane = 0; lane < lanes; lane++) {
            double x = lane * laneWidth + laneWidth / 2;
            double y = height - 20;

            if (lane == openLane) {
                g.drawImage(openMouthBunnySprite, x - 60, y - 90, 120, 120);

            } else {
                g.drawImage(closedMouthBunnySprite, x - 60, y - 90, 120, 120);
            }
        }

        scoreLabel.setText("Score: " + game.getScore());
        updateLifeDisplay();
    }

    private void updateLifeDisplay() {
        livesContainer.getChildren().clear();

        for (int i = 0; i < game.getLives(); i++) {
            ImageView heart = new ImageView(lifeHearSprite);
            heart.setFitWidth(30);   // size of hearts
            heart.setPreserveRatio(true);
            livesContainer.getChildren().add(heart);
        }
    }


    private void drawGameOver() {
        draw();
        GraphicsContext g = gameCanvas.getGraphicsContext2D();
        double width = gameCanvas.getWidth();
        double height = gameCanvas.getHeight();

        g.setFill(Color.color(0, 0, 0, 0.7));
        g.fillRect(0, 0, width, height);

        g.setFill(Color.WHITE);
        String msg = game.hasWon()
                ? "YOU WIN! Score: " + game.getScore()
                : "GAME OVER! Score: " + game.getScore();

        g.fillText(msg, width / 2 - 50, height / 2);
    }
}

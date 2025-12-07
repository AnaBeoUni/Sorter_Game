package com.example.sorter_game.fxControllers;

import com.example.sorter_game.Models.Box;
import com.example.sorter_game.Models.Conveyor;
import com.example.sorter_game.Models.FallingObject;
import com.example.sorter_game.Models.Game;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class HelloController {

    private static final double GAME_LOOP_INTERVAL_MS = 450.0;

    private static final double CONVEYOR_BOTTOM_MARGIN = 60.0;
    private static final double BOX_VERTICAL_OFFSET = 10.0;
    private static final double BOX_WIDTH_SCALE = 0.6;
    private static final double BOX_HEIGHT_SCALE = 0.6;

    private static final double NORMAL_CARROT_DRAW_WIDTH = 120.0;
    private static final double NORMAL_CARROT_DRAW_HEIGHT = 110.0;
    private static final double RARE_CARROT_DRAW_WIDTH = 120.0;
    private static final double RARE_CARROT_DRAW_HEIGHT = 110.0;
    private static final double BOMB_DRAW_WIDTH = 80.0;
    private static final double BOMB_DRAW_HEIGHT = 70.0;

    private static final double BUNNY_DRAW_WIDTH = 120.0;
    private static final double BUNNY_DRAW_HEIGHT = 120.0;
    private static final double BUNNY_HALF_WIDTH = BUNNY_DRAW_WIDTH / 2.0;
    private static final double BUNNY_VERTICAL_OFFSET = 90.0;
    private static final double BUNNY_BOTTOM_MARGIN = 20.0;

    private static final double HEART_ICON_WIDTH = 30.0;

    private static final double GAME_OVER_OVERLAY_OPACITY = 0.7;
    private static final double GAME_OVER_TEXT_OFFSET_X = 50.0;

    private static final KeyCode LEFT_LANE_KEY = KeyCode.Q;
    private static final KeyCode MIDDLE_LANE_KEY = KeyCode.W;
    private static final KeyCode RIGHT_LANE_KEY = KeyCode.E;
    private static final KeyCode EXIT_KEY = KeyCode.X;

    private Image closedMouthBunnySprite;
    private Image openMouthBunnySprite;
    private Image normalCarrotSprite;
    private Image rareCarrotSprite;
    private Image bombSprite;
    private Image backgroundImage;
    private Image lifeHeartSprite;

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

    @FXML
    public void initialize() {
        closedMouthBunnySprite = new Image(getClass().getResourceAsStream("/com/example/sorter_game/images/closedMouthBunny.GIF"));
        openMouthBunnySprite = new Image(getClass().getResourceAsStream("/com/example/sorter_game/images/openMouthBunny.GIF"));
        normalCarrotSprite = new Image(getClass().getResourceAsStream("/com/example/sorter_game/images/normalCarrot.PNG"));
        rareCarrotSprite = new Image(getClass().getResourceAsStream("/com/example/sorter_game/images/rareCarrot.GIF"));
        bombSprite = new Image(getClass().getResourceAsStream("/com/example/sorter_game/images/bomb.GIF"));
        lifeHeartSprite = new Image(getClass().getResourceAsStream("/com/example/sorter_game/images/lifeHeart.gif"));
        backgroundImage = new Image(getClass().getResourceAsStream("/com/example/sorter_game/images/menuBG.PNG"));

        conveyor = new Conveyor();
        game = new Game();

        if (targetLabel != null) {
            targetLabel.setText("Target: " + game.getTargetScore());
        }

        loop = new Timeline(new KeyFrame(
                Duration.millis(GAME_LOOP_INTERVAL_MS),
                e -> updateGameLoop()
        ));
        loop.setCycleCount(Animation.INDEFINITE);
    }

    public void initScene(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == LEFT_LANE_KEY) {
                openLane = 0;
            } else if (code == MIDDLE_LANE_KEY) {
                openLane = 1;
            } else if (code == RIGHT_LANE_KEY) {
                openLane = 2;
            } else if (code == EXIT_KEY) {
                scene.getWindow().hide();
            }
        });

        loop.play();
    }

    private void updateGameLoop() {
        if (!game.isGameOver()) {
            conveyor.tick(openLane, game);
            draw();
        } else {
            drawGameOver();
            loop.stop();
        }
    }

    private void draw() {
        GraphicsContext graphicsContext = gameCanvas.getGraphicsContext2D();
        double width = gameCanvas.getWidth();
        double height = gameCanvas.getHeight();

        drawBackground(graphicsContext, width, height);
        drawBoxes(graphicsContext, width, height);
        drawBunnies(graphicsContext, width, height);
        updateHud();
    }

    private void drawBackground(GraphicsContext graphicsContext, double width, double height) {
        graphicsContext.drawImage(backgroundImage, 0, 0, width, height);
    }

    private void drawBoxes(GraphicsContext graphicsContext, double width, double height) {
        int rows = Conveyor.CONVEYOR_HEIGHT;
        int lanes = Conveyor.LANE_COUNT;

        double laneWidth = width / (double) lanes;
        double rowHeight = (height - CONVEYOR_BOTTOM_MARGIN) / (double) rows;

        for (FallingObject object : conveyor.getObjects()) {
            if (!(object instanceof Box box)) {
                continue;
            }

            int laneIndex = box.getLane();
            int rowIndex = box.getRow();

            double centerX = laneIndex * laneWidth + laneWidth / 2.0;
            double centerY = rowIndex * rowHeight + rowHeight / 2.0 + BOX_VERTICAL_OFFSET;

            double boxWidth = laneWidth * BOX_WIDTH_SCALE;
            double boxHeight = rowHeight * BOX_HEIGHT_SCALE;

            switch (box.getItemType()) {
                case NORMAL_CARROT:
                    graphicsContext.drawImage(
                            normalCarrotSprite,
                            centerX - boxWidth / 2.0,
                            centerY - boxHeight / 2.0,
                            NORMAL_CARROT_DRAW_WIDTH,
                            NORMAL_CARROT_DRAW_HEIGHT
                    );
                    break;
                case BOMB:
                    graphicsContext.drawImage(
                            bombSprite,
                            centerX - boxWidth / 2.0,
                            centerY - boxHeight / 2.0,
                            BOMB_DRAW_WIDTH,
                            BOMB_DRAW_HEIGHT
                    );
                    break;
                case RARE_CARROT:
                    graphicsContext.drawImage(
                            rareCarrotSprite,
                            centerX - boxWidth / 2.0,
                            centerY - boxHeight / 2.0,
                            RARE_CARROT_DRAW_WIDTH,
                            RARE_CARROT_DRAW_HEIGHT
                    );
                    break;
                default:
                    break;
            }
        }
    }

    private void drawBunnies(GraphicsContext graphicsContext, double width, double height) {
        int lanes = Conveyor.LANE_COUNT;
        double laneWidth = width / (double) lanes;

        for (int laneIndex = 0; laneIndex < lanes; laneIndex++) {
            double centerX = laneIndex * laneWidth + laneWidth / 2.0;
            double centerY = height - BUNNY_BOTTOM_MARGIN;

            Image sprite = laneIndex == openLane ? openMouthBunnySprite : closedMouthBunnySprite;

            graphicsContext.drawImage(
                    sprite,
                    centerX - BUNNY_HALF_WIDTH,
                    centerY - BUNNY_VERTICAL_OFFSET,
                    BUNNY_DRAW_WIDTH,
                    BUNNY_DRAW_HEIGHT
            );
        }
    }

    private void updateHud() {
        scoreLabel.setText("Score: " + game.getScore());
        updateLifeDisplay();
    }

    private void updateLifeDisplay() {
        livesContainer.getChildren().clear();

        for (int i = 0; i < game.getLives(); i++) {
            ImageView heart = new ImageView(lifeHeartSprite);
            heart.setFitWidth(HEART_ICON_WIDTH);
            heart.setPreserveRatio(true);
            livesContainer.getChildren().add(heart);
        }
    }

    private void drawGameOver() {
        draw();
        GraphicsContext graphicsContext = gameCanvas.getGraphicsContext2D();
        double width = gameCanvas.getWidth();
        double height = gameCanvas.getHeight();

        graphicsContext.setFill(Color.color(0, 0, 0, GAME_OVER_OVERLAY_OPACITY));
        graphicsContext.fillRect(0, 0, width, height);

        graphicsContext.setFill(Color.WHITE);
        String message = game.hasWon()
                ? "YOU WIN! Score: " + game.getScore()
                : "GAME OVER! Score: " + game.getScore();

        graphicsContext.fillText(message, width / 2.0 - GAME_OVER_TEXT_OFFSET_X, height / 2.0);
    }
}

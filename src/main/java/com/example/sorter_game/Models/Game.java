package com.example.sorter_game.Models;

public class Game {

    public static final int INITIAL_LIVES = 3;
    public static final int TARGET_SCORE = 1000;
    public static final int NORMAL_CARROT_POINTS = 100;
    public static final int RARE_CARROT_POINTS = 200;

    private int score = 0;
    private int lives = INITIAL_LIVES;
    private final int targetScore = TARGET_SCORE;


    public int getScore() { return score; }
    public void addScore(int points) {score += points;}
    public int getLives() { return lives; }
    public void loseLife() {lives--;}
    public int getTargetScore() { return targetScore; }
    public boolean isGameOver() {
        return lives <= 0 || score >= targetScore;
    }
    public boolean hasWon() {
        return score >= targetScore && lives > 0;
    }

}

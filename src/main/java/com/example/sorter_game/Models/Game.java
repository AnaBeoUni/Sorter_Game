package com.example.sorter_game.Models;

public class Game {

    private int score = 0;
    private int lives = 3;
    private final int targetScore = 1000;

    public int getScore() { return score; }
    public int getLives() { return lives; }
    public int getTargetScore() { return targetScore; }

    public boolean isGameOver() {
        return lives <= 0 || score >= targetScore;
    }

    public boolean hasWon() {
        return score >= targetScore && lives > 0;
    }

    public void handleBoxAtBottom(Box box, boolean accepted) {
        if (!accepted) return;

        switch (box.getItemType()) {
            case NORMAL_CARROT:
                score += 100;
                break;
            case RARE_CARROT:
                score += 200;
                break;
            case BOMB:
                lives--;
                break;
        }
    }
}

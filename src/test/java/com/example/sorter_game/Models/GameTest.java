package com.example.sorter_game.Models;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void initialStateIsCorrect() {
        Game game = new Game();
        assertEquals(0, game.getScore());
        assertEquals(Game.INITIAL_LIVES, game.getLives());
        assertFalse(game.isGameOver());
        assertFalse(game.hasWon());
    }

    @Test
    void addScoreIncreasesScore() {
        Game game = new Game();
        game.addScore(150);
        assertEquals(150, game.getScore());
    }

    @Test
    void loseLifeDecreasesLives() {
        Game game = new Game();
        game.loseLife();
        assertEquals(Game.INITIAL_LIVES - 1, game.getLives());
    }

    @Test
    void hasWonWhenScoreReachesTargetAndLivesPositive() {
        Game game = new Game();
        game.addScore(Game.TARGET_SCORE);
        assertTrue(game.hasWon());
        assertTrue(game.isGameOver());
    }

    @Test
    void gameOverWhenNoLives() {
        Game game = new Game();

        for (int i = 0; i < Game.INITIAL_LIVES; i++) {
            game.loseLife();
        }

        assertTrue(game.isGameOver());
        assertFalse(game.hasWon());
    }
}

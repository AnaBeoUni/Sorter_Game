package com.example.sorter_game.Models;

import com.example.sorter_game.Models.Box.ItemType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoxItemTypeTest {

    @Test
    void normalCarrotAddsNormalPoints() {
        Game game = new Game();

        ItemType.NORMAL_CARROT.applyEffect(game);

        assertEquals(Game.NORMAL_CARROT_POINTS, game.getScore());
        assertEquals(Game.INITIAL_LIVES, game.getLives());
    }

    @Test
    void rareCarrotAddsRarePoints() {
        Game game = new Game();

        ItemType.RARE_CARROT.applyEffect(game);

        assertEquals(Game.RARE_CARROT_POINTS, game.getScore());
        assertEquals(Game.INITIAL_LIVES, game.getLives());
    }

    @Test
    void bombReducesLife() {
        Game game = new Game();

        ItemType.BOMB.applyEffect(game);

        assertEquals(0, game.getScore());
        assertEquals(Game.INITIAL_LIVES - 1, game.getLives());
    }
}

package com.example.sorter_game.Models;

import com.example.sorter_game.Models.Box.ItemType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConveyorIntegrationTest {

    @Test
    void acceptedNormalCarrotAddsScore() {
        Game game = new Game();
        Conveyor conveyor = new Conveyor();

        int laneIndex = 1;
        int startRow = Conveyor.CONVEYOR_HEIGHT - 1;

        Box box = new Box(ItemType.NORMAL_CARROT, laneIndex, startRow);
        conveyor.getObjects().add(box);

        conveyor.tick(laneIndex, game);

        assertEquals(Game.NORMAL_CARROT_POINTS, game.getScore());
        assertEquals(Game.INITIAL_LIVES, game.getLives());
        assertEquals(0, conveyor.getObjects().size());
    }

    @Test
    void rejectedBoxDoesNotChangeGameState() {
        Game game = new Game();
        Conveyor conveyor = new Conveyor();

        int laneIndex = 1;
        int openLane = 2;
        int startRow = Conveyor.CONVEYOR_HEIGHT - 1;

        Box box = new Box(ItemType.RARE_CARROT, laneIndex, startRow);
        conveyor.getObjects().add(box);

        conveyor.tick(openLane, game);

        assertEquals(0, game.getScore());
        assertEquals(Game.INITIAL_LIVES, game.getLives());
    }

    @Test
    void acceptedBombReducesLife() {
        Game game = new Game();
        Conveyor conveyor = new Conveyor();

        int laneIndex = 0;
        int startRow = Conveyor.CONVEYOR_HEIGHT - 1;

        Box box = new Box(ItemType.BOMB, laneIndex, startRow);
        conveyor.getObjects().add(box);

        conveyor.tick(laneIndex, game);

        assertEquals(0, game.getScore());
        assertEquals(Game.INITIAL_LIVES - 1, game.getLives());
    }
}

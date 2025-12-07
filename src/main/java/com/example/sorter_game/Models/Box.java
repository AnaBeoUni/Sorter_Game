package com.example.sorter_game.Models;

import java.util.Random;

public class Box extends FallingObject {

    public enum ItemType {
        NORMAL_CARROT {
            @Override
            public void applyEffect(Game game) {
                game.addScore(Game.NORMAL_CARROT_POINTS);
            }
        },
        BOMB {
            @Override
            public void applyEffect(Game game) {
                game.loseLife();
            }
        },
        RARE_CARROT {
            @Override
            public void applyEffect(Game game) {
                game.addScore(Game.RARE_CARROT_POINTS);
            }
        };

        public abstract void applyEffect(Game game);
    }

    private static final int RANDOM_ROLL_BOUND = 100;
    private static final int NORMAL_CARROT_THRESHOLD = 45;
    private static final int BOMB_THRESHOLD = 90;

    private static final Random RANDOM = new Random();

    private final ItemType itemType;

    public Box(ItemType itemType, int laneIndex, int startRowIndex) {
        super(laneIndex, startRowIndex);
        this.itemType = itemType;
    }

    public static Box randomBox(int laneIndex) {
        ItemType itemType = chooseRandomItemType();
        return new Box(itemType, laneIndex, 0);
    }

    private static ItemType chooseRandomItemType() {
        int roll = RANDOM.nextInt(RANDOM_ROLL_BOUND);
        if (roll < NORMAL_CARROT_THRESHOLD) {
            return ItemType.NORMAL_CARROT;
        } else if (roll < BOMB_THRESHOLD) {
            return ItemType.BOMB;
        } else {
            return ItemType.RARE_CARROT;
        }
    }

    public ItemType getItemType() {
        return itemType;
    }

    @Override
    public void onReachBottom(Game game, boolean accepted) {
        if (!accepted) {
            return;
        }
        itemType.applyEffect(game);
    }
}

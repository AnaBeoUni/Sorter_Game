package com.example.sorter_game.Models;

import java.util.Random;

public class Box {

    public enum ItemType {
        NORMAL_CARROT, BOMB, RARE_CARROT
    }

    private static final Random random = new Random();

    private ItemType itemType;
    private int lane; // 0..2
    private int row;  // 0 = top

    public Box(ItemType itemType, int lane, int row) {
        this.itemType = itemType;
        this.lane = lane;
        this.row = row;
    }

    public static Box randomBox(int lane) {
        int roll = random.nextInt(100);
        ItemType itemType;
        if (roll < 45) {
            itemType = ItemType.NORMAL_CARROT;
        } else if (roll < 90) {
            itemType = ItemType.BOMB;
        } else {
            itemType = ItemType.RARE_CARROT;
        }
        return new Box(itemType, lane, 0);
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getLane() {
        return lane;
    }

    public int getRow() {
        return row;
    }

    public void moveDown() {
        row++;
    }
}

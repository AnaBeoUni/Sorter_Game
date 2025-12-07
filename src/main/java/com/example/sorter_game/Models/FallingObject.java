package com.example.sorter_game.Models;

public abstract class FallingObject {

    private final int laneIndex;
    private int rowIndex;

    protected FallingObject(int laneIndex, int startRowIndex) {
        this.laneIndex = laneIndex;
        this.rowIndex = startRowIndex;
    }

    public int getLane() {
        return laneIndex;
    }

    public int getRow() {
        return rowIndex;
    }

    public void moveDown() {
        rowIndex++;
    }

    public abstract void onReachBottom(Game game, boolean accepted);
}

package com.example.sorter_game.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Conveyor {

    public static final int CONVEYOR_HEIGHT = 10;
    public static final int LANE_COUNT = 3;

    private static final int BASE_SPAWN_TICK_INTERVAL = 3;
    private static final int RANDOM_SPAWN_BOUND = 40;
    private static final int RANDOM_SPAWN_THRESHOLD = 2;

    private final List<FallingObject> objects = new ArrayList<>();
    private final Random random = new Random();
    private int tickCount = 0;

    public void tick(int openLane, Game game) {
        tickCount++;

        maybeSpawnBox();

        for (FallingObject object : objects) {
            object.moveDown();
        }

        Iterator<FallingObject> iterator = objects.iterator();
        while (iterator.hasNext()) {
            FallingObject object = iterator.next();
            if (object.getRow() + 1 >= CONVEYOR_HEIGHT) {
                iterator.remove();
                boolean accepted = object.getLane() == openLane;
                object.onReachBottom(game, accepted);
            }
        }
    }

    private void maybeSpawnBox() {
        boolean isIntervalTick = tickCount % BASE_SPAWN_TICK_INTERVAL == 0;
        boolean isRandomSpawn = random.nextInt(RANDOM_SPAWN_BOUND) < RANDOM_SPAWN_THRESHOLD;

        if (isIntervalTick || isRandomSpawn) {
            int laneIndex = random.nextInt(LANE_COUNT);
            objects.add(Box.randomBox(laneIndex));
        }
    }

    public List<FallingObject> getObjects() {
        return objects;
    }
}

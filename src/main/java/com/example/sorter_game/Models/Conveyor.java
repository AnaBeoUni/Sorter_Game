package com.example.sorter_game.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Conveyor {
    public static final int CONVEYOR_HEIGHT = 10;
    public static final int LANE_COUNT = 3;

    private final List<Box> boxes = new ArrayList<>();
    private final Random random = new Random();
    private int tickCount = 0;

    public void tick(int openLane, Game game) {
        tickCount++;

        maybeSpawnBox();

        for (Box b : boxes) {
            b.moveDown();
        }

        Iterator<Box> it = boxes.iterator();
        while (it.hasNext()) {
            Box b = it.next();
            if (b.getRow()+1 >= CONVEYOR_HEIGHT) {
                it.remove();
                boolean accepted = (b.getLane() == openLane);
                game.handleBoxAtBottom(b, accepted);
            }
        }
    }

    private void maybeSpawnBox() {
        if (tickCount % 3 == 0 || random.nextInt(20) < 2) {
            int lane = random.nextInt(LANE_COUNT);
            boxes.add(Box.randomBox(lane));
        }
    }

    public List<Box> getBoxes() {
        return boxes;
    }
}

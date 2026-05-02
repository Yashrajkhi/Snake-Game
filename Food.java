package com.snakegame;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

/**
 * Represents a food item on the board.
 */
public class Food {

    private Point position;
    private final Random random = new Random();

    public Food(int gridWidth, int gridHeight, LinkedList<Point> snakeBody) {
        respawn(gridWidth, gridHeight, snakeBody);
    }

    /**
     * Moves food to a new random position that does not overlap the snake.
     */
    public void respawn(int gridWidth, int gridHeight, LinkedList<Point> snakeBody) {
        Point candidate;
        do {
            int x = random.nextInt(gridWidth);
            int y = random.nextInt(gridHeight);
            candidate = new Point(x, y);
        } while (snakeBody.contains(candidate));
        this.position = candidate;
    }

    public Point getPosition() {
        return position;
    }
}

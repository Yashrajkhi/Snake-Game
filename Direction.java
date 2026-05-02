package com.snakegame;

/**
 * Represents the possible movement directions for the snake.
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    /**
     * Checks if this direction is opposite to the given direction.
     * Used to prevent the snake from reversing into itself.
     */
    public boolean isOpposite(Direction other) {
        if (other == null) return false;
        return (this == UP && other == DOWN)
            || (this == DOWN && other == UP)
            || (this == LEFT && other == RIGHT)
            || (this == RIGHT && other == LEFT);
    }
}

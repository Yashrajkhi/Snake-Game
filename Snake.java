package com.snakegame;

import java.awt.Point;
import java.util.LinkedList;

/**
 * Represents the snake — its body segments and movement logic.
 */
public class Snake {

    private final LinkedList<Point> body;
    private Direction direction;
    private Direction pendingDirection;

    public Snake(int startX, int startY) {
        body = new LinkedList<>();
        // Start with 3 segments
        body.add(new Point(startX, startY));
        body.add(new Point(startX - 1, startY));
        body.add(new Point(startX - 2, startY));
        direction = Direction.RIGHT;
        pendingDirection = Direction.RIGHT;
    }

    /**
     * Moves the snake one step in the current direction.
     *
     * @param grow if true, the tail is not removed (snake grows)
     */
    public void move(boolean grow) {
        // Apply buffered direction change
        if (!pendingDirection.isOpposite(direction)) {
            direction = pendingDirection;
        }

        Point head = getHead();
        Point newHead = switch (direction) {
            case UP    -> new Point(head.x, head.y - 1);
            case DOWN  -> new Point(head.x, head.y + 1);
            case LEFT  -> new Point(head.x - 1, head.y);
            case RIGHT -> new Point(head.x + 1, head.y);
        };

        body.addFirst(newHead);
        if (!grow) {
            body.removeLast();
        }
    }

    /**
     * Buffers a direction change request (applied on the next move).
     */
    public void setDirection(Direction newDirection) {
        if (!newDirection.isOpposite(direction)) {
            pendingDirection = newDirection;
        }
    }

    public Point getHead() {
        return body.getFirst();
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    /**
     * Checks if the snake's head overlaps with any body segment.
     */
    public boolean hasSelfCollision() {
        Point head = getHead();
        return body.stream().skip(1).anyMatch(p -> p.equals(head));
    }

    public int length() {
        return body.size();
    }

    public Direction getDirection() {
        return direction;
    }
}

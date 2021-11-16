package fr.ul.maze.model;

import java.util.Random;

public enum Direction {
    RIGHT,
    LEFT,
    UP,
    DOWN,
    IDLE;

    /**
     * Pick a random direction
     *
     * @return a random direction.
     */
    public static Direction getRandomDirection() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

    /**
     * Get the 90 degre direction from a direction
     *
     * @param dir a direction
     * @return the 90 degre direction from dir.
     */
    public static Direction get90DegreDirection(Direction dir) {
        switch (dir){
            case UP:
                return RIGHT;
            case RIGHT:
                return DOWN;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            default:
                return IDLE;
        }
    }


}

package fr.ul.maze.model;

import exceptions.Exn;

import java.util.Random;

/**
 * Indicates directions in which mobs can move.
 */
public enum Direction {
    UP, UPRIGHT, UPLEFT, DOWN, DOWNRIGHT, DOWNLEFT, LEFT, RIGHT, IDLE;

    /**
     * Returns a random direction among the valid alternatives.
     *
     * @return either {@link #UP}, {@link #DOWN}, {@link #LEFT} or {@link #RIGHT}
     */
    public static Direction random() {
        return Direction.random(new Random());
    }

    /**
     * Returns a random direction among the valid alternatives using an initialized
     * {@link Random} object.
     *
     * @param rnd a random number generator
     * @return either {@link #UP}, {@link #DOWN}, {@link #LEFT} or {@link #RIGHT}
     */
    public static Direction random(Random rnd) {
        final int choice = rnd.nextInt(3);
        switch (choice) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.RIGHT;
            default:
                return Exn.undefined();
        }
    }
}
package fr.ul.maze.model;

/**
 * A simple record holding information about a 2D position (X and Y positions).
 * No unit is specified in this class, meaning that it may be pixels as well as centimeters or other units, as
 * long as it is integral.
 */
public class Position implements Comparable<Position> {
    /**
     * The number of units on the X axis from the origin.
     */
    private final int _x;
    /**
     * The number of units on the Y axis from the origin.
     */
    private final int _y;

    /**
     * Constructs a new position given its X and Y positions.
     *
     * @param x the position on the X axis
     * @param y the position on the Y axis
     */
    public Position(int x, int y) {
        this._x = x;
        this._y = y;
    }

    /**
     * Gets the X position.
     *
     * @return the position on the X axis
     */
    public final int x() {
        return this._x;
    }

    /**
     * Gets the Y position.
     *
     * @return the position on the Y axis
     */
    public final int y() {
        return this._y;
    }

    /**
     * A position is considered to be before another one if either:
     * - its Y position is lower
     * - its Y position is equal, but its X position is lower or equal
     *
     * @param o the other position to compare against
     * @return {@code 1} if the position is after, {@code 0} if the positions are equal, else {@code -1}.
     */
    @Override
    public int compareTo(Position o) {
        if (this._y < o._y)
            return -1;
        if (this._y > o._y)
            return 1;
        return Integer.compare(this._x, o._x);
    }
}

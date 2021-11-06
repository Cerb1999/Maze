package fr.ul.maze.model;

import com.badlogic.gdx.graphics.Texture;

import utils.functional.Lazy;

/**
 * An abstract representation for a maze cell.
 * This cell can be one of:
 * - a wall, which cannot be crossed
 * - a path, where the player stands on
 * - a ladder, indicating the end of a level
 */
public abstract class Cell {
    // FIXME: implement these three initializers at some point
    protected static final Lazy<Texture> WALL_TEXTURE = () -> null;
    protected static final Lazy<Texture> PATH_TEXTURE = () -> null;
    protected static final Lazy<Texture> LADDER_TEXTURE = () -> null;

    /**
     * Allows statically determining whether a cell represents a wall or something else.
     *
     * @return {@code true} if the cell is a wall, else {@code false}
     */
    public abstract boolean isWall();

    /**
     * Retrieves the texture of a cell from one of {@link #WALL_TEXTURE}, {@link #PATH_TEXTURE} or {@link #LADDER_TEXTURE}.
     *
     * @return a texture which can be used in a sprite
     */
    public abstract Texture texture();
}

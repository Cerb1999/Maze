package fr.ul.maze.model;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import fr.ul.maze.MazeGame;

/**
 * An abstract representation for a maze cell.
 * This cell can be one of:
 * - a wall, which cannot be crossed
 * - a path, where the player stands on
 * - a ladder, indicating the end of a level
 */
public abstract class Cell extends Actor {
    /*
    protected static final Lazy<Texture> WALL_TEXTURE = () -> null;
    protected static final Lazy<Texture> PATH_TEXTURE = () -> null;
    protected static final Lazy<Texture> LADDER_TEXTURE = () -> null;
     */

    /**
     * Allows statically determining whether a cell represents a wall or something else.
     *
     * @return {@code true} if the cell is a wall, else {@code false}
     */
    public abstract boolean isWall();

    /**
     * Allows statically determining whether a cell represents a ladder or something else.
     *
     * @return {@code true} if the cell is a ladder, else {@code false}
     */
    public abstract boolean isLadder();

    /**
     * Allows statically determining whether a cell represents a mob or something else.
     *
     * @return {@code true} if the cell is a mob, else {@code false}
     */
    public abstract boolean isMob();
    /*
    /**
     * Retrieves the texture of a cell from one of {@link #WALL_TEXTURE}, {@link #PATH_TEXTURE} or {@link #LADDER_TEXTURE}.
     *
     * @return a texture which can be used in a sprite
     */
    //public abstract Texture texture();


    public abstract void createCell(MazeGame mazeGame, World world, float xPosition, float yPosition);
}

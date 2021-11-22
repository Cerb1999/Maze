package fr.ul.maze.model.generator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fr.ul.maze.model.maze.Maze;
import utils.functional.Tuple3;

/**
 * An abstract maze generator offers means to generate mazes either by instantiating one of its subclasses,
 * which all implements {@link AbstractMazeGenerator#generateMaze(World)} for this purpose.
 */
public abstract class AbstractMazeGenerator {
    /**
     * An abstract class providing means to generate mazes.
     * Dimensions of the maze are indicated by the{@link Maze#WIDTH} and {@link Maze#HEIGHT} constant in the
     * class {@link Maze}.
     *
     * @return A generated Level
     */
    public abstract Tuple3<Maze, Vector2, Vector2> generateMaze(final World world);
}

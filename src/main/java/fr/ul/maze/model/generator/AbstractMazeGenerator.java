package fr.ul.maze.model.generator;

import fr.ul.maze.model.Level;

/**
 * An abstract maze generator offers means to generate mazes either by instantiating one of its subclasses,
 * which all implements {@link AbstractMazeGenerator#generateMaze()} for this purpose.
 */
public abstract class AbstractMazeGenerator {
    /**
     * An abstract class providing means to generate mazes.
     * Dimensions of the maze are indicated by the{@link Level#WIDTH} and {@link Level#HEIGHT} constant in the
     * class {@link Level}.
     *
     * @return A generated Level
     */
    public abstract Level generateMaze();
}

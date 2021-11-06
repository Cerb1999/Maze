package fr.ul.maze.model.generator;

import fr.ul.maze.model.*;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RandomMazeGenerator extends AbstractMazeGenerator {
    /**
     * Randomly generates a maze by applying the randomized Prim's algorithm.
     *
     * @return a procedurally generated maze.
     * @see <a href="https://en.wikipedia.org/wiki/Maze_generation_algorithm#Randomized_Prim's_algorithm">Prim's algorithm</a>
     */
    @Override
    public Level generateMaze() {
        // NOTE: these casts are required because of the type of <code>toArray</code>
        //       they are NOT unsafe!
        //       please don't remove them
        Cell[][] grid = (Cell[][]) IntStream.range(0, Level.HEIGHT)
                .mapToObj(_y -> (Cell[]) Stream.generate(WallCell::new)
                        .limit(Level.WIDTH)
                        .toArray())
                .toArray();

        Random rnd = new Random();

        int startX = rnd.nextInt(Level.WIDTH);
        int startY = rnd.nextInt(Level.HEIGHT);

        // NOTE: it is necessary to use <code>LinkedList</code> because of <code>Collections.shuffle</code> taking
        //       a <code>List<?></code> (else <code>ArrayDeque</code> is faster).
        LinkedList<Position> walls = new LinkedList<>();
        this.pushNeighbours(walls, new Position(startX, startY));

        while (!walls.isEmpty()) {
            Position wall = walls.pop();

            if (this.isValidCell(grid, wall)) {
                grid[wall.y()][wall.x()] = new PathCell();

                this.pushNeighbours(walls, wall);
            }
        }

        return null;
    }

    /**
     * Inserts all the neighbours of the wall into the list of walls only if they are within the bounds of the grid.
     *
     * @param walls the list of wall to modify
     * @param wall the wall to fetch neighbours of
     */
    private void pushNeighbours(LinkedList<Position> walls, Position wall) {
        for (Position pos : this.neighbours(wall)) {
            if (pos.x() >= 0 && pos.x() < Level.WIDTH && pos.y() >= 0 && pos.y() < Level.HEIGHT) {
                walls.push(pos);
            }
        }
        Collections.shuffle(walls, new Random());
    }

    /**
     * Checks whether a given position is within the bounds of the grid, and if there aren't more than 2 practicable
     * cells immediately next to it.
     *
     * @param grid the grid of cells
     * @param pos the position to check for validity
     * @return {@code true} if the position is a valid one for a path, else {@code false}
     */
    private boolean isValidCell(Cell[][] grid, Position pos) {
        if (pos.x() < 0 || pos.x() >= Level.WIDTH || pos.y() < 0 || pos.y() >= Level.HEIGHT)
            return false;

        int nb_adjacent_walls = 0;
        for (Position pos1 : this.neighbours(pos)) {
            if (pos1.x() < 0 || pos1.x() >= Level.WIDTH || pos1.y() < 0 || pos1.y() >= Level.HEIGHT)
                continue;

            if (grid[pos1.y()][pos1.x()].isWall())
                nb_adjacent_walls++;
        }

        return nb_adjacent_walls >= 2;
    }

    /**
     * Retrieves all the neighbours of a given position, without checking if they are in the grid or not.
     *
     * @param pos the position to fetch neighbour cells of
     * @return an array containing all neighbouring positions
     */
    private Position[] neighbours(Position pos) {
        return new Position[] {
                new Position(pos.x(), pos.y() - 1), // NORTH
                new Position(pos.x(), pos.y() + 1), // SOUTH
                new Position(pos.x() - 1, pos.y()), // WEST
                new Position(pos.x() + 1, pos.y()), // EAST
        };
    }
}

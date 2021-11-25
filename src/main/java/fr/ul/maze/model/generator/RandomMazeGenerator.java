package fr.ul.maze.model.generator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fr.ul.maze.model.map.Square;
import fr.ul.maze.model.maze.Maze;
import utils.functional.Tuple3;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Randomly generates a maze by applying the randomized Prim's algorithm.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Maze_generation_algorithm#Randomized_Prim's_algorithm">Prim's algorithm</a>
 */
public class RandomMazeGenerator extends AbstractMazeGenerator {
    @Override
    public Tuple3<Maze, Vector2, Vector2> generateMaze(final World world) {
        Square.Type[][] grid = IntStream.range(0, Maze.HEIGHT)
                .mapToObj(_y -> Stream.generate(() -> Square.Type.WALL)
                        .limit(Maze.WIDTH)
                        .toArray(Square.Type[]::new))
                .toArray(Square.Type[][]::new);

        Random rnd = new Random();

        // Start at a random position in the grid
        int startX = rnd.nextInt(Maze.WIDTH - 2) + 1;
        int startY = rnd.nextInt(Maze.HEIGHT - 2) + 1;
        grid[startY][startX] = Square.Type.PATH;

        // NOTE: it is necessary to use <code>LinkedList</code> because of <code>Collections.shuffle</code> taking
        //       a <code>List<?></code> inside <code>this.pushNeighbours</code> (else <code>ArrayDeque</code> is faster).
        LinkedList<Vector2> walls = new LinkedList<>();
        this.pushNeighbours(walls, rnd, new Vector2(startX, startY));

        while (!walls.isEmpty()) {
            Vector2 wall = walls.pop();

            if (this.isValidCell(grid, wall)) {
                grid[(int) wall.y][(int) wall.x] = Square.Type.PATH;

                this.pushNeighbours(walls, rnd, wall);
            }
        }

        // generate random starting and ending positions
        Vector2 heroVector2, ladderVector2;
        do {
            heroVector2 = new Vector2(rnd.nextInt(Maze.WIDTH), rnd.nextInt(Maze.HEIGHT));
        } while (grid[Math.round(heroVector2.y)][Math.round(heroVector2.x)] == Square.Type.WALL);
        do {
            ladderVector2 = new Vector2(rnd.nextInt(Maze.WIDTH), rnd.nextInt(Maze.HEIGHT));
        } while (grid[Math.round(ladderVector2.y)][Math.round(ladderVector2.x)] == Square.Type.WALL);

        // our grid should be the correct size :)
        return new Tuple3<>(new Maze(world, grid), heroVector2, ladderVector2);
    }

    /**
     * Inserts all the neighbours of the wall into the list of walls only if they are within the bounds of the grid.
     *
     * @param walls the list of wall to modify
     * @param rnd   a random generator
     * @param wall  the wall to fetch neighbours of
     */
    private void pushNeighbours(LinkedList<Vector2> walls, Random rnd, Vector2 wall) {
        this.neighbours(wall).forEachOrdered(pos -> {
            if (pos.x >= 0 && pos.x < Maze.WIDTH && pos.y >= 0 && pos.y < Maze.HEIGHT) {
                walls.push(pos);
            }
        });
        Collections.shuffle(walls, rnd);
    }

    /**
     * Checks whether a given position is within the bounds of the grid, and if there aren't more than 2 practicable
     * cells immediately next to it.
     *
     * @param grid the grid of cells
     * @param pos  the position to check for validity
     * @return {@code true} if the position is a valid one for a path, else {@code false}
     */
    private boolean isValidCell(Square.Type[][] grid, Vector2 pos) {
        if (pos.x <= 0 || pos.x >= Maze.WIDTH - 1 || pos.y <= 0 || pos.y >= Maze.HEIGHT - 1)
            return false;

        return Stream.concat(Stream.of(pos), this.neighbours(pos))
                .filter(pos1 -> pos1.x > 0 && pos1.x < Maze.WIDTH - 1 && pos1.y > 0 && pos1.y < Maze.HEIGHT - 1)
                .filter(pos1 -> grid[(int) pos1.y][(int) pos1.x] != Square.Type.WALL)
                .map(pos1 -> 1)
                .count() < 2;
    }

    /**
     * Retrieves all the neighbours of a given position, without checking if they are in the grid or not.
     *
     * @param pos the position to fetch neighbour cells of
     * @return an array containing all neighbouring positions
     */
    private Stream<Vector2> neighbours(Vector2 pos) {
        return Stream.of(
                new Vector2(pos.x, pos.y - 1), // NORTH
                new Vector2(pos.x, pos.y + 1), // SOUTH
                new Vector2(pos.x - 1, pos.y), // WEST
                new Vector2(pos.x + 1, pos.y) // EAST
        );
    }
}

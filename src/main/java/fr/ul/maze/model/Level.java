package fr.ul.maze.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A level mainly encapsulates an array of {@link Cell}s as well as a level number.
 * <p>
 *
 * @implSpec The storage of {@link Cell}s may be represented using a better data structure than s simple 2D array.
 */
public class Level implements Iterable<Cell> {
    /**
     * Generates a sequence of integers starting at 1.
     */
    private static final AtomicReference<Integer> levelNumberGenerator = new AtomicReference<>(1);

    /**
     * Fixed width for the maze, in a number of cells.
     */
    public static final int WIDTH = 50;
    /**
     * Fixed height for the maze, in a number of cells.
     */
    public static final int HEIGHT = 30;

    /**
     * The current level number (1, 2, ...) corresponding to how many mazes have been completed.
     */
    private final int number;

    /**
     * A mapping from positions to cells.
     */
    private final Map<Position, Cell> cells;

    /**
     * Where is the hero in the maze?
     */
    private final Position heroPosition;

    /**
     * Creates a new maze from an array of cells.
     * <p>
     * Invariant: {@code cells} must be an array of size {@link #WIDTH}×{@link #HEIGHT}.
     *
     * @param grid the cells contained in the maze
     * @param whereIsTheHero  position where the hero is in the maze
     */
    public Level(Cell[][] grid, Position whereIsTheHero) {
        assert (grid.length == HEIGHT) : "the array of cells must be of height 'HEIGHT'";
        assert (Arrays.stream(grid).allMatch(line -> line.length == WIDTH)) : "all lines in the grid must be of length 'WIDTH'";

        // atomically get then set the number generator
        this.number = levelNumberGenerator.getAndUpdate(x -> x + 1);

        // NOTE: beware, the position (1, 1) is most likely to never be a path
        //       we need to randomize the starting position
        this.heroPosition = Objects.requireNonNull(whereIsTheHero);

        this.cells = IntStream.range(0, HEIGHT)
                .boxed()
                .flatMap(y -> IntStream.range(0, WIDTH)
                        .boxed()
                        .map(x -> new Position(x, y)))
                .collect(Collectors.toMap(Function.identity(), pos -> grid[pos.y()][pos.x()]));
    }

    /**
     * Gets an iterator over the grid guaranteed to iterate from cells in a top-down left-right manner.
     * This means that the cells are iterated through line by line (starting from 0), column by column (starting from 0).
     * <p>
     * More formally, cells in this array will be iterated through in the order {@code 1, 2, 3, 4}:
     * {@code
     * 1 │ 2
     * ──┼──
     * 3 │ 4
     * }
     *
     * @return an iterator over cells in a guaranteed order
     */
    @Override
    public Iterator<Cell> iterator() {
        return cells.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .iterator();
    }
}

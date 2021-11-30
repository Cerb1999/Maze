package fr.ul.maze.model.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import fr.ul.maze.model.ai.pathfinding.SimpleHeuristic;
import fr.ul.maze.model.map.Square;
import utils.functional.Tuple2;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * A specialized instance of {@link IndexedGraph} to represent the maze with
 * pathfinding abilities (using GdxAI).
 */
public class Maze implements IndexedGraph<GraphNode> {
    /**
     * Fixed width for the maze, in a number of cells.
     */
    public static final int WIDTH = 30;
    /**
     * Fixed height for the maze, in a number of cells.
     */
    public static final int HEIGHT = 16;

    /**
     * Contains all the connections between paths in the maze.
     */
    private final ObjectMap<GraphNode, Array<Connection<GraphNode>>> paths;
    /**
     * Maps node positions with their respective models.
     *
     * @see Square
     */
    private final HashMap<Vector2, Square> squares;
    /**
     * The heuristic used to determine paths between two nodes.
     *
     * @implNote This is simply set to a new instance of {@link SimpleHeuristic}.
     */
    private final Heuristic<GraphNode> heuristic;
    /**
     * The number of path nodes which are contained in the maze.
     */
    private int nodeCount;

    private Maze() {
        this.paths = new ObjectMap<>();
        this.squares = new HashMap<>();
        this.heuristic = new SimpleHeuristic();
        nodeCount = 0;
    }

    /**
     * Generates a new maze from a grid of {@link Square.Type}s, where only paths are
     * kept as an indexed graph.
     *
     * @param grid a {@link Maze#WIDTH}×{@link Maze#HEIGHT} big grid of {@link Square.Type}
     *             describing what each node is
     * @throws ArrayIndexOutOfBoundsException if the parameter does not have the correct size
     * @throws NullPointerException           if the given grid is {@code null}
     */
    public Maze(final World world, final Square.Type[][] grid) {
        this();

        // a bidirectional map between {@link GraphNode}s and their indices.
        HashMap<GraphNode, Integer> memoryLeft = new HashMap<>();
        HashMap<Integer, GraphNode> memoryRight = new HashMap<>();

        for (int i = 0; i < Maze.HEIGHT; ++i) {
            for (int j = 0; j < Maze.WIDTH; ++j) {
                Vector2 pos = new Vector2(j, i);
                Square.Type type = grid[i][j];

                Square square = new Square(world, type, pos);
                squares.put(pos, square);

                switch (type) {
                    case WALL:
                        continue;
                    case PATH: {
                        GraphNode myself;
                        Integer memorized = memoryLeft.get(new GraphNode(pos, -1));
                        if (memorized != null) {
                            myself = memoryRight.get(memorized); //new GraphNode(pos, memorized);
                        } else {
                            int myIndex = this.nodeCount++;
                            myself = new GraphNode(pos, myIndex);
                            memoryLeft.put(myself, myIndex);
                            memoryRight.put(myIndex, myself);
                        }

                        Vector2[] neighbours = Stream.of(
                                        new Tuple2<>(j - 1, i),
                                        new Tuple2<>(j + 1, i),
                                        new Tuple2<>(j, i - 1),
                                        new Tuple2<>(j, i + 1)
                                )
                                .filter(xy -> xy.fst >= 0 && xy.fst < Maze.WIDTH && xy.snd >= 0 && xy.snd < Maze.HEIGHT)
                                .map(xy -> new Tuple2<>(grid[xy.snd][xy.fst], new Vector2(xy.fst, xy.snd)))
                                .filter(nodeType -> nodeType.fst == Square.Type.PATH)
                                .map(node -> node.snd)
                                .toArray(Vector2[]::new);

                        Array<Connection<GraphNode>> connections = new Array<>(neighbours.length);
                        for (Vector2 posVec : neighbours) {
                            Integer memorizedN = memoryLeft.get(new GraphNode(posVec, -1));
                            GraphNode next;
                            if (Objects.isNull(memorizedN)) {
                                int nextIndex = this.nodeCount++;
                                next = new GraphNode(posVec, nextIndex);
                                memoryLeft.put(next, nextIndex);
                                memoryRight.put(nextIndex, next);
                            } else {
                                next = memoryRight.get(memorizedN); // GraphNode(posVec, memorizedN);
                            }

                            connections.add(new Path(myself, next));
                        }

                        this.paths.put(myself, connections);
                    }
                }
            }
        }
    }

    @Override
    public int getIndex(GraphNode graphNode) {
        return graphNode.snd;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public Array<Connection<GraphNode>> getConnections(GraphNode graphNode) {
        return this.paths.get(graphNode, new Array<>(0));
    }

    /**
     * Returns a reference to the {@link GraphNode} present at the given position if one exists.
     *
     * @param pos the position where to get the node
     * @return {@link Optional#empty()} if no node was found with the given position, else a reference
     * to it encapsulted in {@link Optional#of(Object)}.
     */
    public Optional<GraphNode> getPosition(Vector2 pos) {
        for (GraphNode next : paths.keys()) {
            if (next.fst.epsilonEquals(pos))
                return Optional.of(next);
        }
        return Optional.empty();
    }

    /**
     * Determines the best path between the two given nodes, as long as they are contained in
     * the maze.
     *
     * @param from the position the path starts from
     * @param to   the position which should be reached
     * @return an empty path if none was found, else that path which was found
     */
    public GraphPath<GraphNode> findPath(Vector2 from, Vector2 to) {
        GraphPath<GraphNode> path = new DefaultGraphPath<>();

        Optional<GraphNode> fromNodeOpt = this.getPosition(from);
        Optional<GraphNode> toNodeOpt = this.getPosition(to);

        Gdx.app.debug(getClass().getCanonicalName(), "Is " + from + " found in the graph? " + fromNodeOpt.isPresent());
        Gdx.app.debug(getClass().getCanonicalName(), "Is " + to + " found in the graph? " + toNodeOpt.isPresent());

        if (fromNodeOpt.isPresent() && toNodeOpt.isPresent()) {
            GraphNode fromNode = fromNodeOpt.get(), toNode = toNodeOpt.get();

            boolean found = new IndexedAStarPathFinder<>(this).searchNodePath(fromNode, toNode, this.heuristic, path);
            Gdx.app.debug(getClass().getCanonicalName(), "Any path found? " + found);
        }

        return path;
    }

    /**
     * Returns a new random valid position in the maze, where a valid position is
     * a position pointing to a {@link Square.Type#PATH} square.
     *
     * @return a valid position in the maze
     */
    public Vector2 randomPosition() {
        Vector2 vec;
        Random rnd = new Random();

        do {
            vec = new Vector2(rnd.nextInt(Maze.WIDTH), rnd.nextInt(Maze.HEIGHT));
        } while (this.squares.get(vec).getType() == Square.Type.WALL);

        return vec;
    }

    /**
     * Iterates through cells {@link #WIDTH}-wise first and applies a function to each
     * {@link Square} in the maze (paths and walls included).
     *
     * @param f the consumer to apply to each {@link Square}
     */
    public void forEachCell(Consumer<Square> f) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                f.accept(squares.get(new Vector2(j, i)));
            }
        }
    }

    /**
     * Allows iterating through all connections in the underlying {@link IndexedGraph}.
     * This is mainly exposed for debug purposes.
     *
     * @return an iterable collection of connections, where keys are the origin node, and values are arrays
     * of connections to neighbour nodes.
     */
    public Iterable<? extends ObjectMap.Entry<GraphNode, Array<Connection<GraphNode>>>> getAllPaths() {
        return this.paths;
    }
}

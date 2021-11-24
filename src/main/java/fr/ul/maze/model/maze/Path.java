package fr.ul.maze.model.maze;

import com.badlogic.gdx.ai.pfa.Connection;

/**
 * A path in the graph simply corresponds to a connection between two nodes
 * with a cost of only {@code 1.0f}.
 */
public class Path implements Connection<GraphNode> {
    /**
     * The source and destination nodes.
     */
    private final GraphNode src, dst;

    public Path(final GraphNode src, final GraphNode dst) {
        this.src = src;
        this.dst = dst;
    }

    @Override
    public float getCost() {
        return 1.0f; // the cost of going from one node to the other is always 1
    }

    @Override
    public GraphNode getFromNode() {
        return this.src;
    }

    @Override
    public GraphNode getToNode() {
        return this.dst;
    }
}

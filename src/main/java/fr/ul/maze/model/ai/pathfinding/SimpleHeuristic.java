package fr.ul.maze.model.ai.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;
import fr.ul.maze.model.maze.GraphNode;

/**
 * A very simple heuristic which simply computes the Manhattan distance between two
 * points.
 */
public class SimpleHeuristic implements Heuristic<GraphNode> {
    @Override
    public float estimate(GraphNode n1, GraphNode n2) {
        return Vector2.dst(n1.fst.x, n1.fst.y, n2.fst.x, n2.fst.y);
    }
}

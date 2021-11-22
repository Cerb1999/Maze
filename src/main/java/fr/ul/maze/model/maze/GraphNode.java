package fr.ul.maze.model.maze;

import com.badlogic.gdx.math.Vector2;
import utils.functional.Tuple2;

import java.util.Objects;

/**
 * A node is simply a couple containing the node position and its index in the graph.
 */
public class GraphNode extends Tuple2<Vector2, Integer> {
    public GraphNode(Vector2 vector2, Integer integer) {
        super(vector2, integer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphNode node = (GraphNode) o;
        return this.fst.epsilonEquals(node.fst);
    }


    @Override
    public int hashCode() {
        return Objects.hash(fst);
    }
}

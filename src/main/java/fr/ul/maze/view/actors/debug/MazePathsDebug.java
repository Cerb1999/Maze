package fr.ul.maze.view.actors.debug;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import fr.ul.maze.model.maze.GraphNode;
import fr.ul.maze.model.maze.Maze;
import fr.ul.maze.view.map.RigidSquare;

import java.util.concurrent.atomic.AtomicReference;

public class MazePathsDebug extends Actor {
    private final AtomicReference<Maze> map;

    public MazePathsDebug(final AtomicReference<Maze> maze) {
        this.map = maze;
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        for (ObjectMap.Entry<GraphNode, Array<Connection<GraphNode>>> entry : this.map.get().getAllPaths()) {
            GraphNode origin = entry.key;

            Vector2 pos = origin.fst;

            shapes.setColor(Color.FOREST);
            shapes.set(ShapeRenderer.ShapeType.Filled);
            shapes.circle(pos.x * RigidSquare.WIDTH + RigidSquare.WIDTH / 2, pos.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT / 2, 5);

            for (Connection<GraphNode> line : entry.value) {
                Vector2 from = line.getFromNode().fst;
                Vector2 to = line.getToNode().fst;

                shapes.setColor(Color.BROWN);
                shapes.set(ShapeRenderer.ShapeType.Line);
                shapes.line(
                        from.x * RigidSquare.WIDTH + RigidSquare.WIDTH / 2,
                        from.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT / 2,
                        to.x * RigidSquare.WIDTH + RigidSquare.WIDTH / 2,
                        to.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT / 2
                );
            }
        }
    }
}

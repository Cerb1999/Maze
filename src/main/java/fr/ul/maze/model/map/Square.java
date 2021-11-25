package fr.ul.maze.model.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.maze.view.map.RigidSquare;
import org.lwjgl.Sys;

/**
 * Models squares in the maze.
 */
public class Square {
    /**
     * The position of the square in the maze.
     */
    private final Vector2 pos;
    /**
     * The type of square this models.
     */
    private final Type squareType;
    /**
     * The body used for collisions, when the square needs one (only for walls).
     */
    private Body body;

    public Square(final World world, final Type type, final Vector2 pos) {
        this.squareType = type;
        this.pos = pos;

        if (squareType != Type.PATH) {
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;
            def.position.set(this.pos.x * RigidSquare.WIDTH + RigidSquare.WIDTH / 2f, this.pos.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT / 2f);
            this.body = world.createBody(def);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(RigidSquare.WIDTH / 2f, RigidSquare.HEIGHT / 2f);

            FixtureDef fixDef = new FixtureDef();
            fixDef.shape = shape;
            fixDef.density = 1f;

            Fixture fixture = body.createFixture(fixDef);
            if (getType() == Type.WALL) fixture.setUserData("Wall");

            shape.dispose();
        }
    }

    public Square(final World world, final Type type, final float x, final float y) {
        this(world, type, new Vector2(x, y));
    }

    /**
     * Gets the type of the square.
     *
     * @return the type of the square
     */
    public final Type getType() {
        return this.squareType;
    }

    /**
     * Gets the position of the square.
     *
     * @return the relative position of the square
     */
    public final Vector2 getPosition() {
        return this.pos;
    }

    /**
     * Gets the body used internally.
     *
     * @return the body if the square models a wall, or {@code null} if it does not
     */
    public Body getBody() {
        return body;
    }

    /**
     * An enumeration of all possible square types.
     */
    public enum Type {
        WALL, PATH
    }
}

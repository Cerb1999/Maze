package fr.ul.maze.model.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.maze.view.map.RigidSquare;
import org.lwjgl.Sys;

public class Square {
    public enum Type {
        WALL, PATH;
    }

    private Type squareType;
    private final Vector2 pos;

    private final World world;
    private Body body;

    public Square(final World world, final Type type, final Vector2 pos) {
        this.squareType = type;
        this.pos = pos;

        this.world = world;

        if(squareType!=Type.PATH){
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;
            //System.out.println("pos : " + this.squareType + " : x: " + this.pos.x + " y: " + this.pos.y);
            def.position.set(this.pos.x * RigidSquare.WIDTH + RigidSquare.WIDTH/2, this.pos.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT/2);
            //System.out.println("body position : " + this.squareType + " : x: " + def.position.x + " y: " + def.position.y);
            this.body = world.createBody(def);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(RigidSquare.WIDTH/2, RigidSquare.HEIGHT/2);

            FixtureDef fixDef = new FixtureDef();
            fixDef.shape = shape;
            fixDef.density = 1f;

            Fixture fixture = body.createFixture(fixDef);
            if(getType()==Type.WALL)fixture.setUserData("Wall");

            shape.dispose();
        }
    }
    public Square(final World world, final Type type, final float x, final float y) {
        this(world, type, new Vector2(x, y));
    }

    public final Type getType() {
        return this.squareType;
    }

    public final void setType(Type newType) {
        this.squareType = newType;
    }

    public final Vector2 getPosition() {
        return this.pos;
    }


    public Body getBody() {
        return body;
    }

}

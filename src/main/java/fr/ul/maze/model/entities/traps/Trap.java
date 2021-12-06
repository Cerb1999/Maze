package fr.ul.maze.model.entities.traps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.maze.model.entities.items.ItemType;
import fr.ul.maze.view.map.RigidSquare;
import org.lwjgl.Sys;

public class Trap {

    private Body body;
    private TrapType trapType;
    private World world;
    private boolean toBeRemoved;

    private final Vector2 startingPosition;


    public Trap(World world, Vector2 pos, TrapType trapType) {
        //Create body (hitbox)
        this.trapType = trapType;
        this.world = world;
        this.toBeRemoved = false;
        this.startingPosition = pos;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(pos.x * RigidSquare.WIDTH + RigidSquare.WIDTH/4, pos.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT/4);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();//shape of the body
        shape.setAsBox(RigidSquare.WIDTH /8, RigidSquare.HEIGHT/8);

        FixtureDef fixtureDef = new FixtureDef();//properties of the body
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);//Information shared with the body
        fixture.setUserData(this);

        shape.dispose();//shape not needed after
    }

    public Body getBody() {
        return body;
    }
    public TrapType getTrapType() {
        return trapType;
    }
    public boolean isToBeRemoved() {
        return toBeRemoved;
    }
    public void remove() {
        toBeRemoved = true;
    }
    public void destroyBody() {
        if (body != null) this.world.destroyBody(this.body);
    }

    public void respawn() {
        this.body.setTransform(this.startingPosition.x * RigidSquare.WIDTH + RigidSquare.WIDTH / 2, this.startingPosition.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT / 2, body.getAngle());
    }
    public Vector2 getStartingPosition() {
        return startingPosition;
    }

}

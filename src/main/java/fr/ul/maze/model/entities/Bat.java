package fr.ul.maze.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.maze.view.map.RigidSquare;

import java.util.Random;

public class Bat extends Mob{
    private final static int BASE_HP = 1;
    private static final float BASE_MOVEMENT_SPEED = 150f;
    private final static float BASE_ATTACK_RANGE = 10f;
    private static final float BASE_ATTACK_SPEED = 0.5f;
    private static final int THRESHOLD = 0;
    private static final int VISION_RANGE = 10;
    private static final int WANDER_RANGE = 5;

    public Bat(World world, Vector2 position) {
        super(BASE_HP, BASE_MOVEMENT_SPEED, BASE_ATTACK_RANGE, BASE_ATTACK_SPEED, VISION_RANGE, WANDER_RANGE, world, position);

        this.spriteName = "bat";
        this.mobType = "Bat";

        //Create body (hitbox)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true; // stop body fom spinning on itself
        bodyDef.position.set(position.x * RigidSquare.WIDTH + RigidSquare.WIDTH / 2, position.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT / 2);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();//shape of the body
        shape.setAsBox(RigidSquare.WIDTH / 4, RigidSquare.HEIGHT / 4);

        FixtureDef fixtureDef = new FixtureDef();//properties of the body
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);//Information shared with the body
        fixture.setUserData(this);

        shape.dispose();//shape not needed after
    }

    @Override
    public void backToNormalSpeed() {
        if(this.actionState != EntityActionState.DYING) this.actionState = EntityActionState.IDLE;
        this.walkSpeed = BASE_MOVEMENT_SPEED;
    }
}

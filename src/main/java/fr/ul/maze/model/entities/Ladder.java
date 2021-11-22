package fr.ul.maze.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.maze.view.map.RigidSquare;

import java.util.concurrent.atomic.AtomicReference;

public class Ladder{
    private Body body;

    public Ladder(World world, Vector2 pos) {
        //Create body (hitbox)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(pos.x * RigidSquare.WIDTH + RigidSquare.WIDTH/2, pos.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT/2);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();//shape of the body
        shape.setAsBox(RigidSquare.WIDTH /3, RigidSquare.HEIGHT/3);

        FixtureDef fixtureDef = new FixtureDef();//properties of the body
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);//Information shared with the body
        fixture.setUserData("Ladder");

        shape.dispose();//shape not needed after
    }

    public Body getBody() {
        return body;
    }
}

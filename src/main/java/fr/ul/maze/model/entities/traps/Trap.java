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


    public Trap(World world, Vector2 pos, TrapType trapType) {
        //Create body (hitbox)
        this.trapType = trapType;
        this.world = world;
        this.toBeRemoved = false;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(pos.x * RigidSquare.WIDTH + RigidSquare.WIDTH/2, pos.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT/2);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();//shape of the body
        shape.setAsBox(RigidSquare.WIDTH /4, RigidSquare.HEIGHT/4);

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
        System.out.println("\n Remove est lancée");
    }
    public void destroyBody() {
        System.out.println("\n Destroy body est lancée");
        if (body != null) this.world.destroyBody(this.body);
    }

}

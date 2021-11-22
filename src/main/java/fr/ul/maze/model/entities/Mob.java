package fr.ul.maze.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.maze.model.Direction;
import fr.ul.maze.view.map.RigidSquare;

public class Mob extends Entity {
    private final static int BASE_HP = 1;
    private static final float BASE_MOVEMENT_SPEED = 50f;
    private final static float BASE_ATTACK_RANGE = 10f;
    private static final float BASE_ATTACK_SPEED = 0.5f;
    private static final int THRESHOLD = 0;

    public Mob(final World world, final Vector2 position) {
        super(BASE_HP, BASE_MOVEMENT_SPEED, BASE_ATTACK_RANGE, BASE_ATTACK_SPEED, Direction.IDLE, Direction.DOWN, EntityActionState.IDLE, world, position);

        //Create body (hitbox)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true; // stop body fom spinning on itself
        bodyDef.position.set(position.x * RigidSquare.WIDTH + RigidSquare.WIDTH/2, position.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT/2);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();//shape of the body
        shape.setAsBox(RigidSquare.WIDTH/4, RigidSquare.HEIGHT/4);

        FixtureDef fixtureDef = new FixtureDef();//properties of the body
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);//Information shared with the body
        fixture.setUserData(this);

        shape.dispose();//shape not needed after
    }

    public void destroyBody() {
        if(body!=null)this.world.destroyBody(this.body);
    }

    public void damage(final int dmg) {
        super.damage(dmg);
        if(this.getHp()==0) this.die();
    }

    /**
     * The mob dies. Start dying animation
     */
    public void die(){
        this.actionState = EntityActionState.DYING;
    }
}

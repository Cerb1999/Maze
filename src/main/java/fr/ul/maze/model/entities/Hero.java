package fr.ul.maze.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.maze.model.assets.SoundAssetManager;
import fr.ul.maze.view.map.RigidSquare;
import fr.ul.maze.model.Direction;

public final class Hero extends Entity {
    private final static int BASE_HP = 3;
    private static final float BASE_MOVEMENT_SPEED = 100f;
    private final static float BASE_ATTACK_RANGE = 25f;
    private static final float BASE_ATTACK_SPEED = 0.5f;
    private static final int THRESHOLD = 0;

    public Hero(final World world, final Vector2 position) {
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
        //fixture.setUserData("Hero");
        fixture.setUserData(this);

        shape.dispose();//shape not needed after
    }

    public void createSwordBody(){
        float attackBodyPosX = 0;
        float attackBodyPosY = 0;
        float attackShapeHX = 0;
        float attackShapeHY = 0;
        switch(lastMoveState) {
            case RIGHT:
                attackBodyPosX = body.getPosition().x + this.attackRange;
                attackBodyPosY = body.getPosition().y;
                attackShapeHX = this.attackRange;
                attackShapeHY = this.attackRange/2;
                break;
            case LEFT:
                attackBodyPosX = body.getPosition().x - this.attackRange;
                attackBodyPosY = body.getPosition().y;
                attackShapeHX = this.attackRange;
                attackShapeHY = this.attackRange/2;
                break;
            case UP:
                attackBodyPosX = body.getPosition().x;
                attackBodyPosY = body.getPosition().y + this.attackRange;
                attackShapeHX = this.attackRange/2;
                attackShapeHY = this.attackRange;
                break;
            case DOWN:
                attackBodyPosX = body.getPosition().x;
                attackBodyPosY = body.getPosition().y - this.attackRange;
                attackShapeHX = this.attackRange/2;
                attackShapeHY = this.attackRange;
                break;
        }

        //Create body (hitbox)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true; // stop body fom spinning on itself
        bodyDef.position.set(attackBodyPosX, attackBodyPosY);
        attackBody = this.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();//shape of the body
        shape.setAsBox(attackShapeHX, attackShapeHY);

        FixtureDef fixtureDef = new FixtureDef();//properties of the body
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.isSensor = true;

        Fixture fixture = attackBody.createFixture(fixtureDef);//Information shared with the body
        fixture.setUserData("HeroSword");

        shape.dispose();//shape not needed after
    }

    public void damage(final int dmg) {
        super.damage(dmg);
        SoundAssetManager.getInstance().playHeroHurtSound();
        if(this.getHp()==0) this.die();
    }

    /**
     * The hero dies. Start dying animation
     */
    public void die(){
        this.actionState = EntityActionState.DYING;
    }

    public void destroyBody() {
        if(body!=null)this.world.destroyBody(this.body);
    }

    public boolean isDead() {
        return this.actionState == EntityActionState.DYING;
    }
}

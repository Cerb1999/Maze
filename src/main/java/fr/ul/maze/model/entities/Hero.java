package fr.ul.maze.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.maze.model.assets.SoundAssetManager;
import fr.ul.maze.view.map.RigidSquare;
import fr.ul.maze.model.Direction;

public final class Hero extends Entity {
    private final static int BASE_HP = 3;
    private static int key = 0;
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
        bodyDef.position.set(position.x * RigidSquare.WIDTH + RigidSquare.WIDTH / 2, position.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT / 2);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();//shape of the body
        shape.setAsBox(RigidSquare.WIDTH / 4, RigidSquare.HEIGHT / 4);

        FixtureDef fixtureDef = new FixtureDef();//properties of the body
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);//Information shared with the body
        fixture.setUserData("Hero");

        shape.dispose();//shape not needed after
    }

    /**
     * Créate a hitbox for the sword.
     * Used to detect if the hero hit something when he attacks.
     */
    public void createSwordBody() {
        float attackBodyPosX = 0;
        float attackBodyPosY = 0;
        float attackShapeHX = 0;
        float attackShapeHY = 0;
        switch (lastMoveState) {
            case RIGHT:
                attackBodyPosX = body.getPosition().x + this.attackRange;
                attackBodyPosY = body.getPosition().y;
                attackShapeHX = this.attackRange;
                attackShapeHY = this.attackRange / 2;
                break;
            case LEFT:
                attackBodyPosX = body.getPosition().x - this.attackRange;
                attackBodyPosY = body.getPosition().y;
                attackShapeHX = this.attackRange;
                attackShapeHY = this.attackRange / 2;
                break;
            case UP:
                attackBodyPosX = body.getPosition().x;
                attackBodyPosY = body.getPosition().y + this.attackRange;
                attackShapeHX = this.attackRange / 2;
                attackShapeHY = this.attackRange;
                break;
            case DOWN:
                attackBodyPosX = body.getPosition().x;
                attackBodyPosY = body.getPosition().y - this.attackRange;
                attackShapeHX = this.attackRange / 2;
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

        if (this.isDead())
            this.die();
        else
            this.damaged();
    }


    public void heal(final int heal) {
        if (hp < BASE_HP) super.heal(heal);
        SoundAssetManager.getInstance().playDrinkSound();
    }

    /**
     * The hero dies. Start dying animation
     */
    public void die() {
        this.actionState = EntityActionState.DYING;
    }

    private void damaged() {
        this.actionState = EntityActionState.DAMAGED;
    }

    /**
     * when hero breaks his sword
     */
    public void silence() {
        this.actionState = EntityActionState.NOATTACK;
        SoundAssetManager.getInstance().playBreakSound();
    }

    /**
     * when hero recovers his sword
     */
    public void recover() {
        if(this.actionState != EntityActionState.DYING) this.actionState = EntityActionState.IDLE;
        SoundAssetManager.getInstance().playRedrawSound();
    }

    /**
     * When the hero is slowed by a hourglass
     * @param slow
     */
    public void slow(final float slow) {
        super.slow(slow);
    }

    /**
     * When the hero gets his movement speed back
     */
    public void speed() {
        if(this.actionState != EntityActionState.DYING) this.actionState = EntityActionState.IDLE;
        this.walkSpeed = BASE_MOVEMENT_SPEED;
    }

    /**
     * When there is a contact with the key, the key value becomes 1
     */
    public void obtainKey(){
        this.key = 1 ;
    }

    public void deleteKey(){
        this.key = 0 ;
    }

    public static int getKey() {
        return key;
    }

    public void destroyBody() {
        if (body != null) this.world.destroyBody(this.body);
    }

    public boolean isDead() {
        return super.getHp() == 0; // this.actionState == EntityActionState.DYING;
    }

    public void reset() {
        this.hp = BASE_HP;
    }
}

package fr.ul.maze.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.maze.model.Direction;
import fr.ul.maze.model.assets.SoundAssetManager;
import fr.ul.maze.view.map.RigidSquare;

import java.util.Objects;
import java.util.Random;

public abstract class Mob extends Entity {
    private Vector2 wanderPos;
    private boolean wander = true;

    private final int visionRange;
    private final int wanderRange;

    protected String spriteName;
    protected String mobType;

    public Mob(int baseHp, float baseMovementSpeed, float baseAttackRange, float baseAttackSpeed, int vision_range, int wander_range, final World world, final Vector2 position) {
        super(baseHp, baseMovementSpeed, baseAttackRange, baseAttackSpeed, Direction.IDLE, Direction.DOWN, EntityActionState.IDLE, world, position);

        this.visionRange = vision_range;
        this.wanderRange = wander_range;
    }

    public static void resetScore() {

    }

    public void destroyBody() {
        if (body != null) this.world.destroyBody(this.body);
    }

    public void damage(final int dmg) {
        super.damage(dmg);
        if (this.getHp() == 0) this.die();
    }

    /**
     * The mob dies. Start dying animation
     */
    public void die() {
        this.actionState = EntityActionState.DYING;
        SoundAssetManager.getInstance().playMobDeathSound();
    }

    public int getVisionRange() {
        return visionRange;
    }

    /**
     * Get a random position arround the starting point of the mob (radius of wanderRange).
     * Generate a new wanderPos only if it doesn't exist or rarely so the mob has time to move and don't change direction too frequently
     * @return Vector2
     */
    public Vector2 getWanderPos() {
        Random rnd = new Random();
        //10% chance of changing direction for wander
        if (Objects.isNull(wanderPos) || (rnd.nextFloat() * wanderRange <= 0.1)) {
            float wanderPosX = rnd.nextInt((int) ((this.getStartingPos().x + wanderRange) - (this.getStartingPos().x - wanderRange) + 1)) + (this.getStartingPos().x - wanderRange);
            float wanderPosY = rnd.nextInt((int) ((this.getStartingPos().y + wanderRange) - (this.getStartingPos().y - wanderRange) + 1)) + (this.getStartingPos().y - wanderRange);
            this.wanderPos = new Vector2(wanderPosX, wanderPosY);
        }
        return wanderPos;
    }

    /**
     * When the mob is slowed
     * @param slow
     */
    public void slow(final float slow) {
        super.slow(slow);
        SoundAssetManager.getInstance().playMobSlowSound();
    }

    /**
     * When the mob gets his movement speed back
     */
    public void backToNormalSpeed() {
        if(this.actionState != EntityActionState.DYING) this.actionState = EntityActionState.IDLE;
        this.walkSpeed = BASE_MOVEMENT_SPEED;
    }

    public void speedUp(final float speedup) {
        if(this.actionState != EntityActionState.DYING) this.actionState = EntityActionState.IDLE;
        this.walkSpeed = walkSpeed * speedup;
    }

    public boolean isWandering() {
        return wander;
    }

    public void setWander(boolean wander) {
        this.wander = wander;
    }

    public String getSpriteName(){ return spriteName;}

    public String getMobType(){ return mobType;}
}

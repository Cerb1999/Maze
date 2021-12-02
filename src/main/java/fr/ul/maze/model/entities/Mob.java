package fr.ul.maze.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.maze.model.Direction;
import fr.ul.maze.view.map.RigidSquare;

import java.util.Objects;
import java.util.Random;

public abstract class Mob extends Entity {
    private Vector2 wanderPos;
    private boolean wander = true;

    private int VISION_RANGE;
    private int WANDER_RANGE;

    protected String spriteName;
    protected String mobType;

    public Mob(int baseHp, float baseMovementSpeed, float baseAttackRange, float baseAttackSpeed, int vision_range, int wander_range, final World world, final Vector2 position) {
        super(baseHp, baseMovementSpeed, baseAttackRange, baseAttackSpeed, Direction.IDLE, Direction.DOWN, EntityActionState.IDLE, world, position);

        this.VISION_RANGE = vision_range;
        this.WANDER_RANGE = wander_range;
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
    }

    public int getVisionRange() {
        return VISION_RANGE;
    }

    public Vector2 getWanderPos() {
        Random rnd = new Random();
        //10% chance of changing direction for wander
        if (Objects.isNull(wanderPos) || (rnd.nextFloat() * WANDER_RANGE <= 0.1)) {
            float wanderPosX = rnd.nextInt((int) ((this.getStartingPos().x + WANDER_RANGE) - (this.getStartingPos().x - WANDER_RANGE) + 1)) + (this.getStartingPos().x - WANDER_RANGE);
            float wanderPosY = rnd.nextInt((int) ((this.getStartingPos().y + WANDER_RANGE) - (this.getStartingPos().y - WANDER_RANGE) + 1)) + (this.getStartingPos().y - WANDER_RANGE);
            this.wanderPos = new Vector2(wanderPosX, wanderPosY);
        }
        return wanderPos;
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

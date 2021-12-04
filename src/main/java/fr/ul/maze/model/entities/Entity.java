package fr.ul.maze.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.function.Consumer;

import fr.ul.maze.model.Direction;
import fr.ul.maze.view.map.RigidSquare;

public abstract class Entity {
    protected final World world;
    private final Vector2 startingPosition;
    protected float attackSpeed;
    protected float attackRange;
    protected float walkSpeed;
    protected Direction moveState;
    protected Direction lastMoveState;
    protected EntityActionState actionState;
    protected Body body;
    protected Body attackBody;
    protected int hp;

    public Entity(int baseHp, float baseMovementSpeed, float baseAttackRange, float baseAttackSpeed, Direction moveState, Direction lastMoveState, EntityActionState actionState, World world, Vector2 position) {
        this.hp = baseHp;
        this.attackRange = baseAttackRange;
        this.startingPosition = position;
        this.world = world;

        this.walkSpeed = baseMovementSpeed;
        this.attackSpeed = baseAttackSpeed;
        this.moveState = moveState;
        this.lastMoveState = lastMoveState;
        this.actionState = actionState;
    }

    public void heal(final int heal) {
        this.hp += heal;
    }

    public void damage(final int dmg) {
        this.hp = Math.max(0, this.hp - dmg);
    }

    public final Vector2 getPosition() {
        return this.body.getPosition();
    }

    public void updateBody(Consumer<Body> update) {
        update.accept(this.body);
    }

    public Body getBody() {
        return body;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public float getAttackRange() {
        return attackRange;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public Direction getMoveState() {
        return moveState;
    }

    public void setMoveState(Direction moveState) {
        this.moveState = moveState;
    }

    public Direction getLastMoveState() {
        return lastMoveState;
    }

    public void setLastMoveState(Direction lastMoveState) {
        this.lastMoveState = lastMoveState;
    }

    public EntityActionState getActionState() {
        return actionState;
    }

    public void setActionState(EntityActionState actionState) {
        this.actionState = actionState;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void destroyBody() {
        this.world.destroyBody(this.body);
    }

    public void slow(final float slow) {
        this.walkSpeed = walkSpeed/slow  ;
    }

    public void destroyAttackBody() {
        if (attackBody != null) this.world.destroyBody(this.attackBody);
    }

    public void respawn() {
        this.body.setTransform(this.startingPosition.x * RigidSquare.WIDTH + RigidSquare.WIDTH / 2, this.startingPosition.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT / 2, body.getAngle());

        this.actionState = EntityActionState.IDLE;
        this.moveState = Direction.IDLE;
        this.lastMoveState = Direction.DOWN;
    }

    public Vector2 getStartingPos() {
        return startingPosition;
    }
}

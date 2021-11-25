package fr.ul.maze.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.function.Consumer;
import fr.ul.maze.model.Direction;

public abstract class Entity {
    private int hp;
    protected float attackSpeed;
    protected float attackRange;
    protected float walkSpeed;
    private Vector2 position;
    protected final World world;

    protected Direction moveState;
    protected Direction lastMoveState;
    protected EntityActionState actionState;

    protected Body body;
    protected Body attackBody;


    protected Entity(final int baseHp, final float baseAttackRange, final World world, final Vector2 position) {
        this.hp = baseHp;
        this.attackRange = baseAttackRange;
        this.position = position;
        this.world = world;
    }

    public Entity(int baseHp, float baseMovementSpeed, float baseAttackRange, float baseAttackSpeed, Direction moveState, Direction lastMoveState, EntityActionState actionState, World world, Vector2 position) {
        this.hp = baseHp;
        this.walkSpeed = baseMovementSpeed;
        this.attackRange = baseAttackRange;
        this.attackSpeed = baseAttackSpeed;
        this.moveState = moveState;
        this.lastMoveState = lastMoveState;
        this.actionState = actionState;
        this.position = position;
        this.world = world;
    }

    public final void heal(final int heal) {
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

    public void setHp(int hp){
        this.hp = hp;
    }

    public void destroyBody() {
        this.world.destroyBody(this.body);
    }

    public void destroyAttackBody() {
        if(attackBody!=null)this.world.destroyBody(this.attackBody);
    }
}

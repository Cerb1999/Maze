package fr.ul.maze.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import fr.ul.maze.MazeGame;

import java.util.concurrent.atomic.AtomicReference;

public abstract class Entity extends Actor {
    protected AtomicReference<Integer> hp;
    protected float attackSpeed;
    protected float attackRange;
    protected float walkSpeed;

    protected MazeGame mazeGame;
    protected World world;

    protected Body body;
    protected Sprite sprite;
    protected Body attackBody;

    protected Direction moveState;
    protected Direction lastMoveState;
    protected EntityActionState actionState;

    //Animations
    protected Animation walkRightAnimation;
    protected Animation walkUpAnimation;
    protected Animation walkLeftAnimation;
    protected Animation walkDownAnimation;
    protected Animation attackRightAnimation;
    protected Animation attackUpAnimation;
    protected Animation attackLeftAnimation;
    protected Animation attackDownAnimation;
    protected Animation dieAnimation;

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

    @Override
    protected void positionChanged() {
        sprite.setPosition(getX(), getY());
        super.positionChanged();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        boolean toBeRemoved = false;
        switch (actionState){
            case IDLE:
                switch(moveState) {
                    case RIGHT :
                        walkRightAnimation.update(delta);
                        sprite.set(new Sprite(walkRightAnimation.getFrame()));
                        lastMoveState = Direction.RIGHT;
                        break;
                    case LEFT :
                        walkLeftAnimation.update(delta);
                        sprite.set(new Sprite(walkLeftAnimation.getFrame()));
                        lastMoveState = Direction.LEFT;
                        break;
                    case UP :
                        walkUpAnimation.update(delta);
                        sprite.set(new Sprite(walkUpAnimation.getFrame()));
                        lastMoveState = Direction.UP;
                        break;
                    case DOWN :
                        walkDownAnimation.update(delta);
                        sprite.set(new Sprite(walkDownAnimation.getFrame()));
                        lastMoveState = Direction.DOWN;
                        break;
                    case IDLE :
                        switch(lastMoveState) {
                            case RIGHT:
                                sprite.set(new Sprite((mazeGame.getManagedTexture(this.walkRightAnimation.getNomAnimation()+"1")).getRegion()));
                                break;
                            case LEFT:
                                sprite.set(new Sprite((mazeGame.getManagedTexture(this.walkLeftAnimation.getNomAnimation()+"1")).getRegion()));
                                break;
                            case UP:
                                sprite.set(new Sprite((mazeGame.getManagedTexture(this.walkUpAnimation.getNomAnimation()+"1")).getRegion()));
                                break;
                            case DOWN:
                                sprite.set(new Sprite((mazeGame.getManagedTexture(this.walkDownAnimation.getNomAnimation()+"1")).getRegion()));
                                break;
                        }
                        break;
                    default :

                        break;
                }
                break;
            case ATTACK:
                switch(lastMoveState) {
                    case RIGHT:
                        attackRightAnimation.update(delta);
                        if(!attackRightAnimation.isFinished()) sprite.set(new Sprite(attackRightAnimation.getFrame()));
                        else {
                            actionState = EntityActionState.IDLE;
                            world.destroyBody(attackBody);
                        }
                        break;
                    case LEFT:
                        attackLeftAnimation.update(delta);
                        if(!attackLeftAnimation.isFinished()) sprite.set(new Sprite(attackLeftAnimation.getFrame()));
                        else {
                            actionState = EntityActionState.IDLE;
                            world.destroyBody(attackBody);
                        }
                        break;
                    case UP:
                        attackUpAnimation.update(delta);
                        if(!attackUpAnimation.isFinished()) sprite.set(new Sprite(attackUpAnimation.getFrame()));
                        else {
                            actionState = EntityActionState.IDLE;
                            world.destroyBody(attackBody);
                        }
                        break;
                    case DOWN:
                        attackDownAnimation.update(delta);
                        if(!attackDownAnimation.isFinished()) sprite.set(new Sprite(attackDownAnimation.getFrame()));
                        else {
                            actionState = EntityActionState.IDLE;
                            world.destroyBody(attackBody);
                        }
                        break;
                }
                break;
            case DYING:
                dieAnimation.update(delta);
                if(!dieAnimation.isFinished()) sprite.set(new Sprite(dieAnimation.getFrame()));
                else toBeRemoved=true;
                break;
        }

        //Update actor from body position and angle
        this.setRotation(body.getAngle()*  MathUtils.radiansToDegrees);
        this.setPosition(body.getPosition().x-this.getWidth()/2,body.getPosition().y-this.getHeight()/2);

        //Update actor from actor position
        sprite.setPosition(this.getX(),this.getY());

        //Remove body and actor if the actor is to be removed
        if(toBeRemoved){
            world.destroyBody(body);
            this.remove();
        }

    }
}

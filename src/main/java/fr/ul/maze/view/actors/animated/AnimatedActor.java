package fr.ul.maze.view.actors.animated;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import fr.ul.maze.model.Direction;
import fr.ul.maze.model.assets.MazeAssetManager;
import fr.ul.maze.model.entities.Entity;
import fr.ul.maze.model.entities.EntityActionState;

import java.util.concurrent.atomic.AtomicReference;

public class AnimatedActor extends Actor {
    protected final AtomicReference<? extends Entity> model;

    protected final Sprite sprite;

    //Animations
    protected final Animation walkRightAnimation;
    protected final Animation walkUpAnimation;
    protected final Animation walkLeftAnimation;
    protected final Animation walkDownAnimation;
    protected final Animation attackRightAnimation;
    protected final Animation attackUpAnimation;
    protected final Animation attackLeftAnimation;
    protected final Animation attackDownAnimation;
    protected final Animation dieAnimation;

    protected AnimatedActor(AtomicReference<? extends Entity> model, String sprite, String walkRight, String walkUp, String walkLeft, String walkDown, String attackRight, String attackUp, String attackLeft, String attackDown, String die) {
        this.model = model;

        this.sprite = new Sprite((MazeAssetManager.getInstance().getManagedTexture(sprite)).getRegion());

        //Init walk animations
        this.walkRightAnimation = new Animation(walkRight, 9, 0.5f);
        this.walkUpAnimation = new Animation(walkUp, 9, 0.5f);
        this.walkLeftAnimation = new Animation(walkLeft, 9, 0.5f);
        this.walkDownAnimation = new Animation(walkDown, 9, 0.5f);

        //Init attack animations
        this.attackRightAnimation = new Animation(attackRight, 6, this.model.get().getAttackSpeed(), false);
        this.attackUpAnimation = new Animation(attackUp, 6, this.model.get().getAttackSpeed(), false);
        this.attackLeftAnimation = new Animation(attackLeft, 6, this.model.get().getAttackSpeed(), false);
        this.attackDownAnimation = new Animation(attackDown, 6, this.model.get().getAttackSpeed(), false);

        //Init dying animation
        this.dieAnimation = new Animation(die, 6, 2, false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        boolean toBeRemoved = false;

        switch (this.model.get().getActionState()) {
            case IDLE:
                switch (this.model.get().getMoveState()) {
                    case RIGHT:
                        walkRightAnimation.update(delta);
                        sprite.set(new Sprite(walkRightAnimation.getFrame()));
                        this.model.get().setLastMoveState(Direction.RIGHT);
                        break;
                    case LEFT:
                        walkLeftAnimation.update(delta);
                        sprite.set(new Sprite(walkLeftAnimation.getFrame()));
                        this.model.get().setLastMoveState(Direction.LEFT);
                        break;
                    case UP:
                        walkUpAnimation.update(delta);
                        sprite.set(new Sprite(walkUpAnimation.getFrame()));
                        this.model.get().setLastMoveState(Direction.UP);
                        break;
                    case DOWN:
                        walkDownAnimation.update(delta);
                        sprite.set(new Sprite(walkDownAnimation.getFrame()));
                        this.model.get().setLastMoveState(Direction.DOWN);
                        break;
                    case IDLE:
                        switch (this.model.get().getLastMoveState()) {
                            case RIGHT:
                                sprite.set(new Sprite((MazeAssetManager.getInstance().getManagedTexture(this.walkRightAnimation.getNomAnimation() + "1")).getRegion()));
                                break;
                            case LEFT:
                                sprite.set(new Sprite((MazeAssetManager.getInstance().getManagedTexture(this.walkLeftAnimation.getNomAnimation() + "1")).getRegion()));
                                break;
                            case UP:
                                sprite.set(new Sprite((MazeAssetManager.getInstance().getManagedTexture(this.walkUpAnimation.getNomAnimation() + "1")).getRegion()));
                                break;
                            case DOWN:
                                sprite.set(new Sprite((MazeAssetManager.getInstance().getManagedTexture(this.walkDownAnimation.getNomAnimation() + "1")).getRegion()));
                                break;
                        }
                        break;
                    default:

                        break;
                }
                break;
            case ATTACK:
                this.attackRightAnimation.setFinishedState(false);
                this.attackLeftAnimation.setFinishedState(false);
                this.attackUpAnimation.setFinishedState(false);
                this.attackDownAnimation.setFinishedState(false);
                switch (this.model.get().getLastMoveState()) {
                    case RIGHT:
                        attackRightAnimation.update(delta);
                        if (!attackRightAnimation.isFinished()) sprite.set(new Sprite(attackRightAnimation.getFrame()));
                        else {
                            this.model.get().setActionState(EntityActionState.IDLE);
                            this.model.get().setMoveState(Direction.IDLE);
                        }
                        break;
                    case LEFT:
                        attackLeftAnimation.update(delta);
                        if (!attackLeftAnimation.isFinished()) sprite.set(new Sprite(attackLeftAnimation.getFrame()));
                        else {
                            this.model.get().setActionState(EntityActionState.IDLE);
                            this.model.get().setMoveState(Direction.IDLE);
                        }
                        break;
                    case UP:
                        attackUpAnimation.update(delta);
                        if (!attackUpAnimation.isFinished()) sprite.set(new Sprite(attackUpAnimation.getFrame()));
                        else {
                            this.model.get().setActionState(EntityActionState.IDLE);
                            this.model.get().setMoveState(Direction.IDLE);
                        }
                        break;
                    case DOWN:
                        attackDownAnimation.update(delta);
                        if (!attackDownAnimation.isFinished()) sprite.set(new Sprite(attackDownAnimation.getFrame()));
                        else {
                            this.model.get().setActionState(EntityActionState.IDLE);
                            this.model.get().setMoveState(Direction.IDLE);
                        }
                        break;
                }
                break;
            case DYING:
                dieAnimation.update(delta);
                if (!dieAnimation.isFinished()) sprite.set(new Sprite(dieAnimation.getFrame()));
                else toBeRemoved = true;
                break;
        }

        //Remove body and actor if the actor is to be removed
        if (toBeRemoved) {
            this.model.get().destroyBody();
            this.remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch);
    }
}

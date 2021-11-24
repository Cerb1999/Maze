package fr.ul.maze.view.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import fr.ul.maze.model.assets.MazeAssetManager;
import fr.ul.maze.model.entities.EntityActionState;
import fr.ul.maze.model.entities.Hero;
import fr.ul.maze.view.map.RigidSquare;
import fr.ul.maze.model.Direction;

import java.util.concurrent.atomic.AtomicReference;

public final class HeroActor extends Actor {
    private final AtomicReference<Hero> model;

    protected Sprite sprite;

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

    public HeroActor(final World world, AtomicReference<Hero> hero) {
        this.model = hero;
        this.sprite = new Sprite((MazeAssetManager.getInstance().getManagedTexture("walkS1")).getRegion());
        sprite.setPosition(hero.get().getPosition().x - RigidSquare.WIDTH / 2, hero.get().getPosition().x - RigidSquare.HEIGHT / 2);

        //Parameter for the actor linked with the body
        this.setWidth(sprite.getWidth());
        this.setHeight(sprite.getHeight());
        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);

        //Init walk animations
        walkRightAnimation = new Animation("walkR", 9, 0.5f);
        walkUpAnimation = new Animation("walkN", 9, 0.5f);
        walkLeftAnimation = new Animation("walkL", 9, 0.5f);
        walkDownAnimation = new Animation("walkS", 9, 0.5f);

        //Init attack animations
        attackRightAnimation = new Animation("slashR", 6, this.model.get().getAttackSpeed(), false);
        attackUpAnimation = new Animation("slashN", 6, this.model.get().getAttackSpeed(), false);
        attackLeftAnimation = new Animation("slashL", 6, this.model.get().getAttackSpeed(), false);
        attackDownAnimation = new Animation("slashS", 6, this.model.get().getAttackSpeed(), false);

        //Init dying animation
        dieAnimation = new Animation("die", 6, 2, false);
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
                            this.model.get().destroyAttackBody();
                        }
                        break;
                    case LEFT:
                        attackLeftAnimation.update(delta);
                        if (!attackLeftAnimation.isFinished()) sprite.set(new Sprite(attackLeftAnimation.getFrame()));
                        else {
                            this.model.get().setActionState(EntityActionState.IDLE);
                            this.model.get().setMoveState(Direction.IDLE);
                            this.model.get().destroyAttackBody();
                        }
                        break;
                    case UP:
                        attackUpAnimation.update(delta);
                        if (!attackUpAnimation.isFinished()) sprite.set(new Sprite(attackUpAnimation.getFrame()));
                        else {
                            this.model.get().setActionState(EntityActionState.IDLE);
                            this.model.get().setMoveState(Direction.IDLE);
                            this.model.get().destroyAttackBody();
                        }
                        break;
                    case DOWN:
                        attackDownAnimation.update(delta);
                        if (!attackDownAnimation.isFinished()) sprite.set(new Sprite(attackDownAnimation.getFrame()));
                        else {
                            this.model.get().setActionState(EntityActionState.IDLE);
                            this.model.get().setMoveState(Direction.IDLE);
                            this.model.get().destroyAttackBody();
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

        //Update actor from body position and angle
        this.setRotation(this.model.get().getBody().getAngle() * MathUtils.radiansToDegrees);
        this.setPosition(this.model.get().getBody().getPosition().x - this.getWidth() / 2, this.model.get().getBody().getPosition().y - this.getHeight() / 2);

        //Update actor from actor position
        sprite.setPosition(this.getX(), this.getY());

        //Remove body and actor if the actor is to be removed
        if (toBeRemoved) {
            this.model.get().destroyBody();
            this.remove();
        }
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
    public void drawDebug(ShapeRenderer shapes) {
        super.drawDebug(shapes);

        shapes.setColor(Color.TEAL);
        shapes.set(ShapeRenderer.ShapeType.Filled);
        shapes.circle(this.model.get().getPosition().x, this.model.get().getPosition().y, 10);
    }
}

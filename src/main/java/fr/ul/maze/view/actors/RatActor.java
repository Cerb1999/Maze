package fr.ul.maze.view.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import fr.ul.maze.model.entities.Mob;
import fr.ul.maze.view.actors.animated.AnimatedActor;
import fr.ul.maze.view.actors.animated.Animation;
import fr.ul.maze.view.map.RigidSquare;

import java.util.concurrent.atomic.AtomicReference;

public final class RatActor extends AnimatedActor {
    public RatActor(final World world, AtomicReference<Mob> mob) {
        super(mob, mob.get().getSpriteName()+"WalkS1", mob.get().getSpriteName()+"WalkR", mob.get().getSpriteName()+"WalkN", mob.get().getSpriteName()+"WalkL", mob.get().getSpriteName()+"WalkS", 3, mob.get().getSpriteName()+"WalkR", mob.get().getSpriteName()+"WalkN", mob.get().getSpriteName()+"WalkL", mob.get().getSpriteName()+"WalkS", 3, mob.get().getSpriteName()+"WalkS", 1);

        sprite.setPosition(mob.get().getPosition().x - RigidSquare.WIDTH / 2, mob.get().getPosition().x - RigidSquare.HEIGHT / 2);

        //Parameter for the actor linked with the body
        this.setWidth(sprite.getWidth());
        this.setHeight(sprite.getHeight());
        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        //Update actor from body position and angle
        this.setRotation(this.model.get().getBody().getAngle() * MathUtils.radiansToDegrees);
        this.setPosition(this.model.get().getBody().getPosition().x - this.getWidth() / 2, this.model.get().getBody().getPosition().y - this.getHeight() / 2);

        //Update actor from actor position
        sprite.setPosition(this.getX(), this.getY());
    }

    @Override
    protected void positionChanged() {
        sprite.setPosition(getX(), getY());
        super.positionChanged();
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        super.drawDebug(shapes);

        shapes.setColor(Color.TEAL);
        shapes.set(ShapeRenderer.ShapeType.Filled);
        shapes.circle(this.model.get().getPosition().x, this.model.get().getPosition().y, 10);
    }
}

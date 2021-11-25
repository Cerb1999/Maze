package fr.ul.maze.view.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import fr.ul.maze.model.assets.MazeAssetManager;
import fr.ul.maze.model.entities.Ladder;
import fr.ul.maze.view.map.RigidSquare;

import java.util.concurrent.atomic.AtomicReference;

public final class LadderActor extends Actor {
    private final AtomicReference<Ladder> model;

    protected Sprite sprite;

    public LadderActor(final World world, AtomicReference<Ladder> ladder) {
        this.model = ladder;
        sprite = new Sprite((MazeAssetManager.getInstance().getManagedTexture("ladder1")).getRegion());
        sprite.setPosition(ladder.get().getBody().getPosition().x - RigidSquare.WIDTH / 2, ladder.get().getBody().getPosition().x - RigidSquare.HEIGHT / 2);

        //Parameter for the actor linked with the body
        this.setWidth(sprite.getWidth());
        this.setHeight(sprite.getHeight());
        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
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

        //Update actor from body position and angle
        this.setRotation(model.get().getBody().getAngle() * MathUtils.radiansToDegrees);
        this.setPosition(model.get().getBody().getPosition().x - this.getWidth() / 2, model.get().getBody().getPosition().y - this.getHeight() / 2);

        //Update actor from actor position
        sprite.setPosition(this.getX(), this.getY());

    }
}

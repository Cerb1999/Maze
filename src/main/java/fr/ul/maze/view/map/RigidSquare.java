package fr.ul.maze.view.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import fr.ul.maze.model.assets.MazeAssetManager;
import fr.ul.maze.model.map.Square;

import java.util.Random;

public final class RigidSquare extends Actor {
    public final static int WIDTH = 64;
    public final static int HEIGHT = 64;

    private final Square.Type squareType;

    private final Sprite sprite;
    private final Square model;

    public RigidSquare(final World world, final Square model) {
        this.model = model;
        Vector2 relativePosition = model.getPosition();
        this.squareType = model.getType();
        if(model.getType() == Square.Type.WALL)sprite = new Sprite((MazeAssetManager.getInstance().getManagedTexture("wall1-1")).getRegion());
        else sprite = new Sprite((MazeAssetManager.getInstance().getManagedTexture("floor1")).getRegion());
        sprite.setPosition(relativePosition.x * WIDTH, relativePosition.y * HEIGHT);


        //Parameter for the actor linked with the body
        this.setWidth(sprite.getWidth());
        this.setHeight(sprite.getHeight());
        this.setOrigin(this.getWidth()/2,this.getHeight()/2);

        //floor don't need body
        if(model.getType() == Square.Type.PATH){
            this.setRotation(0 *  MathUtils.radiansToDegrees);
            this.setPosition(relativePosition.x * WIDTH,relativePosition.y * HEIGHT);
        }
    }

    public RigidSquare(final World world, final Square model, final int currentLevelNumber) {
        this.model = model;
        Vector2 relativePosition = model.getPosition();
        this.squareType = model.getType();
        if(model.getType() == Square.Type.WALL){
            String spriteName = "wall";
            spriteName = spriteName.concat(String.valueOf(1 + ((currentLevelNumber/5)%4)));
            Random rnd = new Random();
            //Moss on the wall ?
            if(rnd.nextFloat()<=0.75) spriteName = spriteName.concat("-1");
            else spriteName = spriteName.concat("-2");
            //Crack for the wall
            if(rnd.nextFloat()>=0.75){
                float crackChoice = rnd.nextFloat();
                if(crackChoice<=0.33) spriteName = spriteName.concat("-1");
                else if(crackChoice<=0.66) spriteName = spriteName.concat("-2");
                else spriteName = spriteName.concat("-3");
            }
            sprite = new Sprite((MazeAssetManager.getInstance().getManagedTexture(spriteName)).getRegion());
        }
        else sprite = new Sprite((MazeAssetManager.getInstance().getManagedTexture("floor1")).getRegion());
        sprite.setPosition(relativePosition.x * WIDTH, relativePosition.y * HEIGHT);


        //Parameter for the actor linked with the body
        this.setWidth(sprite.getWidth());
        this.setHeight(sprite.getHeight());
        this.setOrigin(this.getWidth()/2,this.getHeight()/2);

        //floor don't need body
        if(model.getType() == Square.Type.PATH){
            this.setRotation(0 *  MathUtils.radiansToDegrees);
            this.setPosition(relativePosition.x * WIDTH,relativePosition.y * HEIGHT);
        }
    }

    public Square.Type getSquareType() {
        return squareType;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(model.getType() != Square.Type.PATH){
            //Update actor from body position and angle
            this.setRotation(model.getBody().getAngle()*  MathUtils.radiansToDegrees);
            this.setPosition(model.getBody().getPosition().x-this.getWidth()/2,model.getBody().getPosition().y-this.getHeight()/2);
        }
        //Update actor from actor position
        sprite.setPosition(this.getX(),this.getY());

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
}

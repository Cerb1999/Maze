package fr.ul.maze.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.maze.MazeGame;

public class PathCell extends Cell {
    private Body body;
    private Sprite sprite;

    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public boolean isLadder() {
        return false;
    }

    @Override
    public boolean isMob() {
        return false;
    }

    @Override
    public void createCell(MazeGame mazeGame, World world, float xPosition, float yPosition){
        sprite = new Sprite((mazeGame.getManagedTexture("floor1")).getRegion());
        sprite.setPosition(xPosition,yPosition);

        //Create body (hitbox)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(xPosition, yPosition);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();//shape of the body
        shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);

        FixtureDef fixtureDef = new FixtureDef();//properties of the body
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);//Information shared with the body
        fixture.setUserData("Path");

        shape.dispose();//shape not needed after

        //Parameter for the actor linked with the body
        this.setWidth(sprite.getWidth());
        this.setHeight(sprite.getHeight());
        this.setOrigin(this.getWidth()/2,this.getHeight()/2);
        this.setRotation(body.getAngle()*  MathUtils.radiansToDegrees);
        this.setPosition(body.getPosition().x-this.getWidth()/2,body.getPosition().y-this.getHeight()/2);

        //floor don't need body
        world.destroyBody(body);
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
        //this.setRotation(body.getAngle()*  MathUtils.radiansToDegrees);
        //this.setPosition(body.getPosition().x-this.getWidth()/2,body.getPosition().y-this.getHeight()/2);

        //Update actor from actor position
        sprite.setPosition(this.getX(),this.getY());

    }
}

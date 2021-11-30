package fr.ul.maze.model.entities.items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.maze.view.map.RigidSquare;

public class Item {
    private Body body;
    private ItemType itemType;
    private World world;
    private boolean toBeRemoved;

    public Item(World world, Vector2 pos, ItemType itemType) {
        //Create body (hitbox)
        this.itemType = itemType;
        this.world = world;
        this.toBeRemoved = false;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(pos.x * RigidSquare.WIDTH + RigidSquare.WIDTH/2, pos.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT/2);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();//shape of the body
        shape.setAsBox(RigidSquare.WIDTH /3, RigidSquare.HEIGHT/3);

        FixtureDef fixtureDef = new FixtureDef();//properties of the body
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);//Information shared with the body
        fixture.setUserData(this);

        shape.dispose();//shape not needed after
    }

    public Body getBody() {
        return body;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public boolean isToBeRemoved() {
        return toBeRemoved;
    }
    public void remove() {
        toBeRemoved = true;
    }
    public void destroyBody() {
        if (body != null) this.world.destroyBody(this.body);
    }

}

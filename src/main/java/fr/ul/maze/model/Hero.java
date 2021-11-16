package fr.ul.maze.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import fr.ul.maze.MazeGame;

import java.util.concurrent.atomic.AtomicReference;

public class Hero extends Entity {
    private static final int BASE_HP = 3;
    private static final float BASE_MS = 50f;
    private static final float BASE_ATKRANGE = 64f;
    private static final float BASE_ATKSPEED = 0.5f;
    private static final int THRESHOLD = 0;

    public Hero(MazeGame mazeGame, World world, float xPosition, float yPosition){
        this.hp = new AtomicReference<>(BASE_HP);
        this.attackSpeed = 0.5f;
        this.attackRange = 25f;
        this.walkSpeed = 100;
        this.mazeGame = mazeGame;
        this.world = world;
        this.moveState = Direction.IDLE;
        this.lastMoveState = Direction.DOWN;
        this.actionState = EntityActionState.IDLE;

        sprite = new Sprite((mazeGame.getManagedTexture("walkS1")).getRegion());
        sprite.setPosition(xPosition,yPosition);

        //Create body (hitbox)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true; // stop body fom spinning on itself
        bodyDef.position.set(xPosition, yPosition);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();//shape of the body
        shape.setAsBox(sprite.getWidth()/4, sprite.getHeight()/4);

        FixtureDef fixtureDef = new FixtureDef();//properties of the body
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);//Information shared with the body
        fixture.setUserData("Hero");

        shape.dispose();//shape not needed after

        //Parameter for the actor linked with the body
        this.setWidth(sprite.getWidth());
        this.setHeight(sprite.getHeight());
        this.setOrigin(this.getWidth()/2,this.getHeight()/2);

        //Init walk animations
        walkRightAnimation = new Animation(mazeGame, "walkR", 9, 0.5f);
        walkUpAnimation = new Animation(mazeGame, "walkN", 9, 0.5f);
        walkLeftAnimation = new Animation(mazeGame, "walkL", 9, 0.5f);
        walkDownAnimation = new Animation(mazeGame, "walkS", 9, 0.5f);

        //Init attack animations
        attackRightAnimation = new Animation(mazeGame, "slashR", 6, this.attackSpeed, false);
        attackUpAnimation = new Animation(mazeGame, "slashN", 6, this.attackSpeed, false);
        attackLeftAnimation = new Animation(mazeGame, "slashL", 6, this.attackSpeed, false);
        attackDownAnimation = new Animation(mazeGame, "slashS", 6, this.attackSpeed, false);

        //Init dying animation
        dieAnimation = new Animation(mazeGame, "die", 6, 2, false);
    }

    /**
     * Method used to move the body of the Hero.
     * Apply force to the body depending of the direction.
     * @param dir direction where which the hero move.
     */
    public void moveHero(Direction dir){
        if(this.actionState == EntityActionState.IDLE){
            this.moveState = dir;
            switch(moveState) {
                case RIGHT:
                    this.body.setLinearVelocity(this.walkSpeed,0);
                    break;
                case LEFT:
                    this.body.setLinearVelocity(-this.walkSpeed,0);
                    break;
                case UP:
                    this.body.setLinearVelocity(0,this.walkSpeed);
                    break;
                case DOWN:
                    this.body.setLinearVelocity(0,-this.walkSpeed);
                    break;
                case IDLE:
                    this.body.setLinearVelocity(0,0);
                    break;
            }
        }
        else
            this.body.setLinearVelocity(0,0);
    }

    /**
     * Method used to make the hero attack
     * Start animation attack
     */
    public void attack() {
        if(this.actionState == EntityActionState.IDLE) {
            this.actionState = EntityActionState.ATTACK;
            this.attackDownAnimation.setFinishedState(false);
            this.attackUpAnimation.setFinishedState(false);
            this.attackRightAnimation.setFinishedState(false);
            this.attackLeftAnimation.setFinishedState(false);
            //Add here function to interact with another entity (a monster for exemple)
            this.createSwordBody();
        }
    }

    private void createSwordBody(){
        float attackBodyPosX = 0;
        float attackBodyPosY = 0;
        float attackShapeHX = 0;
        float attackShapeHY = 0;
        switch(lastMoveState) {
            case RIGHT:
                attackBodyPosX = body.getPosition().x + this.attackRange;
                attackBodyPosY = body.getPosition().y;
                attackShapeHX = this.attackRange;
                attackShapeHY = this.attackRange/2;
                break;
            case LEFT:
                attackBodyPosX = body.getPosition().x - this.attackRange;
                attackBodyPosY = body.getPosition().y;
                attackShapeHX = this.attackRange;
                attackShapeHY = this.attackRange/2;
                break;
            case UP:
                attackBodyPosX = body.getPosition().x;
                attackBodyPosY = body.getPosition().y + this.attackRange;
                attackShapeHX = this.attackRange/2;
                attackShapeHY = this.attackRange;
                break;
            case DOWN:
                attackBodyPosX = body.getPosition().x;
                attackBodyPosY = body.getPosition().y - this.attackRange;
                attackShapeHX = this.attackRange/2;
                attackShapeHY = this.attackRange;
                break;
        }

        //Create body (hitbox)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true; // stop body fom spinning on itself
        bodyDef.position.set(attackBodyPosX, attackBodyPosY);
        attackBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();//shape of the body
        shape.setAsBox(attackShapeHX, attackShapeHY);

        FixtureDef fixtureDef = new FixtureDef();//properties of the body
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.isSensor = true;

        Fixture fixture = attackBody.createFixture(fixtureDef);//Information shared with the body
        fixture.setUserData("HeroSword");

        shape.dispose();//shape not needed after
    }

    /**
     * Set hp of the hero
     */
    public void setHp(int newHp){
        this.hp.set(newHp);
    }

    /**
     * The hero loses one hp
     */
    public void hurt(int hp){
        int val = this.hp.updateAndGet(value -> Math.max(value - hp, THRESHOLD));
        assert (val >= THRESHOLD) : "inconsistent life loss";
    }

    public void heal(int hp) {
        int val = this.hp.updateAndGet(value -> Math.min(value + hp, BASE_HP));
        assert (val >= BASE_HP) : "inconsistent life gain";
    }

    /**
     * The hero dies. Start dying animation
     */
    public void die(){
        this.actionState = EntityActionState.DYING;
        this.dieAnimation.setFinishedState(false);
    }

}

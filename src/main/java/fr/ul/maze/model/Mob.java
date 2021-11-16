package fr.ul.maze.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.maze.MazeGame;

import java.util.concurrent.atomic.AtomicReference;

public class Mob extends Entity{
    private static final int BASE_HP = 1;
    private static final float BASE_MS = 50f;
    private static final float BASE_ATKRANGE = 64f;
    private static final float BASE_ATKSPEED = 0.5f;
    private static final int THRESHOLD = 0;

    private long lastTurn = System.currentTimeMillis();
    private int nbWallMoveRandom = 0;

    public Mob(MazeGame mazeGame, World world, float xPosition, float yPosition){
        this.hp = new AtomicReference<>(BASE_HP);
        this.attackSpeed = 0.5f;
        this.attackRange = 64f;
        this.walkSpeed = 50;
        this.mazeGame = mazeGame;
        this.world = world;
        this.moveState = Direction.IDLE;
        this.lastMoveState = Direction.DOWN;
        this.actionState = EntityActionState.IDLE;

        sprite = new Sprite((mazeGame.getManagedTexture("zombieWalkS1")).getRegion());
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
        fixture.setUserData(this);

        shape.dispose();//shape not needed after

        //Parameter for the actor linked with the body
        this.setWidth(sprite.getWidth());
        this.setHeight(sprite.getHeight());
        this.setOrigin(this.getWidth()/2,this.getHeight()/2);

        //Init walk animations
        walkRightAnimation = new Animation(mazeGame, "zombieWalkR", 9, 0.5f);
        walkUpAnimation = new Animation(mazeGame, "zombieWalkN", 9, 0.5f);
        walkLeftAnimation = new Animation(mazeGame, "zombieWalkL", 9, 0.5f);
        walkDownAnimation = new Animation(mazeGame, "zombieWalkS", 9, 0.5f);

        //Init attack animations
        attackRightAnimation = new Animation(mazeGame, "zombieSmashR", 6, this.attackSpeed, false);
        attackUpAnimation = new Animation(mazeGame, "zombieSmashN", 6, this.attackSpeed, false);
        attackLeftAnimation = new Animation(mazeGame, "zombieSmashL", 6, this.attackSpeed, false);
        attackDownAnimation = new Animation(mazeGame, "zombieSmashS", 6, this.attackSpeed, false);

        //Init dying animation
        dieAnimation = new Animation(mazeGame, "zombieDie", 6, 2, false);
    }

    /**
     * Method used to move the body of the Mob.
     * Apply force to the body depending of the direction
     */
    public void move(Direction dir){
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
        if(this.actionState== EntityActionState.IDLE) {
            this.actionState = EntityActionState.ATTACK;
            this.attackDownAnimation.setFinishedState(false);
            this.attackUpAnimation.setFinishedState(false);
            this.attackRightAnimation.setFinishedState(false);
            this.attackLeftAnimation.setFinishedState(false);
            //Add here function to interact with another entity (a monster for exemple)
        }
    }

    /**
     * Set hp of the mob
     */
    public void setHp(int newHp){
        this.hp.set(newHp);
    }

    /**
     * The mob loses one hp
     */
    public void hurt(int hp){
        int val = this.hp.updateAndGet(value -> Math.max(value - hp, THRESHOLD));
        assert (val >= THRESHOLD) : "inconsistent life loss";
        if(this.hp.get()==0) die();
    }

    public void heal(int hp) {
        int val = this.hp.updateAndGet(value -> Math.min(value + hp, BASE_HP));
        assert (val >= BASE_HP) : "inconsistent life gain";
    }

    /**
     * The mob dies. Start dying animation
     */
    public void die(){
        this.actionState = EntityActionState.DYING;
        this.dieAnimation.setFinishedState(false);
    }


    public int getNbWallMoveRandom() {
        return nbWallMoveRandom;
    }

    public void addNbWallMoveRandom() {
        this.nbWallMoveRandom++;
    }

    /**
     * Make the mob randomly move
     * The mob tends to keep the same direction than choose another (3x)
     */
    private void moveRandom(){
        if (System.currentTimeMillis() - lastTurn >= 1000) {
            Direction nextMove = Direction.getRandomDirection();
            if(nextMove!=moveState) nextMove = Direction.getRandomDirection();
            if(nextMove!=moveState) nextMove = Direction.getRandomDirection();
            move(nextMove);
            lastTurn = System.currentTimeMillis();
            nbWallMoveRandom = 0;
        }
    }

    @Override
    public void act(float delta) {
        this.moveRandom();
        super.act(delta);
    }
}

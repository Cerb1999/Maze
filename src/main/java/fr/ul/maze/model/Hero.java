package fr.ul.maze.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import fr.ul.maze.MazeGame;

import java.util.concurrent.atomic.AtomicReference;

public class Hero extends Actor {
    private static final int BASE_HP = 3;
    private static final float BASE_MS = 50f;
    private static final float BASE_ATKRANGE = 64f;
    private static final float BASE_ATKSPEED = 0.5f;
    private static final int THRESHOLD = 0;

    private AtomicReference<Integer> hp = new AtomicReference<>(BASE_HP);
    private float attackSpeed;
    private float attackRange;
    private float walkSpeed;

    private final MazeGame mazeGame;

    private final Body body;
    private final Sprite sprite;

    private HeroMoveState moveState;
    private HeroMoveState lastMoveState;
    private HeroActionState actionState;

    //Animations
    private final Animation walkRightAnimation;
    private final Animation walkUpAnimation;
    private final Animation walkLeftAnimation;
    private final Animation walkDownAnimation;
    private final Animation attackRightAnimation;
    private final Animation attackUpAnimation;
    private final Animation attackLeftAnimation;
    private final Animation attackDownAnimation;
    private final Animation dieAnimation;

    public Hero(MazeGame mazeGame, World world, float xPosition, float yPosition){
        this.attackSpeed = 0.5f;
        this.attackRange = 64f;
        this.walkSpeed = 100;
        this.mazeGame = mazeGame;
        this.moveState = HeroMoveState.IDLE;
        this.lastMoveState = HeroMoveState.DOWN;
        this.actionState = HeroActionState.IDLE;

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

    /**
     * Method used to move the body of the Hero.
     * Apply force to the body depending of the direction.
     * @param dir direction where which the hero move.
     */
    public void moveHero(HeroMoveState dir){
        if(this.actionState==HeroActionState.IDLE){
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
    }

    /**
     * Method used to make the hero attack
     * Start animation attack
     */
    public void attack() {
        if(this.actionState==HeroActionState.IDLE) {
            this.actionState = HeroActionState.ATTACK;
            this.attackDownAnimation.setFinishedState(false);
            this.attackUpAnimation.setFinishedState(false);
            this.attackRightAnimation.setFinishedState(false);
            this.attackLeftAnimation.setFinishedState(false);
            //Add here function to interact with another entity (a monster for exemple)
        }
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
     * The hero die. Start dying animation
     */
    public void die(){
        this.actionState = HeroActionState.DYING;
        this.dieAnimation.setFinishedState(false);
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
        switch (actionState){
            case IDLE:
                switch(moveState) {
                    case RIGHT :
                        walkRightAnimation.update(delta);
                        sprite.set(new Sprite(walkRightAnimation.getFrame()));
                        lastMoveState = HeroMoveState.RIGHT;
                        break;
                    case LEFT :
                        walkLeftAnimation.update(delta);
                        sprite.set(new Sprite(walkLeftAnimation.getFrame()));
                        lastMoveState = HeroMoveState.LEFT;
                        break;
                    case UP :
                        walkUpAnimation.update(delta);
                        sprite.set(new Sprite(walkUpAnimation.getFrame()));
                        lastMoveState = HeroMoveState.UP;
                        break;
                    case DOWN :
                        walkDownAnimation.update(delta);
                        sprite.set(new Sprite(walkDownAnimation.getFrame()));
                        lastMoveState = HeroMoveState.DOWN;
                        break;
                    case IDLE :
                        switch(lastMoveState) {
                            case RIGHT:
                                sprite.set(new Sprite((mazeGame.getManagedTexture("walkR1")).getRegion()));
                                break;
                            case LEFT:
                                sprite.set(new Sprite((mazeGame.getManagedTexture("walkL1")).getRegion()));
                                break;
                            case UP:
                                sprite.set(new Sprite((mazeGame.getManagedTexture("walkN1")).getRegion()));
                                break;
                            case DOWN:
                                sprite.set(new Sprite((mazeGame.getManagedTexture("walkS1")).getRegion()));
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
                        if(!attackRightAnimation.isFinished())sprite.set(new Sprite(attackRightAnimation.getFrame()));
                        else actionState = HeroActionState.IDLE;
                        break;
                    case LEFT:
                        attackLeftAnimation.update(delta);
                        if(!attackLeftAnimation.isFinished())sprite.set(new Sprite(attackLeftAnimation.getFrame()));
                        else actionState = HeroActionState.IDLE;
                        break;
                    case UP:
                        attackUpAnimation.update(delta);
                        if(!attackUpAnimation.isFinished())sprite.set(new Sprite(attackUpAnimation.getFrame()));
                        else actionState = HeroActionState.IDLE;
                        break;
                    case DOWN:
                        attackDownAnimation.update(delta);
                        if(!attackDownAnimation.isFinished())sprite.set(new Sprite(attackDownAnimation.getFrame()));
                        else actionState = HeroActionState.IDLE;
                        break;
                }
                break;
            case DYING:
                dieAnimation.update(delta);
                if(!dieAnimation.isFinished())sprite.set(new Sprite(dieAnimation.getFrame()));
                //else add respawn or game over
                break;
        }

        //Update actor from body position and angle
        this.setRotation(body.getAngle()*  MathUtils.radiansToDegrees);
        this.setPosition(body.getPosition().x-this.getWidth()/2,body.getPosition().y-this.getHeight()/2);

        //Update actor from actor position
        sprite.setPosition(this.getX(),this.getY());

    }
}

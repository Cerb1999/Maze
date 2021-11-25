package fr.ul.maze.controller.keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import fr.ul.maze.model.Direction;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.assets.SoundAssetManager;
import fr.ul.maze.model.entities.EntityActionState;
import fr.ul.maze.model.entities.Hero;

import java.util.concurrent.atomic.AtomicReference;

public final class HeroMoveController implements InputProcessor {
    private final AtomicReference<MasterState> state;

    public HeroMoveController(final AtomicReference<MasterState> state) {
        this.state = state;
    }



    /**
     * Method used to move the body of the Hero.
     * Apply force to the body depending of the direction.
     * @param d direction where which the hero move.
     */
    public void moveHero(Direction d) {
        AtomicReference<Hero> hero = this.state.get().getHero();

        hero.updateAndGet(h -> {
            h.updateBody(body -> {
                if(h.getActionState() == EntityActionState.IDLE){
                    if(d==Direction.UPRIGHT || d==Direction.UPLEFT) h.setMoveState(Direction.UP);
                    else if(d==Direction.DOWNRIGHT || d==Direction.DOWNLEFT) h.setMoveState(Direction.DOWN);
                    else h.setMoveState(d);
                    switch(d) {
                        case UP:
                            body.setLinearVelocity(0,h.getWalkSpeed());
                            break;
                        case UPRIGHT:
                            body.setLinearVelocity(h.getWalkSpeed(), h.getWalkSpeed());
                            break;
                        case UPLEFT:
                            body.setLinearVelocity(-h.getWalkSpeed(), h.getWalkSpeed());
                            break;
                        case DOWN:
                            body.setLinearVelocity(0,-h.getWalkSpeed());
                            break;
                        case DOWNRIGHT:
                            body.setLinearVelocity(h.getWalkSpeed(), -h.getWalkSpeed());
                            break;
                        case DOWNLEFT:
                            body.setLinearVelocity(-h.getWalkSpeed(), -h.getWalkSpeed());
                            break;
                        case RIGHT:
                            body.setLinearVelocity(h.getWalkSpeed(),0);
                            break;
                        case LEFT:
                            body.setLinearVelocity(-h.getWalkSpeed(),0);
                            break;
                        case IDLE:
                            body.setLinearVelocity(0,0);
                            break;
                    }
                    SoundAssetManager.getInstance().stopFootstep();
                    SoundAssetManager.getInstance().playFootstep();
                }
                else{
                    body.setLinearVelocity(0,0);
                    SoundAssetManager.getInstance().stopFootstep();
                }

            });
            return h;
        });
    }

    @Override
    public boolean keyDown(int i) {
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.RIGHT))this.moveHero(Direction.UPRIGHT);
        else if(Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.LEFT))this.moveHero(Direction.UPLEFT);
        else if(Gdx.input.isKeyPressed(Input.Keys.UP)) this.moveHero(Direction.UP);
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.RIGHT))this.moveHero(Direction.DOWNRIGHT);
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.LEFT))this.moveHero(Direction.DOWNLEFT);
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) this.moveHero(Direction.DOWN);
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) this.moveHero(Direction.RIGHT);
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) this.moveHero(Direction.LEFT);

        return false;
    }

    @Override
    public boolean keyUp(int i) {
        if(!Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            this.moveHero(Direction.IDLE);
            SoundAssetManager.getInstance().stopFootstep();
        }
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}

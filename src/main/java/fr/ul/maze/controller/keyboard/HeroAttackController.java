package fr.ul.maze.controller.keyboard;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import exceptions.Exn;
import fr.ul.maze.model.Direction;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.entities.EntityActionState;
import fr.ul.maze.model.entities.Hero;

import java.util.concurrent.atomic.AtomicReference;

public final class HeroAttackController implements InputProcessor {
    private final AtomicReference<MasterState> state;

    public HeroAttackController(final AtomicReference<MasterState> state) {
        this.state = state;
    }

    /**
     * Method used to make the hero attack
     * Start animation attack
     */
    public void attack() {
        AtomicReference<Hero> hero = this.state.get().getHero();
        if(hero.get().getActionState() == EntityActionState.IDLE) {
            hero.get().setActionState(EntityActionState.ATTACK);
            hero.get().createSwordBody();
        }
    }

    @Override
    public boolean keyDown(int i) {
        if (i == Input.Keys.SPACE) {
            this.attack();
        }

        return false;
    }

    @Override
    public boolean keyUp(int i) {
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

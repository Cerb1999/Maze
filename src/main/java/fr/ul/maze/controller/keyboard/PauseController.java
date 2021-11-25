package fr.ul.maze.controller.keyboard;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import exceptions.Exn;
import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.view.screens.MasterScreen;

import java.util.concurrent.atomic.AtomicReference;

public final class PauseController implements InputProcessor {
    private final AtomicReference<MasterState> state;
    private final MasterScreen master;

    private boolean paused;

    public PauseController(final boolean pause, final AtomicReference<MasterState> state, MasterScreen masterScreen) {
        this.state = state;
        this.paused = pause;
        this.master = masterScreen;
    }

    public void pause() {
        TimerSingleton.stop();
        master.switchScreen(master.PAUSE_SCREEN.get());
    }

    public void unpause() {
        TimerSingleton.start();
        master.switchScreen(master.MAIN_SCREEN.get());
    }

    @Override
    public boolean keyDown(int i) {
        switch (i) {
            case Input.Keys.ESCAPE: {
                paused^=true;
                if(paused) pause();
                else unpause();
                break;
            }
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

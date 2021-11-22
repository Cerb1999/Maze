package fr.ul.maze.controller.keyboard;

import com.badlogic.gdx.InputProcessor;
import exceptions.Exn;

public final class PauseController implements InputProcessor {
    private boolean paused;

    public PauseController() {
        this.paused = false;
    }

    public void pause() {
        Exn.todo("pause the game");
    }

    public void unpause() {
        Exn.todo("unpause the game");
    }

    @Override
    public boolean keyDown(int i) {
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

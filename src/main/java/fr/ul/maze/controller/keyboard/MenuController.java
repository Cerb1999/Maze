package fr.ul.maze.controller.keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.controller.tasks.GameTimerTask;
import fr.ul.maze.controller.tasks.TaskType;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.view.screens.MasterScreen;

import java.util.concurrent.atomic.AtomicReference;

public final class MenuController implements InputProcessor {
    private final AtomicReference<MasterState> state;
    private final MasterScreen master;

    public MenuController(final AtomicReference<MasterState> state, MasterScreen masterScreen) {
        this.state = state;
        this.master = masterScreen;
    }

    @Override
    public boolean keyDown(int i) {
        switch (i) {
            case Input.Keys.SPACE: {
                TimerSingleton.init().addTask(new GameTimerTask(state, master), TaskType.GAME);
                master.switchScreen(master.MAIN_SCREEN.get());
                break;
            }
            case Input.Keys.ESCAPE: {
                Gdx.app.exit();
                System.exit(0);
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

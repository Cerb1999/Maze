package fr.ul.maze.controller.keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Timer;
import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.assets.SoundAssetManager;
import fr.ul.maze.view.screens.MapScreen;
import fr.ul.maze.view.screens.MasterScreen;

import java.util.concurrent.atomic.AtomicReference;

public final class GameOverController implements InputProcessor {
    private final AtomicReference<MasterState> state;
    private final MasterScreen master;

    public GameOverController(final AtomicReference<MasterState> state, MasterScreen masterScreen) {
        this.state = state;
        this.master = masterScreen;
    }

    @Override
    public boolean keyDown(int i) {
        switch (i) {
            case Input.Keys.SPACE: {
                TimerSingleton.refresh();
                master.switchScreen(master.MAIN_SCREEN.get());
                state.get().reset();
                ((MapScreen) master.MAIN_SCREEN.get()).constructLevel();
                TimerSingleton.start();
                break;
            }
            case Input.Keys.ESCAPE: {
                Gdx.app.exit();
                System.exit(0);
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

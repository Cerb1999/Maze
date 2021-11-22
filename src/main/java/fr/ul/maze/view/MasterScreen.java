package fr.ul.maze.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.view.screens.MapScreen;
import fr.ul.maze.view.screens.PauseScreen;
import utils.functional.Lazy;

import java.util.concurrent.atomic.AtomicReference;

public final class MasterScreen implements Screen {
    public final Lazy<Screen> MAIN_SCREEN;
    public final Lazy<Screen> PAUSE_SCREEN;

    private Screen currentScreen;

    public MasterScreen(final Stage stage, final AtomicReference<MasterState> state) {
        MAIN_SCREEN = () -> new MapScreen(stage, state);
        PAUSE_SCREEN = () -> new PauseScreen(stage, state);
    }

    public void switchScreen(Screen newScreen) {
        this.currentScreen = newScreen;
    }

    @Override
    public void show() {
        this.currentScreen.show();
    }

    @Override
    public void render(float v) {
        this.currentScreen.render(v);
    }

    @Override
    public void resize(int i, int i1) {
        this.currentScreen.resize(i, i1);
    }

    @Override
    public void pause() {
        this.currentScreen.pause();
    }

    @Override
    public void resume() {
        this.currentScreen.resume();
    }

    @Override
    public void hide() {
        this.currentScreen.hide();
    }

    @Override
    public void dispose() {
        this.currentScreen.dispose();
        this.MAIN_SCREEN.get().dispose();
        this.PAUSE_SCREEN.get().dispose();
    }
}

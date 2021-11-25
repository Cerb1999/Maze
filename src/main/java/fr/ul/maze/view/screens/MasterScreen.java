package fr.ul.maze.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import fr.ul.maze.model.MasterState;
import utils.functional.Lazy;

import java.util.concurrent.atomic.AtomicReference;

public final class MasterScreen implements Screen {
    private static String BACKGROUND_NAME = "Background.jpg";
    private static String FONT_NAME = "Ancient.ttf";

    public final Lazy<Screen> MAIN_SCREEN;
    public final Lazy<Screen> PAUSE_SCREEN;
    public final Lazy<Screen> MENU_SCREEN;
    public final Lazy<Screen> LEVEL_TRANSITION_SCREEN;
    public final Lazy<Screen> GAME_OVER_SCREEN;
    private Screen currentScreen;
    
    private BitmapFont fontBIG, fontMID, fontSMALL;
    private Texture background;

    public MasterScreen(final AtomicReference<MasterState> state) {
        initFontAndBackground();

        MAIN_SCREEN = () -> new MapScreen(state, this);
        PAUSE_SCREEN = () -> new PauseScreen(state, this);
        MENU_SCREEN = () -> new MenuScreen(state, this);
        LEVEL_TRANSITION_SCREEN = () -> new LevelTransitionScreen(state, this);
        GAME_OVER_SCREEN = () -> new GameOverScreen(state, this);
    }

    private void initFontAndBackground() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_NAME));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 200;
        params.color = Color.RED;
        fontBIG = generator.generateFont(params);
        params.size = 140;
        params.color = Color.WHITE;
        fontMID = generator.generateFont(params);
        params.size = 100;
        params.color = Color.YELLOW;
        fontSMALL = generator.generateFont(params);
        generator.dispose();

        background = new Texture(Gdx.files.internal(BACKGROUND_NAME));
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
        //this.currentScreen.dispose();
        this.MAIN_SCREEN.get().dispose();
        this.PAUSE_SCREEN.get().dispose();
        this.MENU_SCREEN.get().dispose();
        this.LEVEL_TRANSITION_SCREEN.get().dispose();
        this.GAME_OVER_SCREEN.get().dispose();
        this.fontBIG.dispose();
        this.fontMID.dispose();
    }

    public BitmapFont getFontBIG() {
        return fontBIG;
    }

    public BitmapFont getFontMID() {
        return fontMID;
    }

    public BitmapFont getFontSMALL() {
        return fontSMALL;
    }

    public Texture getBackground() {
        return background;
    }
}

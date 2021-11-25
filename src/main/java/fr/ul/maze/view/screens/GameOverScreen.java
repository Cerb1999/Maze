package fr.ul.maze.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import fr.ul.maze.controller.keyboard.GameOverController;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.assets.MusicAssetManager;
import fr.ul.maze.model.maze.Maze;
import fr.ul.maze.view.map.RigidSquare;

import java.util.concurrent.atomic.AtomicReference;

public class GameOverScreen implements Screen {
    private final MasterScreen master;
    private final Stage stage;
    private final AtomicReference<MasterState> state;
    private final OrthographicCamera camera;

    private InputMultiplexer mux;
    private GameOverController gameOverController;

    public GameOverScreen(final AtomicReference<MasterState> state, MasterScreen masterScreen) {
        this.stage = new Stage();

        this.state = state;
        this.master = masterScreen;

        this.camera = new OrthographicCamera(RigidSquare.WIDTH * Maze.WIDTH, RigidSquare.HEIGHT * Maze.HEIGHT);

        this.stage.getViewport().setCamera(this.camera);
        this.camera.position.set(this.camera.viewportWidth / 2, this.camera.viewportHeight / 2, 0);
        this.stage.setViewport(new FitViewport(this.camera.viewportWidth, this.camera.viewportHeight, this.camera));

        this.constructScreen();
    }

    private void constructScreen() {
        Label.LabelStyle lStyle = new Label.LabelStyle();
        lStyle.font = master.getFontBIG();
        Label l = new Label("Game Over ...", lStyle);

        Label.LabelStyle l2Style = new Label.LabelStyle();
        l2Style.font = master.getFontMID();

        //TODO new game
        Label restart = new Label("[SPACE] Recommencer", l2Style);
        Label exit = new Label("[ESC] Quitter", l2Style);

        Table table = new Table();
        table.setFillParent(true);
        table.align(Align.center);
        table.top();
        table.add(l).pad(stage.getCamera().viewportHeight/6, 0, stage.getCamera().viewportHeight/4, 0);
        table.row();
        //btnGroup.add(restart);
        table.row();
        table.add(exit);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        gameOverController = new GameOverController(state, master);

        stage.addActor(table);
        mux = new InputMultiplexer();
        mux.addProcessor(stage);
        mux.addProcessor(gameOverController);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(mux);
        MusicAssetManager.getInstance().playGameoverMusic();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update();
        this.stage.getBatch().setProjectionMatrix(this.camera.combined);

        this.stage.getBatch().begin();
        this.stage.getBatch().draw(master.getBackground(), 0, 0, stage.getCamera().viewportWidth,stage.getCamera().viewportHeight);
        this.stage.getBatch().end();
        this.stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        this.stage.getViewport().update(i, i1, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }
}

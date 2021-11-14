package fr.ul.maze.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.ul.maze.MazeGame;
import fr.ul.maze.model.GameState;

public abstract class View implements Screen {
    protected MazeGame mazeGame;
    protected GameState gameState;
    protected Stage stage;
    protected InputMultiplexer inputMultiplexer;

    public View(MazeGame mazeGame, GameState gameState) {
        this.mazeGame = mazeGame;
        this.gameState = gameState;
        inputMultiplexer = new InputMultiplexer();
        stage = new Stage(new ScreenViewport());
    }

    @Override
    final public void show() {
        // Map the controller
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        // Screen-specific initialization
        init();
    }

    public void init() {

    }

    @Override
    final public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getCamera().update();
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);

        if(stage != null) {
            stage.act(delta);
            stage.draw();
        }



        Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);
        debugRenderer.render(gameState.getWorld(), stage.getCamera().combined);

        //Apply physics to our world
        gameState.getWorld().step(1/60f, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        if(stage != null) stage.dispose();
        stage = null;
    }
}

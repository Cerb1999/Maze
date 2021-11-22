package fr.ul.maze.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import fr.ul.maze.MazeGame;
import fr.ul.maze.model.GameState;
import fr.ul.maze.model.Level;

public abstract class View implements Screen {
    protected MazeGame mazeGame;
    protected GameState gameState;
    protected Stage stage;
    protected InputMultiplexer inputMultiplexer;
    private final Texture bg;

    public View(MazeGame mazeGame, GameState gameState) {
        this.mazeGame = mazeGame;
        this.gameState = gameState;

        inputMultiplexer = new InputMultiplexer();
        stage = new Stage(new ScreenViewport());
        bg = new Texture(Gdx.files.internal("Bg.jpg"));

        stage.getViewport().setCamera(new OrthographicCamera(64 * Level.WIDTH + 64,64 * Level.HEIGHT + 64));
        stage.getCamera().position.set((stage.getCamera().viewportWidth / 2), (stage.getCamera().viewportHeight / 2), 0);
        stage.setViewport(new StretchViewport(stage.getCamera().viewportWidth, stage.getCamera().viewportHeight, stage.getCamera()));

        stage.getViewport().apply();
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
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getCamera().update();
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);

        if(stage != null) {
            stage.act(delta);
            stage.getBatch().begin();
            stage.getBatch().draw(bg, 0, 0, stage.getCamera().viewportWidth,stage.getCamera().viewportHeight);
            stage.getBatch().end();
            stage.draw();
        }



        //Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);
        //debugRenderer.render(gameState.getWorld(), stage.getCamera().combined);

        //Apply physics to our world
        gameState.getWorld().step(1/60f, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getCamera().position.set((stage.getCamera().viewportWidth / 2), (stage.getCamera().viewportHeight / 2), 0);
    }

    @Override
    public void dispose() {
        if(stage != null) stage.dispose();
        stage = null;
    }
}

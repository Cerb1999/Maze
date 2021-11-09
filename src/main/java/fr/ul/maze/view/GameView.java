package fr.ul.maze.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import fr.ul.maze.MazeGame;
import fr.ul.maze.controller.HeroController;
import fr.ul.maze.model.GameState;

public class GameView extends View {

    public GameView(MazeGame parent, GameState model) {
        super(parent, model);
        Box2D.init();

        //Add new input controller
        inputMultiplexer.addProcessor(new HeroController(mazeGame, gameState));
        Gdx.input.setInputProcessor(inputMultiplexer);

        model.setStage(this.stage);

        stage.getViewport().setCamera(new OrthographicCamera(3264,1984));
        stage.getCamera().position.set((stage.getCamera().viewportWidth / 2) - 64, (stage.getCamera().viewportHeight / 2) - 64, 0);
        stage.setViewport(new StretchViewport(stage.getCamera().viewportWidth, stage.getCamera().viewportHeight, stage.getCamera()));
    }

    @Override
    public void init() {

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
}

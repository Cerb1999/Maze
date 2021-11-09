package fr.ul.maze.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fr.ul.maze.MazeGame;
import fr.ul.maze.controller.PauseController;
import fr.ul.maze.model.GameState;

public class PauseScreenView extends View {

    public PauseScreenView(MazeGame parent, GameState model) {
        super(parent, model);
        Box2D.init();
        inputMultiplexer.addProcessor(new PauseController(parent, gameState));
        Gdx.input.setInputProcessor(inputMultiplexer);
        stage.addActor(model.getPause().getButton());
        model.getPause().getButton().addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameState.switchState(); parent.switchScreen();
            };
        });
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

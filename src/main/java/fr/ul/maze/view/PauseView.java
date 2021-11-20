package fr.ul.maze.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import fr.ul.maze.MazeGame;
import fr.ul.maze.controller.PauseController;
import fr.ul.maze.model.GameState;
import fr.ul.maze.model.State;

public class PauseView extends View {
    private Table btnGroup;
    private TextButton pauseButton;
    private Label l;
    private BitmapFont font;

    public PauseView(MazeGame parent, GameState model) {
        super(parent, model);
        Box2D.init();
        inputMultiplexer.addProcessor(new PauseController(parent, gameState));
        Gdx.input.setInputProcessor(inputMultiplexer);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Ancient.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 160;
        font = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.RED;

        l = new Label("Pause", labelStyle);

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = font;

        pauseButton = new TextButton("Continuer", btnStyle);

        btnGroup = new Table();
        btnGroup.setFillParent(true);
        btnGroup.align(Align.center);

        btnGroup.top();
        btnGroup.add(l).pad(stage.getCamera().viewportHeight/6, 0, stage.getCamera().viewportHeight/4, 0);
        btnGroup.row();
        btnGroup.add(pauseButton);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameState.switchState();
                State state = gameState.getState();
                if(state == State.GAME_RUNNING) gameState.restartTimer();
                else if(state == State.GAME_PAUSED) gameState.stopTimer();
                parent.switchScreen();
            }
        });

        stage.addActor(btnGroup);
        model.setStage(this.stage);
        Gdx.input.setInputProcessor(stage);
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

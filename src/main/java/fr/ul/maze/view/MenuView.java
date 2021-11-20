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
import fr.ul.maze.model.GameState;
import fr.ul.maze.model.State;

public class MenuView extends View {
    private Table btnGroup;
    private TextButton startButton, exitButton;
    private TextButton.TextButtonStyle btnStyle;
    private Label l;
    private BitmapFont font;

    public MenuView(MazeGame parent, GameState model) {
        super(parent, model);
        Box2D.init();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Ancient.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 160;
        font = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.RED;

        l = new Label("Maze", labelStyle);

        btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = font;

        startButton = new TextButton("Commencer", btnStyle);
        exitButton = new TextButton("Quitter", btnStyle);

        btnGroup = new Table();
        btnGroup.setFillParent(true);
        btnGroup.align(Align.center);

        btnGroup.top();
        btnGroup.add(l).pad(stage.getCamera().viewportHeight/6, 0, stage.getCamera().viewportHeight/4, 0);
        btnGroup.row();
        btnGroup.add(startButton);
        btnGroup.row();
        btnGroup.add(exitButton);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mazeGame.switchState(State.GAME_RUNNING);
                model.launchTimer();
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                System.exit(0);
            }
        });

        stage.addActor(btnGroup);
        model.setStage(this.stage);
        Gdx.input.setInputProcessor(stage);
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

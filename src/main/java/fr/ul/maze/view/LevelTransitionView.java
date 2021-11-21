package fr.ul.maze.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import fr.ul.maze.MazeGame;
import fr.ul.maze.model.GameState;
import fr.ul.maze.model.State;

public class LevelTransitionView extends View {
    Table btnGroup;
    Label lvl;
    BitmapFont font;

    public LevelTransitionView(MazeGame parent, GameState model) {
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

        lvl = new Label("Niveau suivant ...", labelStyle);


        btnGroup = new Table();
        btnGroup.setFillParent(true);
        btnGroup.align(Align.center);

        btnGroup.add(lvl);


        stage.addActor(btnGroup);
        model.setStage(this.stage);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void init() {
        Timer timer = new Timer();
        Timer.Task task = timer.scheduleTask(new Timer.Task() {
            @Override
            public void run () {
                mazeGame.switchState(State.GAME_RUNNING);
                gameState.refreshTimer();
            }
        }, 2);
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

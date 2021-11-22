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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import fr.ul.maze.MazeGame;
import fr.ul.maze.model.GameState;
import fr.ul.maze.model.State;

public class LevelTransitionView extends View {
    private final Table table;
    private final Label curLvl, lvl;
    private final Label.LabelStyle lvlStyle, curLvlStyle;
    private final BitmapFont font;

    public LevelTransitionView(MazeGame parent, GameState model) {
        super(parent, model);
        Box2D.init();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Ancient.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 160;
        font = generator.generateFont(parameter);
        generator.dispose();

        lvlStyle = new Label.LabelStyle();
        lvlStyle.font = font;
        lvlStyle.fontColor = Color.WHITE;
        lvl = new Label("Niveau suivant ..." + gameState.getLevelNumber(), lvlStyle);

        curLvlStyle = new Label.LabelStyle();
        curLvlStyle.font = font;
        curLvlStyle.fontColor = Color.RED;
        curLvl = new Label("Niveau : " + gameState.getLevelNumber() + " complété !", curLvlStyle);

        table = new Table();
        table.setFillParent(true);
        table.align(Align.center);
        table.top();
        table.add(curLvl).pad(stage.getCamera().viewportHeight/6, 0, stage.getCamera().viewportHeight/4, 0);
        table.row();
        table.add(lvl);

        model.setStage(this.stage);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void init() {
        stage.addActor(table);
        Timer timer = new Timer();
        Timer.Task task = timer.scheduleTask(new Timer.Task() {
            @Override
            public void run () {
                mazeGame.switchState(State.GAME_RUNNING);
                stage.setViewport(new StretchViewport(stage.getCamera().viewportWidth + 128, stage.getCamera().viewportHeight + 160, stage.getCamera()));
                gameState.refreshTimer();
                curLvl.setText("Niveau : " + gameState.getLevelNumber() + " complété !");
            }
        }, 2);
        super.init();
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

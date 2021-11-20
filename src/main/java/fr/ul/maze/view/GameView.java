package fr.ul.maze.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import fr.ul.maze.MazeGame;
import fr.ul.maze.controller.HeroController;
import fr.ul.maze.controller.PauseController;
import fr.ul.maze.model.GameState;
import fr.ul.maze.model.Level;
import fr.ul.maze.model.State;
import fr.ul.maze.model.TimerTask;
import javafx.scene.text.Font;

public class GameView extends View {
    private BitmapFont font;
    private Label time;
    private Table table;

    public GameView(MazeGame parent, GameState model) {
        super(parent, model);
        Box2D.init();
        inputMultiplexer.addProcessor(new HeroController(mazeGame, gameState));
        inputMultiplexer.addProcessor(new PauseController(mazeGame, gameState));
        Gdx.input.setInputProcessor(inputMultiplexer);

        stage.getCamera().position.set((stage.getCamera().viewportWidth / 2) - 128, (stage.getCamera().viewportHeight / 2) - 128, 0);
        stage.setViewport(new StretchViewport(stage.getCamera().viewportWidth + 128, stage.getCamera().viewportHeight + 160, stage.getCamera()));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Ancient.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;
        font = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.YELLOW;

        time = new Label(gameState.getTime(), labelStyle);

        table = new Table();
        table.setFillParent(true);
        table.align(Align.right);

        table.top();
        table.add(time).padRight(stage.getCamera().viewportWidth/10).padTop(160);
        table.row();

        stage.addActor(table);
        model.setStage(this.stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.time.setText(gameState.getTime());
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getCamera().position.set((stage.getCamera().viewportWidth / 2) - 128, (stage.getCamera().viewportHeight / 2) - 160, 0);
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

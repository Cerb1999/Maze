package fr.ul.maze.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import fr.ul.maze.MazeGame;
import fr.ul.maze.controller.HeroController;
import fr.ul.maze.controller.PauseController;
import fr.ul.maze.model.GameState;

public class GameView extends View {
    private final BitmapFont font;
    private final Label time;
    private final Label.LabelStyle lTime;
    private final Table table;

    public GameView(MazeGame parent, GameState model) {
        super(parent, model);
        Box2D.init();

        inputMultiplexer.addProcessor(new HeroController(mazeGame, gameState));
        inputMultiplexer.addProcessor(new PauseController(mazeGame, gameState));

        Gdx.input.setInputProcessor(inputMultiplexer);
        stage.setViewport(new StretchViewport(stage.getCamera().viewportWidth + 128, stage.getCamera().viewportHeight + 160, stage.getCamera()));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Ancient.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;
        font = generator.generateFont(parameter);
        generator.dispose();

        lTime = new Label.LabelStyle();
        lTime.font = font;
        lTime.fontColor = Color.YELLOW;

        time = new Label(gameState.getTime(), lTime);

        table = new Table();
        table.setFillParent(true);
        table.align(Align.right);
        table.top();
        table.add(time).padRight(stage.getCamera().viewportWidth/10);
        table.row();

        gameState.setStage(this.stage);
    }

    @Override
    public void init() {
        stage.addActor(table);
        super.init();
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        this.time.setText(gameState.getTime());
        //System.out.println(stage.getActors());
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getCamera().position.set((stage.getCamera().viewportWidth / 2), (stage.getCamera().viewportHeight / 2), 0);
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

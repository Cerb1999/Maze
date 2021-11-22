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

public class GameOverView extends View {
    private final Table table;
    private final TextButton restartButton;
    private final TextButton exitButton;
    private final TextButton.TextButtonStyle bStyle;
    private final Label l;
    private final Label.LabelStyle lStyle;
    private final BitmapFont font;

    public GameOverView(MazeGame parent, GameState model) {
        super(parent, model);
        Box2D.init();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Ancient.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 160;
        font = generator.generateFont(parameter);
        generator.dispose();


        lStyle = new Label.LabelStyle();
        lStyle.font = font;
        lStyle.fontColor = Color.RED;
        l = new Label("Game Over ...", lStyle);

        bStyle = new TextButton.TextButtonStyle();
        bStyle.font = font;

        //TODO new game
        restartButton = new TextButton("Recommencer", bStyle);
        exitButton = new TextButton("Quitter", bStyle);

        table = new Table();
        table.setFillParent(true);
        table.align(Align.center);
        table.top();
        table.add(l).pad(stage.getCamera().viewportHeight/6, 0, stage.getCamera().viewportHeight/4, 0);
        table.row();
        //btnGroup.add(restartButton);
        table.row();
        table.add(exitButton);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //DesktopLauncher.main(new String[] {"new game"});
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                System.exit(0);
            }
        });

        model.setStage(this.stage);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void init() {
        stage.addActor(table);
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

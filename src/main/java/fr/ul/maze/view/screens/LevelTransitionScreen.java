package fr.ul.maze.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.maze.Maze;
import fr.ul.maze.view.map.RigidSquare;

import java.util.concurrent.atomic.AtomicReference;

public class LevelTransitionScreen implements Screen {
    private final AtomicReference<MasterState> state;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final MasterScreen master;

    private Label curLvl;

    public LevelTransitionScreen(final AtomicReference<MasterState> state, MasterScreen masterScreen) {
        this.state = state;
        this.master = masterScreen;

        this.stage = new Stage();
        this.camera = new OrthographicCamera(RigidSquare.WIDTH * Maze.WIDTH, RigidSquare.HEIGHT * Maze.HEIGHT);

        this.stage.getViewport().setCamera(this.camera);
        this.camera.position.set(this.camera.viewportWidth / 2, this.camera.viewportHeight / 2, 0);
        this.stage.setViewport(new FitViewport(this.camera.viewportWidth, this.camera.viewportHeight, this.camera));

        this.constructScreen();
    }

    private void constructScreen() {
        Label.LabelStyle curLvlStyle = new Label.LabelStyle();
        curLvlStyle.font = master.getFontBIG();
        curLvl = new Label("Niveau : " + state.get().getCurrentLevelNumber() + " complété !", curLvlStyle);

        Label.LabelStyle lvlStyle = new Label.LabelStyle();
        lvlStyle.font = master.getFontMID();
        Label lvl = new Label("Niveau suivant ...", lvlStyle);


        Table table = new Table();
        table.setFillParent(true);
        table.align(Align.center);
        table.top();
        table.add(curLvl).pad(stage.getCamera().viewportHeight/6, 0, stage.getCamera().viewportHeight/4, 0);
        table.row();
        table.add(lvl);

        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update();
        this.stage.getBatch().setProjectionMatrix(this.camera.combined);
        this.curLvl.setText("Niveau : " + state.get().getCurrentLevelNumber() + " complété !");

        this.stage.getBatch().begin();
        this.stage.getBatch().draw(master.getBackground(), 0, 0, stage.getCamera().viewportWidth,stage.getCamera().viewportHeight);
        this.stage.getBatch().end();
        this.stage.draw();
    }

    public void resize(int i, int i1) {
        this.stage.getViewport().update(i, i1, true);
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

    @Override
    public void dispose() {
        this.stage.dispose();
    }
}
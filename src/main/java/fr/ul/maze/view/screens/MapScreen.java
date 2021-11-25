package fr.ul.maze.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import compiler.Options;
import fr.ul.maze.controller.MobMoveController;
import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.controller.contact.MasterContactController;
import fr.ul.maze.controller.keyboard.HeroAttackController;
import fr.ul.maze.controller.keyboard.HeroMoveController;
import fr.ul.maze.controller.keyboard.PauseController;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.assets.MusicAssetManager;
import fr.ul.maze.model.entities.Mob;
import fr.ul.maze.model.map.Square;
import fr.ul.maze.model.maze.Maze;
import fr.ul.maze.view.actors.HeroActor;
import fr.ul.maze.view.actors.LadderActor;
import fr.ul.maze.view.actors.MobActor;
import fr.ul.maze.view.actors.debug.MazePathsDebug;
import fr.ul.maze.view.map.RigidSquare;
import utils.RefCell;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class MapScreen implements Screen {
    private final AtomicReference<MasterState> state;
    private PauseController pauseController;
    private final Camera camera;
    private InputMultiplexer mux;
    private final Stage stage;
    private final MasterScreen master;

    private LinkedList<RigidSquare> squares;
    private RefCell<HeroActor> hero;
    private RefCell<LadderActor> ladder;
    private RefCell<List<MobActor>> mobs;

    private HeroMoveController heroMoveController;
    private HeroAttackController heroAttackController;
    private MasterContactController masterContactListener;
    private RefCell<List<MobMoveController>> mobMoveControllers;

    private Box2DDebugRenderer debugRenderer;

    private World world;

    private Label time;


    public MapScreen(final Stage stage, final AtomicReference<MasterState> state, MasterScreen masterScreen) {
        this.state = state;
        this.stage = new Stage();
        this.master = masterScreen;

        this.camera = new OrthographicCamera(RigidSquare.WIDTH * Maze.WIDTH, RigidSquare.HEIGHT * Maze.HEIGHT);

        this.stage.getViewport().setCamera(this.camera);
        this.camera.position.set(this.camera.viewportWidth / 2, this.camera.viewportHeight / 2 + 160, 0);
        this.stage.setViewport(new FitViewport(this.camera.viewportWidth, this.camera.viewportHeight + 160, this.camera));
        this.constructLevel();
        this.constructScreen();
        MusicAssetManager.getInstance().playGameMusic();
    }

    private void constructScreen() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Ancient.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle lTime = new Label.LabelStyle();
        lTime.font = font;
        lTime.fontColor = Color.YELLOW;

        time = new Label(TimerSingleton.getTime()+"", lTime);

        Table table = new Table();
        table.setFillParent(true);
        table.align(Align.right);
        table.top();
        table.add(time).padRight(stage.getCamera().viewportWidth/10);
        table.row();

        pauseController = new PauseController(false, state, master);

        stage.addActor(table);
        mux.addProcessor(pauseController);
        Gdx.input.setInputProcessor(mux);
    }

    @Override
    public void show() {
        MusicAssetManager.getInstance().playGameMusic();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update();
        this.stage.getBatch().setProjectionMatrix(this.camera.combined);


        this.mobMoveControllers.inner.forEach(MobMoveController::moveMob);
        this.stage.act(v);
        this.stage.getBatch().begin();
        this.stage.getBatch().draw(master.getBackground(), 0, 0, stage.getCamera().viewportWidth,stage.getCamera().viewportHeight);
        this.stage.getBatch().end();
        this.time.setText(TimerSingleton.getTime());

        this.stage.draw();

        if (Options.DEBUG)
            this.debugRenderer.render(this.world, this.camera.combined);

        this.world.step(1f / 60f, 6, 2);
    }

    @Override
    public void resize(int i, int i1) {
        this.stage.getViewport().update(i, i1, true);
    }

    @Override
    public void pause() {
        MusicAssetManager.getInstance().stopMusic();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //this.stage.dispose();
        MusicAssetManager.getInstance().stopMusic();
    }

    public void constructLevel() {
        this.stage.clear();
        this.world = this.state.get().getWorld();

        this.hero = new RefCell<>();
        this.ladder = new RefCell<>();
        this.mobs = new RefCell<>();
        this.mobMoveControllers = new RefCell<>();

        this.squares = new LinkedList<>();
        this.state.updateAndGet(st -> {
            st.getLevel().get().forEachCell( sq -> this.squares.push(new RigidSquare(st.getWorld(), sq)));
            this.squares.forEach(this.stage::addActor);

            this.stage.addActor(new MazePathsDebug(st.getLevel()));

            this.hero.inner = new HeroActor(st.getWorld(), st.getHero());
            this.stage.addActor(this.hero.inner);

            this.ladder.inner = new LadderActor(st.getWorld(), st.getLadder());
            this.stage.addActor(this.ladder.inner);

            this.mobMoveControllers.inner = new LinkedList<>();
            this.mobs.inner = new LinkedList<>();
            for (AtomicReference<Mob> mob : st.getMobs()) {
                this.mobs.inner.add(new MobActor(st.getWorld(), mob));
                this.mobMoveControllers.inner.add(new MobMoveController(this.state, mob));
            }
            this.mobs.inner.forEach(this.stage::addActor);

            return st;
        });

        this.mux = new InputMultiplexer(stage);

        this.heroMoveController = new HeroMoveController(this.state);
        this.mux.addProcessor(this.heroMoveController);

        this.heroAttackController = new HeroAttackController(this.state);
        this.mux.addProcessor(this.heroAttackController);

        this.debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);

        this.masterContactListener = new MasterContactController(this.state, this, master);

        this.world.setContactListener(this.masterContactListener);

        //order sprite z-index
        this.mobs.inner.forEach(Actor::toFront);
        this.hero.inner.toFront();
        this.squares.forEach(rigidSquare -> {if(rigidSquare.getSquareType()== Square.Type.WALL)rigidSquare.toFront();});
    }
}

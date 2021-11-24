package fr.ul.maze.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import compiler.Options;
import fr.ul.maze.controller.MobMoveController;
import fr.ul.maze.controller.contact.MasterContactController;
import fr.ul.maze.controller.keyboard.HeroAttackController;
import fr.ul.maze.controller.keyboard.HeroMoveController;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.entities.Mob;
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

    private final Camera camera;
    private InputMultiplexer mux;
    private final Stage stage;

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


    public MapScreen(final Stage stage, final AtomicReference<MasterState> state) {
        this.state = state;

        this.stage = stage;
        this.camera = new OrthographicCamera(RigidSquare.WIDTH * Maze.WIDTH, RigidSquare.HEIGHT * Maze.HEIGHT);

        this.stage.getViewport().setCamera(this.camera);
        this.camera.position.set(this.camera.viewportWidth / 2, this.camera.viewportHeight / 2, 0);
        this.stage.setViewport(new FitViewport(this.camera.viewportWidth, this.camera.viewportHeight, this.camera));

        this.constructLevel();

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


        this.mobMoveControllers.inner.forEach(MobMoveController::moveMob);
        this.stage.act(v);
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

        this.mux = new InputMultiplexer(this.stage);

        this.heroMoveController = new HeroMoveController(this.state);
        this.mux.addProcessor(this.heroMoveController);

        this.heroAttackController = new HeroAttackController(this.state);
        this.mux.addProcessor(this.heroAttackController);

        Gdx.input.setInputProcessor(this.mux);

        this.debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);

        this.masterContactListener = new MasterContactController(this.state, this);

        this.world.setContactListener(this.masterContactListener);

    }
}

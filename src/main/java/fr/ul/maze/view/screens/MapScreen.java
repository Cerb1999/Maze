package fr.ul.maze.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import compiler.Options;
import fr.ul.maze.controller.Box2DTaskQueue;
import fr.ul.maze.controller.MobMoveController;
import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.controller.contact.MasterContactController;
import fr.ul.maze.controller.keyboard.HeroAttackController;
import fr.ul.maze.controller.keyboard.HeroMoveController;
import fr.ul.maze.controller.keyboard.PauseController;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.assets.MusicAssetManager;
import fr.ul.maze.model.entities.Mob;
import fr.ul.maze.model.entities.items.Item;
import fr.ul.maze.model.map.Square;
import fr.ul.maze.model.maze.Maze;
import fr.ul.maze.view.actors.*;
import fr.ul.maze.view.actors.animated.AnimatedActor;
import fr.ul.maze.view.actors.debug.MazePathsDebug;
import fr.ul.maze.view.map.RigidSquare;
import utils.RefCell;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class MapScreen implements Screen {
    private final AtomicReference<MasterState> state;
    private final Camera camera;
    private final Stage stage;
    private final MasterScreen master;

    private InputMultiplexer mux;

    private LinkedList<RigidSquare> squares;
    private RefCell<HeroActor> hero;

    private RefCell<ItemActor> ladder;

    private RefCell<List<AnimatedActor>> mobs;
    private RefCell<List<ItemActor>> lifeups;
    private RefCell<List<ItemActor>> noattacks;

    private HeroMoveController heroMoveController;
    private HeroAttackController heroAttackController;
    private MasterContactController masterContactListener;
    private RefCell<List<MobMoveController>> mobMoveControllers;

    private Box2DDebugRenderer debugRenderer;

    private World world;

    private Label time;
    private Label hplab;
    private Label scorelab;


    private final PauseController pauseController;

    public MapScreen(final AtomicReference<MasterState> state, final PauseController pauseController, MasterScreen masterScreen) {
        this.state = state;
        this.stage = new Stage();
        this.master = masterScreen;

        this.camera = new OrthographicCamera(RigidSquare.WIDTH * Maze.WIDTH, RigidSquare.HEIGHT * Maze.HEIGHT + master.getFontSMALL().getLineHeight() + 30);

        this.stage.getViewport().setCamera(this.camera);
        this.camera.position.set(this.camera.viewportWidth / 2, this.camera.viewportHeight / 2, 0);
        this.stage.setViewport(new FitViewport(this.camera.viewportWidth, this.camera.viewportHeight, this.camera));

        this.pauseController = pauseController;

        this.constructLevel();
    }

    private void constructScreen() {

        //Variables : Time, Hp and score
        Label.LabelStyle lTime = new Label.LabelStyle();
        lTime.font = master.getFontSMALL();

        Label.LabelStyle lScore = new Label.LabelStyle();
        lScore.font = master.getFontSMALL();

        Label.LabelStyle lHp = new Label.LabelStyle();
        lHp.font = master.getFontSMALL();


        int score = this.state.get().getCurrentLevelNumber();
        int hp = this.state.get().getHero().get().getHp();

        Label.LabelStyle lTimeLabel = new Label.LabelStyle();
        lTimeLabel.font = master.getFontSMALL();

        time = new Label("Time", lTime);
        time.setWrap(true);

        scorelab = new Label("Score " + score + "",lScore);
        scorelab.setWrap(true);

        hplab = new Label("Hp "+hp+"",lHp);




        Table table = new Table();
        table.setFillParent(true);
        table.align(Align.right);
        table.top();
        table.add(time).padRight(stage.getCamera().viewportWidth/5);
        table.add(scorelab).padRight(stage.getCamera().viewportWidth/5);
        table.add(hplab).padRight(stage.getCamera().viewportWidth/10);
        table.row();

        stage.addActor(table);
        mux.addProcessor(this.pauseController);

        Gdx.input.setInputProcessor(mux);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.mux);
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
        this.time.setText("Time : " + TimerSingleton.getInstance().getTime());
        this.hplab.setText("Hp " + this.state.get().getHero().get().getHp());
        this.scorelab.setText("Score " + this.state.get().getCurrentLevelNumber());
        this.stage.draw();

        if (Options.DEBUG)
            this.debugRenderer.render(this.world, this.camera.combined);

        this.world.step(1f / 60f, 6, 2);

        Deque<Runnable> tasks = Box2DTaskQueue.getQueue();
        while (!tasks.isEmpty()) {
            tasks.pop().run();
        }
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
        MusicAssetManager.getInstance().stopMusic();

    }

    @Override
    public void dispose() {
        //this.stage.dispose();
    }

    public void constructLevel() {
        this.stage.clear();
        this.world = this.state.get().getWorld();

        this.hero = new RefCell<>();

        this.ladder = new RefCell<>();

        this.mobs = new RefCell<>();
        this.mobMoveControllers = new RefCell<>();

        this.lifeups = new RefCell<>();
        this.noattacks = new RefCell<>();

        this.squares = new LinkedList<>();
        this.state.updateAndGet(st -> {
            st.getLevel().get().forEachCell( sq -> this.squares.push(new RigidSquare(st.getWorld(), sq, this.state.get().getCurrentLevelNumber())));
            this.squares.forEach(this.stage::addActor);

            this.stage.addActor(new MazePathsDebug(st.getLevel()));

            this.hero.inner = new HeroActor(st.getWorld(), st.getHero());
            this.stage.addActor(this.hero.inner);

            this.ladder.inner = new ItemActor(st.getWorld(), st.getLadder());

            this.stage.addActor(this.ladder.inner);

            this.mobMoveControllers.inner = new LinkedList<>();
            this.mobs.inner = new LinkedList<>();
            for (AtomicReference<Mob> mob : st.getMobs()) {
                switch (mob.get().getMobType()){
                    case "Zombie":
                        this.mobs.inner.add(new ZombieActor(st.getWorld(), mob));
                        break;
                    case "Rat":
                        this.mobs.inner.add(new RatActor(st.getWorld(), mob));
                        break;
                    case "Bat":
                        this.mobs.inner.add(new BatActor(st.getWorld(), mob));
                        break;
                }
                this.mobMoveControllers.inner.add(new MobMoveController(this.state, mob));
            }
            this.mobs.inner.forEach(this.stage::addActor);

            this.lifeups.inner = new LinkedList<>();
            for (AtomicReference<Item> lifeup : st.getLifeups()) {
                this.lifeups.inner.add(new ItemActor(st.getWorld(), lifeup));
            }
            this.lifeups.inner.forEach(this.stage::addActor);

            this.noattacks.inner = new LinkedList<>();
            for (AtomicReference<Item> lifeup : st.getNoattacks()) {
                this.noattacks.inner.add(new ItemActor(st.getWorld(), lifeup));
            }
            this.noattacks.inner.forEach(this.stage::addActor);

            return st;
        });

        this.mux = new InputMultiplexer();
        this.mux.addProcessor(stage);

        this.heroMoveController = new HeroMoveController(this.state);
        this.mux.addProcessor(this.heroMoveController);

        this.heroAttackController = new HeroAttackController(this.state);
        this.mux.addProcessor(this.heroAttackController);

        this.mux.addProcessor(this.pauseController);

        this.debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);

        this.masterContactListener = new MasterContactController(this.state, this, master);

        this.world.setContactListener(this.masterContactListener);

        this.hero.inner.toFront();
        this.mobs.inner.forEach(Actor::toFront);
        this.lifeups.inner.forEach(Actor::toFront);
        this.noattacks.inner.forEach(Actor::toFront);
        this.squares.forEach(rigidSquare -> {if(rigidSquare.getSquareType()== Square.Type.WALL)rigidSquare.toFront();});

        this.constructScreen();
    }
}

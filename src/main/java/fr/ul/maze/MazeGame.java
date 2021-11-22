package fr.ul.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.ul.maze.model.entities.Ladder;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.MazeAssetManager;
import fr.ul.maze.model.entities.Hero;
import fr.ul.maze.model.entities.Mob;
import fr.ul.maze.model.generator.RandomMazeGenerator;
import fr.ul.maze.model.maze.Maze;
import fr.ul.maze.view.MasterScreen;
import utils.functional.Tuple3;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class MazeGame extends Game {
    private MasterScreen screen;
    private final AtomicReference<MasterState> state;
    private final AtomicReference<Maze> level;
    private final AtomicReference<Hero> hero;
    private final AtomicReference<Ladder> ladder;
    private final List<AtomicReference<Mob>> mobs;

    private Stage stage;

    public MazeGame() {
        World world = new World(new Vector2(0, 0), true);

        Tuple3<Maze, Vector2, Vector2> randomMaze = new RandomMazeGenerator().generateMaze(world);
        this.level = new AtomicReference<>(randomMaze.fst);
        this.hero = new AtomicReference<>(new Hero(world, randomMaze.snd));
        this.ladder = new AtomicReference<>(new Ladder(world, randomMaze.thd));
        this.mobs = new LinkedList<>();

        this.state = new AtomicReference<>(new MasterState(world, this.level, this.hero, this.ladder, this.mobs));

        final int levelNumber = this.state.get().getCurrentLevelNumber();
        for (int i = 0; i < levelNumber * 1.5; ++i) {
            this.mobs.add(new AtomicReference<>(new Mob(world, level.get().randomPosition())));
        }
    }

    @Override
    public void create() {
        Box2D.init();
        MazeAssetManager.getInstance();

        this.stage = new Stage(new ScreenViewport());
        this.screen = new MasterScreen(this.stage, this.state);

        this.screen.switchScreen(this.screen.MAIN_SCREEN.get());
        this.setScreen(this.screen);
    }

    @Override
    public void dispose() {
        this.screen.dispose();
        MazeAssetManager.getInstance().dispose();
    }
}

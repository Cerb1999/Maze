package fr.ul.maze.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fr.ul.maze.model.entities.Hero;
import fr.ul.maze.model.entities.Ladder;
import fr.ul.maze.model.entities.Mob;
import fr.ul.maze.model.generator.RandomMazeGenerator;
import fr.ul.maze.model.maze.Maze;
import org.lwjgl.Sys;
import utils.functional.Tuple3;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class MasterState {
    private static int levelNumberFactory = 0;

    private AtomicReference<Maze> level;
    private int currentLevelNumber;
    private AtomicReference<Hero> hero;
    private AtomicReference<Ladder> ladder;
    private List<AtomicReference<Mob>> mobs;

    private World world;

    public MasterState(){
        this.world = new World(new Vector2(0, 0), true);

        Tuple3<Maze, Vector2, Vector2> randomMaze = new RandomMazeGenerator().generateMaze(world);
        this.level = new AtomicReference<>(randomMaze.fst);
        this.currentLevelNumber = ++levelNumberFactory;
        this.hero = new AtomicReference<>(new Hero(world, randomMaze.snd));
        this.ladder = new AtomicReference<>(new Ladder(world, randomMaze.thd));
        this.mobs = new LinkedList<>();
        for (int i = 0; i < currentLevelNumber * 1.5; ++i) {
            this.mobs.add(new AtomicReference<>(new Mob(world, level.get().randomPosition())));
        }
    }

    public World getWorld() {
        return this.world;
    }

    public AtomicReference<Maze> getLevel() {
        return this.level;
    }

    public AtomicReference<Hero> getHero() {
        return hero;
    }

    public AtomicReference<Ladder> getLadder() {
        return ladder;
    }

    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    public List<AtomicReference<Mob>> getMobs() {
        return mobs;
    }

    public void nextLevel(){
        this.world =new World(new Vector2(0, 0), true);
        Tuple3<Maze, Vector2, Vector2> randomMaze = new RandomMazeGenerator().generateMaze(world);
        this.level = new AtomicReference<>(randomMaze.fst);
        this.currentLevelNumber = ++levelNumberFactory;
        int lasthp = this.hero.get().getHp();
        this.hero = new AtomicReference<>(new Hero(world, randomMaze.snd));
        this.hero.get().setHp(lasthp);
        this.ladder = new AtomicReference<>(new Ladder(world, randomMaze.thd));
        this.mobs = new LinkedList<>();
        for (int i = 0; i < currentLevelNumber * 1.5; ++i) {
            this.mobs.add(new AtomicReference<>(new Mob(world, level.get().randomPosition())));
        }
        System.out.println(currentLevelNumber);
    }
}

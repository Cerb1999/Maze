package fr.ul.maze.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fr.ul.maze.model.entities.*;
import fr.ul.maze.model.generator.RandomMazeGenerator;
import fr.ul.maze.model.maze.Maze;
import utils.functional.Tuple3;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public final class MasterState {
    private static int levelNumberFactory = 0;
    private static final float DIFFICULTY_MODIFIER = 2.5f;

    private final AtomicReference<Maze> level;
    private int currentLevelNumber;
    private final AtomicReference<Hero> hero;
    private final AtomicReference<Item> ladder;
    private List<AtomicReference<Mob>> mobs;

    private List<AtomicReference<Item>> lifeups;
    private List<AtomicReference<Item>> noattacks;

    private World world;


    public MasterState(){
        this.world = new World(new Vector2(0, 0), true);

        Tuple3<Maze, Vector2, Vector2> randomMaze = new RandomMazeGenerator().generateMaze(world);
        this.level = new AtomicReference<>(randomMaze.fst);
        this.currentLevelNumber = ++levelNumberFactory;
        this.hero = new AtomicReference<>(new Hero(world, randomMaze.snd));
        this.mobs = new LinkedList<>();
        for (int i = 0; i < currentLevelNumber * DIFFICULTY_MODIFIER; ++i) {
            this.mobs.add(new AtomicReference<>(new Random().nextFloat() <=0.33 ? new Zombie(world, level.get().randomPosition()) : (new Random().nextFloat() <=0.5 ? new Bat(world, level.get().randomPosition()) : new Rat(world, level.get().randomPosition()))));
        }

        this.ladder = new AtomicReference<>(new Item(world, randomMaze.thd, ItemType.LADDER));
        this.lifeups = new LinkedList<>();
        for (int i = 0; i < currentLevelNumber; ++i) {
            this.lifeups.add(new AtomicReference<>(new Item(world, level.get().randomPosition(), ItemType.LIFEUP)));
        }
        this.noattacks = new LinkedList<>();
        for (int i = 0; i < currentLevelNumber * DIFFICULTY_MODIFIER; ++i) {
            this.noattacks.add(new AtomicReference<>(new Item(world, level.get().randomPosition(), ItemType.NOATTACK)));
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

    public AtomicReference<Item> getLadder() {
        return ladder;
    }

    public List<AtomicReference<Item>> getNoattacks() {
        return noattacks;
    }

    public List<AtomicReference<Item>> getLifeups() {
        return lifeups;
    }

    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    public List<AtomicReference<Mob>> getMobs() {
        return mobs;
    }

    /**
     * Regenerate a new level but keep hero stats
     */
    public void nextLevel(){
        this.world =new World(new Vector2(0, 0), true);
        Tuple3<Maze, Vector2, Vector2> randomMaze = new RandomMazeGenerator().generateMaze(world);
        this.level.set(randomMaze.fst);
        this.currentLevelNumber = ++levelNumberFactory;
        int lasthp = this.hero.get().getHp();
        this.hero.set(new Hero(world, randomMaze.snd));
        this.hero.get().setHp(lasthp);
        this.mobs = new LinkedList<>();
        for (int i = 0; i < currentLevelNumber * DIFFICULTY_MODIFIER; ++i) {
            this.mobs.add(new AtomicReference<>(new Random().nextFloat() <=0.33 ? new Zombie(world, level.get().randomPosition()) : (new Random().nextFloat() <=0.5 ? new Bat(world, level.get().randomPosition()) : new Rat(world, level.get().randomPosition()))));
        }

        this.ladder.set(new Item(world, randomMaze.thd, ItemType.LADDER));
        this.lifeups = new LinkedList<>();
        for (int i = 0; i < currentLevelNumber; ++i) {
            this.lifeups.add(new AtomicReference<>(new Item(world, level.get().randomPosition(), ItemType.LIFEUP)));
        }
        this.noattacks = new LinkedList<>();
        for (int i = 0; i < currentLevelNumber; ++i) {
            this.noattacks.add(new AtomicReference<>(new Item(world, level.get().randomPosition(), ItemType.NOATTACK)));
        }
    }

    public void reset() {
        levelNumberFactory = 0;
        hero.get().reset();
        Mob.resetScore();
        nextLevel();
    }
}

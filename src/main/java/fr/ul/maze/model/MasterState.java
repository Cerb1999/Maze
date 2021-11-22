package fr.ul.maze.model;

import com.badlogic.gdx.physics.box2d.World;
import fr.ul.maze.model.entities.Hero;
import fr.ul.maze.model.entities.Ladder;
import fr.ul.maze.model.entities.Mob;
import fr.ul.maze.model.maze.Maze;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class MasterState {
    private static int levelNumberFactory = 0;

    private final AtomicReference<Maze> level;
    private final int currentLevelNumber;
    private final AtomicReference<Hero> hero;
    private final AtomicReference<Ladder> ladder;
    private final List<AtomicReference<Mob>> mobs;

    private final World world;

    public MasterState(final World world, final AtomicReference<Maze> level, final AtomicReference<Hero> hero, final AtomicReference<Ladder> ladder, final List<AtomicReference<Mob>> mobs) {
        this.level = level;
        this.currentLevelNumber = ++levelNumberFactory;
        this.hero = hero;
        this.ladder = ladder;
        this.mobs = mobs;

        this.world = world; // new World(new Vector2(0, 0), true);
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
}

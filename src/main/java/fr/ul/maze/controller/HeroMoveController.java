package fr.ul.maze.controller;

import fr.ul.maze.model.Level;
import fr.ul.maze.model.Position;

import java.util.concurrent.atomic.AtomicReference;

/**
 * A basic controller to control the movements of the hero.
 */
public class HeroMoveController {
    private final AtomicReference<Level> modelReference;

    public HeroMoveController(final AtomicReference<Level> modelReference) {
        this.modelReference = modelReference;
    }

    /**
     * Tries to move the hero in the given direction.
     * If any non-practicable cell is encountered in this direction, no operations are performed.
     *
     * @param dir the direction to move in
     */
    public void moveHero(final Direction dir) {
        this.modelReference.updateAndGet(level -> {
            Position heroPosition = level.getHeroPosition();
            Position newPosition = dir.from(heroPosition);

            if (level.canMoveTo(newPosition)) {
                level.updateHeroPosition(newPosition);
            }

            return level;
        });
    }
}

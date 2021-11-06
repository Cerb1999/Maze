package fr.ul.maze.controller;

import fr.ul.maze.model.Level;
import fr.ul.maze.model.Position;

import java.util.concurrent.atomic.AtomicReference;

public class HeroMoveController {
    private final AtomicReference<Level> modelReference;

    public HeroMoveController(final AtomicReference<Level> modelReference) {
        this.modelReference = modelReference;
    }

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

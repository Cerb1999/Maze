package fr.ul.maze.controller;

import fr.ul.maze.model.Position;

public enum Direction {
    LEFT, RIGHT, UP, DOWN;

    public Position from(Position pos) {
        switch (this) {
            case UP: return new Position(pos.x(), pos.y() - 1);
            case DOWN: return new Position(pos.x(), pos.y() + 1);
            case LEFT: return new Position(pos.x() - 1, pos.y());
            case RIGHT: return new Position(pos.x() + 1, pos.y());
        }

        assert false : "all direction variants should be handled";
        return null;
    }
}

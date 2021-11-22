package fr.ul.maze.model;

import com.badlogic.gdx.math.Vector2;
import exceptions.Exn;

import java.util.Random;

public enum Direction {
    UP, UPRIGHT, UPLEFT, DOWN, DOWNRIGHT, DOWNLEFT, LEFT, RIGHT, IDLE;

    public final Vector2 extend(Vector2 pos) {
        switch (this) {
            case UP: return new Vector2(pos.x, pos.y + 1);
            case DOWN: return new Vector2(pos.x, pos.y - 1);
            case LEFT: return new Vector2(pos.x - 1, pos.y);
            case RIGHT: return new Vector2(pos.x + 1, pos.y);
            default: return pos;
        }
    }

    public static Direction random() {
        return Direction.random(new Random());
    }
    public static Direction random(Random rnd) {
        final int choice = rnd.nextInt(3);
        switch (choice) {
            case 0: return Direction.UP;
            case 1: return Direction.DOWN;
            case 2: return Direction.LEFT;
            case 3: return Direction.RIGHT;
            default: return Exn.undefined();
        }
    }
}
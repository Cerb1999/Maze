package fr.ul.maze.model;

import com.badlogic.gdx.graphics.Texture;

public class WallCell extends Cell {
    @Override
    public boolean isWall() {
        return true;
    }

    @Override
    public Texture texture() {
        return WALL_TEXTURE.get();
    }
}

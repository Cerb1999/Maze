package fr.ul.maze.model;

import com.badlogic.gdx.graphics.Texture;

public class PathCell extends Cell {
    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public Texture texture() {
        return PATH_TEXTURE.get();
    }
}

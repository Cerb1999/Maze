package fr.ul.maze.model;

import com.badlogic.gdx.graphics.Texture;

public class LadderCell extends Cell {
    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public boolean isLadder() {
        return true;
    }

    @Override
    public Texture texture() {
        return LADDER_TEXTURE.get();
    }
}

package fr.ul.maze.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.maze.MazeGame;

public class MobCell extends Cell {
    Mob mob;

    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public boolean isLadder() {
        return false;
    }

    @Override
    public boolean isMob() {
        return true;
    }

    @Override
    public void createCell(MazeGame mazeGame, World world, float xPosition, float yPosition) {
        //Create special cell for monster's spawn ?

    }

}

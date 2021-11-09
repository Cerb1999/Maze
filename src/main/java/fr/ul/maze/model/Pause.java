package fr.ul.maze.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import fr.ul.maze.MazeGame;

public class Pause extends Actor {
    private final MazeGame mazeGame;

    private Texture tpause;
    private TextureRegion trpause;
    private TextureRegionDrawable trdpause;
    private ImageButton button;

    public Pause(MazeGame mazeGame){
        this.mazeGame = mazeGame;

        tpause = new Texture(Gdx.files.internal("Play.png"));
        trpause = new TextureRegion(tpause);
        trdpause = new TextureRegionDrawable(trpause);
        button = new ImageButton(trdpause); //Set the button up
        button.setWidth(Gdx.graphics.getWidth());
        button.setHeight(Gdx.graphics.getHeight());
    }

    public ImageButton getButton() {
        return button;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
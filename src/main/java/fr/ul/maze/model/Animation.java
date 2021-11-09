package fr.ul.maze.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import fr.ul.maze.MazeGame;

/**
 * Custom class for texture animation
 */
public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;
    private boolean loop;
    private boolean isFinished;

    public Animation(MazeGame mazeGame, String nomAnimation, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        for(int i = 1; i <= frameCount; i++){
            frames.add((mazeGame.getManagedTexture(nomAnimation+i)).getRegion());
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
        this.loop = true;
    }

    public Animation(MazeGame mazeGame, String nomAnimation, int frameCount, float cycleTime, boolean loop){
        frames = new Array<TextureRegion>();
        for(int i = 1; i <= frameCount; i++){
            frames.add((mazeGame.getManagedTexture(nomAnimation+i)).getRegion());
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
        this.loop = loop;
    }


    /**
     * Update frame of the animation since the last update
     * @param dt Time since the last update
     */
    public void update(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frameCount){
            frame = 0;
            if(!loop) isFinished = true;
        }

    }

    /**
     * Get the current frame of the animation
     */
    public TextureRegion getFrame(){
        return frames.get(frame);
    }

    /**
     * Only used for restarting non-loopable animation without creating another object
     */
    public void setFinishedState(boolean state){
        isFinished = state;
    }

    /**
     * Check if the animation has finished once
     * @return boolean
     */
    public boolean isFinished(){
        return isFinished;
    }
}

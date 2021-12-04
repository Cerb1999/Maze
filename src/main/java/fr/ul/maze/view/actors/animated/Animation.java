package fr.ul.maze.view.actors.animated;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import fr.ul.maze.model.assets.MazeAssetManager;

/**
 * Custom class for texture animation
 */
public class Animation {
    private final String nomAnimation;
    private final Array<TextureRegion> frames;
    private final float maxFrameTime;
    private final int frameCount;
    private final boolean loop;
    private float currentFrameTime;
    private int frame;
    private boolean isFinished;

    public Animation(String nomAnimation, int frameCount, float cycleTime) {
        this(nomAnimation, frameCount, cycleTime, true);
    }

    public Animation(String nomAnimation, int frameCount, float cycleTime, boolean loop) {
        this.nomAnimation = nomAnimation;
        this.frames = new Array<>();
        for (int i = 1; i <= frameCount; i++) {
            this.frames.add((MazeAssetManager.getInstance().getManagedTexture(nomAnimation + i)).getRegion());
        }
        this.frameCount = frameCount;
        this.maxFrameTime = cycleTime / frameCount;
        this.currentFrameTime = 0;
        this.frame = 0;
        this.loop = loop;
    }


    /**
     * Update frame of the animation since the last update
     *
     * @param dt Time since the last update
     */
    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }
        if (frame >= frameCount) {
            frame = 0;
            if (!loop) isFinished = true;
        }

    }

    /**
     * Get the current frame of the animation
     */
    public TextureRegion getFrame() {
        return frames.get(frame);
    }

    /**
     * Only used for restarting non-loopable animation without creating another object
     */
    public void setFinishedState(boolean state) {
        isFinished = state;
    }

    /**
     * Check if the animation has finished once
     *
     * @return boolean
     */
    public boolean isFinished() {
        return isFinished;
    }

    public String getNomAnimation() {
        return nomAnimation;
    }

    /**
     * Restart the whole animation from the beginning.
     */
    public void restart() {
        this.frame = 0;
        this.currentFrameTime = 0;
        this.isFinished = false;
    }
}

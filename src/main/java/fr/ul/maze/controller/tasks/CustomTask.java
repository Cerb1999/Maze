package fr.ul.maze.controller.tasks;

import com.badlogic.gdx.utils.Timer;
import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.model.MasterState;

import java.util.concurrent.atomic.AtomicReference;

public abstract class CustomTask extends Timer.Task{
    private final Runnable onTick, onEnd;
    protected Timer timer;
    protected final AtomicReference<MasterState> state;
    private final long BASE_TIMER;
    protected long time;

    /**
     * create custom task
     * @param state masterstate
     * @param BASE_TIMER base timer
     * @param onTick runnable onTick
     * @param onEnd runnable onEnd
     */
    public CustomTask(final AtomicReference<MasterState> state, final long BASE_TIMER, final Runnable onTick, final Runnable onEnd) {
        this.state = state;
        this.time = BASE_TIMER;
        this.BASE_TIMER = BASE_TIMER;
        this.onTick = onTick;
        this.onEnd = onEnd;
    }

    /**
     * stops the timer
     */
    public void stop() {
        timer.stop();
    }

    /**
     * starts the timer
     */
    public void start() {
        timer.start();
    }

    /**
     * clears the timer
     */
    public void clear() {
        timer.clear();
    }

    /**
     * creates new timer instance and schedules it
     */
    public void launch() {
        this.timer = new Timer();
        timer.scheduleTask(this, 1, 1);
    }

    /**
     * scheduled task run. calls onTick runnable per call and onEnd runnable on time reaching 0.
     * clears timer and removes task from map
     */
    public void run() {
        this.time--;
        this.onTick.run();
        if(this.time == 0) {
            this.onEnd.run();
            clear();
            TimerSingleton.getInstance().removeTask(this);
        }
    }

    /**
     * refreshes the timer.
     */
    public void refresh() {
        this.time = BASE_TIMER;
    };

    public String getTime() {
        return String.valueOf(time);
    }
}

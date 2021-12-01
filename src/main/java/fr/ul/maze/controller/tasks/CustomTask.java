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

    public CustomTask(final AtomicReference<MasterState> state, final long BASE_TIMER, final Runnable onTick, final Runnable onEnd) {
        this.state = state;
        this.time = BASE_TIMER;
        this.BASE_TIMER = BASE_TIMER;
        this.onTick = onTick;
        this.onEnd = onEnd;
    }

    public void stop() {
        timer.stop();
    }
    public void start() {
        timer.start();
    }
    public void clear() {
        timer.clear();
    }
    public void launch() {
        this.timer = new Timer();
        timer.scheduleTask(this, 1, 1);
    }
    public void run() {
        this.time--;
        this.onTick.run();
        if(this.time == 0) {
            this.onEnd.run();
            clear();
            TimerSingleton.getInstance().removeTask(this);
        }
    }
    public String getTime() {
        return String.valueOf(time);
    }
    public void refresh() {
        this.time = BASE_TIMER;
    };
}

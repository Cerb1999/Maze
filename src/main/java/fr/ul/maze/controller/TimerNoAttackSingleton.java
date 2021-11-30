package fr.ul.maze.controller;

import com.badlogic.gdx.utils.Timer;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.entities.Hero;
import fr.ul.maze.view.screens.MasterScreen;

import java.util.concurrent.atomic.AtomicReference;

public class TimerNoAttackSingleton extends Timer.Task {
    private static final int BASE_NOATTACK_TIME = 5;
    private static int time = BASE_NOATTACK_TIME;
    private static TimerNoAttackSingleton singleton = null;
    private final AtomicReference<MasterState> state;
    private static boolean running = false;

    private TimerNoAttackSingleton(AtomicReference<MasterState> state) {
        this.state = state;
    }

    public synchronized static TimerNoAttackSingleton init(AtomicReference<MasterState> state) {
        if (singleton != null)
        {
            throw new AssertionError("You already initialized me");
        }

        running = true;
        singleton = new TimerNoAttackSingleton(state);
        return singleton;
    }

    public static void refresh() {
        time = BASE_NOATTACK_TIME;
    }


    public void run() {
        time--;
        if(time == 0) {
            state.get().getHero().get().recover();
            clear();
        }
    }

    public static void start() {
        running = true;
        Timer.instance().start();
    }

    public static void stop() {
        running = false;
        Timer.instance().stop();
    }
    public static void clear() {
        running = false;
        Timer.instance().clear();
    }

    public static void startIfNeeded() {
        if(running) start();
    }

    public static void stopIfNeeded() {
        if(!running) stop();
    }

    public static void disableIfNeeded() {
        if(running) clear();
    }

    public static void launch(AtomicReference<MasterState> state) {
        if(!running) {
            TimerNoAttackSingleton singleton = TimerNoAttackSingleton.init(state);
            Timer.schedule(singleton, 1f, 1f);
        } else refresh();
    }
}
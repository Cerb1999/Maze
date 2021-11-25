package fr.ul.maze.controller;

import com.badlogic.gdx.utils.Timer;
import fr.ul.maze.view.screens.MasterScreen;

public class TimerSingleton extends Timer.Task {
    private static int BASE_TIMER = 100;
    private static int time = BASE_TIMER;
    private static TimerSingleton singleton = null;
    private final MasterScreen masterScreen;

    private TimerSingleton(MasterScreen masterScreen) {
        this.masterScreen = masterScreen;
    }

    public synchronized static TimerSingleton init(MasterScreen masterScreen) {
        if (singleton != null)
        {
            throw new AssertionError("You already initialized me");
        }

        singleton = new TimerSingleton(masterScreen);
        return singleton;
    }

    public static void refresh() {
        time = BASE_TIMER;
    }

    public void run() {
        time--;
        if(time == 0) {
            masterScreen.switchScreen(masterScreen.GAME_OVER_SCREEN.get());
            clear();
        }
    }

    public static void start() {
        Timer.instance().start();
    }
    public static void stop() {
        Timer.instance().stop();
    }
    public static void clear() {
        Timer.instance().clear();
    }
    /**
     * launch timer every 1 second
     */
    public static void launch(MasterScreen masterScreen) {
        TimerSingleton singleton = TimerSingleton.init(masterScreen);
        Timer.schedule(singleton, 1f, 1f);
    }

    public static int getTime() {
        return time;
    }
}
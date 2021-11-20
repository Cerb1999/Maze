package fr.ul.maze.model;

import com.badlogic.gdx.utils.Timer;

public class TimerTask extends Timer.Task {
    private static final int BASE_TIMER = 100;
    private int time;
    private GameState gameState;

    public TimerTask(GameState gameState) {
        time = BASE_TIMER;
        this.gameState = gameState;
    }

    @Override
    public void run() {
        decrTime();
        if(isCountDownOver()) {
            gameState.changeState(State.GAME_OVER);
            gameState.getMazeGame().switchScreen();
            Timer.instance().stop();
        }
    }

    public void decrTime() {
        if(time > 0) time--;
    }
    public void refreshCountdown() {
        time = BASE_TIMER;
    }
    public int getTime() {
        return time;
    }
    public boolean isCountDownOver() {
        return time == 0;
    }

    public void start() {
        Timer.instance().start();
    }

    public void stop() {
        Timer.instance().stop();
    }

    public void launch() {
        Timer.schedule(this, 1f, 1f);
    }
}

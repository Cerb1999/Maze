package fr.ul.maze.controller.tasks;

import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.assets.SoundAssetManager;
import fr.ul.maze.view.screens.MasterScreen;

import java.util.concurrent.atomic.AtomicReference;

public class GameTimerTask extends CustomTask {
    private static final int BASE_GAME_TIMER = 100;

    public GameTimerTask(AtomicReference<MasterState> state, MasterScreen masterScreen) {
        super(state, BASE_GAME_TIMER, () -> {}, () -> {
            TimerSingleton.getInstance().clearTasks();
            masterScreen.switchScreen(masterScreen.GAME_OVER_SCREEN.get());
            SoundAssetManager.getInstance().stopFootstep();
        });
    }
}

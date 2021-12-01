package fr.ul.maze.controller.contact;

import com.badlogic.gdx.utils.Timer;
import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.controller.tasks.GameTimerTask;
import fr.ul.maze.controller.tasks.TaskType;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.assets.SoundAssetManager;
import fr.ul.maze.view.screens.MapScreen;
import fr.ul.maze.view.screens.MasterScreen;

import java.util.concurrent.atomic.AtomicReference;

public final class EndLevelController {
    public void nextLevel(AtomicReference<MasterState> state, MapScreen mapScreen, MasterScreen master) {
        SoundAssetManager.getInstance().stopFootstep();
        TimerSingleton instance = TimerSingleton.getInstance();
        instance.clearTasks();
        master.switchScreen(master.LEVEL_TRANSITION_SCREEN.get());
        Timer timer = new Timer();
        Timer.Task task = timer.scheduleTask(new Timer.Task() {
                @Override
                public void run () {
                    instance.addTask(new GameTimerTask(state, master), TaskType.GAME);
                    state.get().nextLevel();
                    mapScreen.constructLevel();
                    master.switchScreen(master.MAIN_SCREEN.get());
                }
            }, 2);
        }
}


package fr.ul.maze.controller.contact;

import com.badlogic.gdx.utils.Timer;
import exceptions.Exn;
import fr.ul.maze.controller.TimerNoAttackSingleton;
import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.view.screens.MapScreen;
import fr.ul.maze.view.screens.MasterScreen;
import org.lwjgl.Sys;

import java.util.concurrent.atomic.AtomicReference;

public final class EndLevelController {
    public void nextLevel(AtomicReference<MasterState> state, MapScreen mapScreen, MasterScreen master) {
        TimerSingleton.stop();
        master.switchScreen(master.LEVEL_TRANSITION_SCREEN.get());
        Timer timer = new Timer();
        Timer.Task task = timer.scheduleTask(new Timer.Task() {
                @Override
                public void run () {
                    TimerSingleton.refresh();
                    master.switchScreen(master.MAIN_SCREEN.get());
                    state.get().nextLevel();
                    mapScreen.constructLevel();
                    TimerSingleton.start();
                }
            }, 2);
        }
}


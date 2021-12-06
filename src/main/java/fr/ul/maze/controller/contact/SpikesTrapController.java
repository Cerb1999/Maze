package fr.ul.maze.controller.contact;


import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.controller.tasks.NoAttackTimerTask;
import fr.ul.maze.controller.tasks.SpikesUpTimerTask;
import fr.ul.maze.controller.tasks.TaskType;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.entities.Hero;
import fr.ul.maze.view.screens.MapScreen;
import fr.ul.maze.view.screens.MasterScreen;

import java.util.concurrent.atomic.AtomicReference;

public class SpikesTrapController {

    public SpikesTrapController(AtomicReference<MasterState> state) {

    }

    public void spikeUp(AtomicReference<MasterState> state, MapScreen mapScreen) {
        TimerSingleton.getInstance().addTask(new SpikesUpTimerTask(state,mapScreen), TaskType.SPIKESUP);
    }
}


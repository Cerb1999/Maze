package fr.ul.maze.controller.contact;

import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.controller.tasks.SlowMobTimerTask;
import fr.ul.maze.controller.tasks.SpeedMobTimerTask;
import fr.ul.maze.controller.tasks.TaskType;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.entities.Mob;

import java.util.concurrent.atomic.AtomicReference;

public class SpeedMobController {

    public void speedMob(AtomicReference<MasterState> state) {

        int listlength = state.get().getMobs().size();
        int i;

        for(i = 0; i < listlength; i++){
            Mob mob = state.get().getMobs().get(i).get();
            mob.speedUp(1.3f);
            TimerSingleton.getInstance().addTask(new SpeedMobTimerTask(state), TaskType.SPEEDMOB);
        }

    }

}

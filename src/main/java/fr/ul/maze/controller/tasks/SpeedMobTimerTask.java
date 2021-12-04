package fr.ul.maze.controller.tasks;

import fr.ul.maze.model.MasterState;

import java.util.concurrent.atomic.AtomicReference;

public class SpeedMobTimerTask extends CustomTask {

    private static final int BASE_MOB_SPEED_TIME = 5;

    /**
     * create custom task
     */

    public SpeedMobTimerTask(AtomicReference<MasterState> state) {

        super(state, BASE_MOB_SPEED_TIME, () -> {} ,  () -> {
            for(int i=0; i < state.get().getMobs().size(); i++){
                state.get().getMobs().get(i).get().backToNormalSpeed();
            }

        });
    }
}

package fr.ul.maze.controller.tasks;

import fr.ul.maze.model.MasterState;

import java.util.concurrent.atomic.AtomicReference;

public class SlowMobTimerTask extends CustomTask{

    private static final int BASE_MOB_SLOW_TIME = 5;

    /**
     * create custom task
     */

    public SlowMobTimerTask(AtomicReference<MasterState> state) {

        super(state, BASE_MOB_SLOW_TIME, () -> {} ,  () -> {
            for(int i=0; i < state.get().getMobs().size(); i++){
                state.get().getMobs().get(i).get().speed();
            }

        });
    }
}

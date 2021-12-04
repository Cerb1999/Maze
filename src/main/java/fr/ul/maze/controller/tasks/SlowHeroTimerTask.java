package fr.ul.maze.controller.tasks;

import fr.ul.maze.model.MasterState;

import java.util.concurrent.atomic.AtomicReference;

public class SlowHeroTimerTask extends CustomTask {

    private static final int BASE_SLOW_TIME = 5;

    /**
     * create custom task
     */

    public SlowHeroTimerTask(AtomicReference<MasterState> state) {
        super(state, BASE_SLOW_TIME, () -> {} ,  () -> {
            state.get().getHero().get().speed();
        });
    }
}

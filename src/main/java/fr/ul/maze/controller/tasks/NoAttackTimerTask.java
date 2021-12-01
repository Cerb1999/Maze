package fr.ul.maze.controller.tasks;

import fr.ul.maze.model.MasterState;

import java.util.concurrent.atomic.AtomicReference;

public class NoAttackTimerTask extends CustomTask {
    private static final int BASE_NOATTACK_TIME = 5;

    public NoAttackTimerTask(AtomicReference<MasterState> state) {
        super(state, BASE_NOATTACK_TIME, () -> {}, () -> {
            state.get().getHero().get().recover();
        });
    }
}

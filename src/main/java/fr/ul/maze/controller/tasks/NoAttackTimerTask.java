package fr.ul.maze.controller.tasks;

import fr.ul.maze.model.MasterState;

import java.util.concurrent.atomic.AtomicReference;

public class NoAttackTimerTask extends CustomTask {
    private static final int BASE_NOATTACK_TIME = 5;

    /**
     * creates task for the game timer.
     * onTick is empty
     * onEnd called when time reaches 0 (NOATTACK debuff time) => hero recovers his ability to attack
     * @param state state passed to super
     */
    public NoAttackTimerTask(AtomicReference<MasterState> state) {
        super(state, BASE_NOATTACK_TIME, () -> {}, () -> {
            state.get().getHero().get().recover();
        });
    }
}

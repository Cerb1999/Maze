package fr.ul.maze.controller.contact;

import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.controller.tasks.NoAttackTimerTask;
import fr.ul.maze.controller.tasks.TaskType;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.entities.Hero;

import java.util.concurrent.atomic.AtomicReference;

public class NoAttackController {
    public void disableWeapon(AtomicReference<MasterState> state) {
        Hero hero = state.get().getHero().get();
        hero.silence();
        TimerSingleton.getInstance().addTask(new NoAttackTimerTask(state), TaskType.NOATTACK);
    }
}

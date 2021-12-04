package fr.ul.maze.controller.contact;

import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.controller.tasks.SlowHeroTimerTask;
import fr.ul.maze.controller.tasks.TaskType;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.entities.Hero;

import java.util.concurrent.atomic.AtomicReference;

public class SlowHeroController {

    public void slowHero(AtomicReference<MasterState> state) {
        Hero hero = state.get().getHero().get();
        hero.slow(2f);
        TimerSingleton.getInstance().addTask(new SlowHeroTimerTask(state), TaskType.SLOWHERO);
    }
}

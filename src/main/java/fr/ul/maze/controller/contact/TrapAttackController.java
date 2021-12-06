package fr.ul.maze.controller.contact;

import com.badlogic.gdx.utils.Timer;
import fr.ul.maze.controller.Box2DTaskQueue;
import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.model.Direction;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.entities.EntityActionState;
import fr.ul.maze.model.entities.Hero;
import fr.ul.maze.model.entities.Mob;
import fr.ul.maze.model.entities.traps.Trap;
import fr.ul.maze.view.screens.MasterScreen;

import java.util.concurrent.atomic.AtomicReference;

public class TrapAttackController {

    private final MasterScreen master;
    private final AtomicReference<MasterState> state;

    public TrapAttackController(MasterScreen masterScreen, AtomicReference<MasterState> state) {
        this.master = masterScreen;
        this.state = state;
    }

    public void attack(Trap trap, Hero hero) {

        hero.damage(1);

        if(hero.isDead()) {
            TimerSingleton instance = TimerSingleton.getInstance();
            instance.stopTasks();
            Timer timer = new Timer();
            Timer.Task task = timer.scheduleTask(new Timer.Task() {
                @Override
                public void run () {
                    instance.clearTasks();
                    master.switchScreen(master.GAME_OVER_SCREEN.get());
                }
            }, 2);
        } else {
            // put the hero at its starting position
            Box2DTaskQueue.getQueue().add(hero::respawn);
        }
    }
}

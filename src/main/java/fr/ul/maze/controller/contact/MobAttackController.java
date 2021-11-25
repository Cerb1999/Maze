package fr.ul.maze.controller.contact;

import com.badlogic.gdx.utils.Timer;
import exceptions.Exn;
import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.entities.Hero;
import fr.ul.maze.model.entities.Mob;
import fr.ul.maze.view.screens.MasterScreen;

import java.util.concurrent.atomic.AtomicReference;

public final class MobAttackController {
    private final MasterScreen master;

    public MobAttackController(MasterScreen masterScreen) {
        this.master = masterScreen;
    }

    public void attack(Mob monster, Hero hero) {
        hero.damage(1);
        if(hero.isDead()) {
            TimerSingleton.stop();
            Timer timer = new Timer();
            Timer.Task task = timer.scheduleTask(new Timer.Task() {
                @Override
                public void run () {
                    master.switchScreen(master.GAME_OVER_SCREEN.get());
                    TimerSingleton.clear();
                }
            }, 2);
        }
    }
}
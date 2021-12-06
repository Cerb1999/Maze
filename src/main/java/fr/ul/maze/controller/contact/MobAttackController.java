package fr.ul.maze.controller.contact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import fr.ul.maze.controller.Box2DTaskQueue;
import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.model.Direction;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.entities.EntityActionState;
import fr.ul.maze.model.entities.Hero;
import fr.ul.maze.model.entities.Mob;
import fr.ul.maze.view.screens.MasterScreen;

import java.util.concurrent.atomic.AtomicReference;

public final class MobAttackController {
    private final MasterScreen master;
    private final AtomicReference<MasterState> state;

    public MobAttackController(MasterScreen masterScreen, AtomicReference<MasterState> state) {
        this.master = masterScreen;
        this.state = state;
    }

    public void attack(Mob monster, Hero hero) {
        if (monster.getHp() <= 0)
            return; // cancel attack if the mob is dying/dead
        if (hero.getActionState() == EntityActionState.DAMAGED || hero.getActionState() == EntityActionState.DYING)
            return; // prevent monster from multi kills
        if (hero.isDead())
            return; // should never happen

        monster.setActionState(EntityActionState.ATTACK);
        monster.setMoveState(Direction.IDLE);

        hero.damage(1);

        Timer.Task todo;
        if (hero.isDead()) {
            TimerSingleton instance = TimerSingleton.getInstance();
            instance.stopTasks();
            todo = new Timer.Task() {
                @Override
                public void run() {
                    instance.clearTasks();
                    master.switchScreen(master.GAME_OVER_SCREEN.get());
                }
            };
        } else {
            todo = new Timer.Task() {
                @Override
                public void run() {
                    Box2DTaskQueue.getQueue().push(hero::respawn);
                }
            };
        }

        new Timer().scheduleTask(todo, 1.5f);
    }
}
package fr.ul.maze.controller.contact;

import com.badlogic.gdx.utils.Timer;
import fr.ul.maze.controller.TimerNoAttackSingleton;
import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.entities.Hero;

import java.util.concurrent.atomic.AtomicReference;

public class NoAttackController {
    public void disableWeapon(AtomicReference<MasterState> state) {
        Hero hero = state.get().getHero().get();
        hero.silence();
        TimerNoAttackSingleton.launch(state);
    }
}

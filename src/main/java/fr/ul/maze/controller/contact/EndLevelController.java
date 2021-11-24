package fr.ul.maze.controller.contact;

import exceptions.Exn;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.view.screens.MapScreen;
import org.lwjgl.Sys;

import java.util.concurrent.atomic.AtomicReference;

public final class EndLevelController {
    public void nextLevel(AtomicReference<MasterState> state, MapScreen mapScreen) {
        state.get().nextLevel();
        mapScreen.constructLevel();
    }
}

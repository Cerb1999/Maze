package fr.ul.maze.controller.tasks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fr.ul.maze.controller.TimerSingleton;
import fr.ul.maze.controller.contact.SpikesTrapController;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.assets.SoundAssetManager;
import fr.ul.maze.model.entities.traps.Trap;
import fr.ul.maze.model.entities.traps.TrapType;
import fr.ul.maze.view.screens.MapScreen;
import org.lwjgl.Sys;


import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SpikesUpTimerTask extends CustomTask{

    private static int tic;
    private static final int BASE_SPIKES_UP_TIMER_TASK = 100;

    public SpikesUpTimerTask(AtomicReference<MasterState> state, MapScreen mapScreen) {
        super(state, BASE_SPIKES_UP_TIMER_TASK, () -> {

            if(tic == 5){
                if(state.get().getDangerous() == 0) {
                    state.get().setDangerous(1);
                    for(int i = 0; i < mapScreen.getSpikes().inner.size(); i++){
                        mapScreen.getSpikes().inner.get(i).setVisible(true);
                    }
                    SoundAssetManager.getInstance().playSpikeSound();
                    tic = 0;
                }else{
                    state.get().setDangerous(0);
                    for(int i = 0; i < mapScreen.getSpikes().inner.size(); i++){
                        mapScreen.getSpikes().inner.get(i).setVisible(false);
                    }
                    SoundAssetManager.getInstance().playSpikeSound();
                    tic = 0;
                }
            }{
                tic = tic +1;
            }


        } ,  () -> {

        });
    }
}

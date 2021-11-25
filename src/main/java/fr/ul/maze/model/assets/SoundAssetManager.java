package fr.ul.maze.model.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;


public class SoundAssetManager {
    private final AssetManager assetManager;
    private static final String SOUND_PATH = "sound/";
    private static final String DRAW_SWORD_SOUND = "drawSword.ogg";
    private static final String FOOTSTEP_SOUND = "footstep.ogg";
    private static final String HEROHURT_SOUND = "heroHurt.flac";

    private static Sound swordSlashSound;
    private static Sound footstepSound;
    private static Sound heroHurtSound;

    private SoundAssetManager(){
        assetManager = new AssetManager();
        assetManager.load(SOUND_PATH + DRAW_SWORD_SOUND, Sound.class);
        assetManager.load(SOUND_PATH + FOOTSTEP_SOUND, Sound.class);
        assetManager.finishLoading();
        swordSlashSound = assetManager.get(SOUND_PATH + DRAW_SWORD_SOUND);
        footstepSound = assetManager.get(SOUND_PATH + FOOTSTEP_SOUND);
        heroHurtSound = assetManager.get(SOUND_PATH + HEROHURT_SOUND);
    }

    private static class SoundAssetManagerSingletonHolder{
        private final static SoundAssetManager instance = new SoundAssetManager();
    }

    public static SoundAssetManager getInstance()
    {
        return SoundAssetManagerSingletonHolder.instance;
    }

    public void dispose(){
        if(assetManager!=null)assetManager.dispose();
        if(swordSlashSound!=null)swordSlashSound.dispose();
        if(footstepSound!=null)footstepSound.dispose();
        if(heroHurtSound!=null)heroHurtSound.dispose();
    }

    public void playDrawSwordSound(){
        swordSlashSound.play();
    }

    public void playFootstep(){
        long id = footstepSound.play();
        footstepSound.setLooping(id, true);
        footstepSound.setVolume(id, 0.2f);
    }

    public void stopFootstep(){
        footstepSound.stop();
    }

    public void playHeroHurtSound(){
        heroHurtSound.play();
    }
}

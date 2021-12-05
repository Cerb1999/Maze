package fr.ul.maze.model.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;


public class SoundAssetManager {
    private final AssetManager assetManager;
    private static final String SOUND_PATH = "sound/";
    private static final String DRAW_SWORD_SOUND = "drawSword.ogg";
    private static final String FOOTSTEP_SOUND = "footstep.ogg";
    private static final String HEROHURT_SOUND = "heroHurt.ogg";
    private static final String HERO_SLOW_SOUND = "heroslow.ogg";
    private static final String MOB_DEATH_SOUND = "mobDeath.ogg";
    private static final String MOB_SLOW_SOUND = "mobSlow.ogg";
    private static final String DRINK_SOUND = "drink.ogg";
    private static final String REDRAW_SOUND = "redraw.ogg";
    private static final String BREAK_SOUND = "break.ogg";
    private static final String CLASH_SOUND = "clash.ogg";
    private static final String KEY_SOUND = "key.ogg";

    private static Sound swordSlashSound;
    private static Sound footstepSound;
    private static Sound heroHurtSound;
    private static Sound heroSlowSound;
    private static Sound mobDeathSound;
    private static Sound mobSlowSound;
    private static Sound drinkSound;
    private static Sound redrawSound;
    private static Sound breakSound;
    private static Sound clashSound;
    private static Sound keySound;

    private SoundAssetManager(){
        assetManager = new AssetManager();
        assetManager.load(SOUND_PATH + DRAW_SWORD_SOUND, Sound.class);
        assetManager.load(SOUND_PATH + FOOTSTEP_SOUND, Sound.class);
        assetManager.load(SOUND_PATH + HEROHURT_SOUND, Sound.class);
        assetManager.load(SOUND_PATH + HERO_SLOW_SOUND, Sound.class);
        assetManager.load(SOUND_PATH + MOB_DEATH_SOUND, Sound.class);
        assetManager.load(SOUND_PATH + MOB_SLOW_SOUND, Sound.class);
        assetManager.load(SOUND_PATH + DRINK_SOUND, Sound.class);
        assetManager.load(SOUND_PATH + REDRAW_SOUND, Sound.class);
        assetManager.load(SOUND_PATH + BREAK_SOUND, Sound.class);
        assetManager.load(SOUND_PATH + CLASH_SOUND, Sound.class);
        assetManager.load(SOUND_PATH + KEY_SOUND, Sound.class);
        assetManager.finishLoading();
        swordSlashSound = assetManager.get(SOUND_PATH + DRAW_SWORD_SOUND);
        footstepSound = assetManager.get(SOUND_PATH + FOOTSTEP_SOUND);
        heroHurtSound = assetManager.get(SOUND_PATH + HEROHURT_SOUND);
        heroSlowSound = assetManager.get(SOUND_PATH + HERO_SLOW_SOUND);
        mobDeathSound = assetManager.get(SOUND_PATH + MOB_DEATH_SOUND);
        mobSlowSound = assetManager.get(SOUND_PATH + MOB_SLOW_SOUND);
        drinkSound = assetManager.get(SOUND_PATH + DRINK_SOUND);
        redrawSound = assetManager.get(SOUND_PATH + REDRAW_SOUND);
        breakSound = assetManager.get(SOUND_PATH + BREAK_SOUND);
        clashSound = assetManager.get(SOUND_PATH + CLASH_SOUND);
        keySound = assetManager.get(SOUND_PATH + KEY_SOUND);
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
        if(mobDeathSound!=null)mobDeathSound.dispose();
        if(mobSlowSound!=null)mobSlowSound.dispose();
        if(heroSlowSound!= null) heroSlowSound.dispose();
        if(drinkSound!=null)drinkSound.dispose();
        if(redrawSound!=null)redrawSound.dispose();
        if(breakSound!=null)breakSound.dispose();
        if(clashSound!=null)clashSound.dispose();
        if(keySound!=null)keySound.dispose();
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
        long id = heroHurtSound.play();
        heroHurtSound.setVolume(id, 0.2f);
    }

    public void playMobDeathSound(){
        long id = mobDeathSound.play();
        mobDeathSound.setVolume(id,0.4f);
    }

    public void playMobSlowSound(){
        long id = mobSlowSound.play();
        mobSlowSound.setVolume(id,0.4f);
    }


    public void playHeroSlowSound(){
        long id = heroSlowSound.play();
        heroSlowSound.setVolume(id, 0.2f);
    }

    public void playDrinkSound(){
        long id = drinkSound.play();
        drinkSound.setVolume(id, 0.2f);
    }

    public void playRedrawSound(){
        long id = redrawSound.play();
        redrawSound.setVolume(id, 0.2f);
    }

    public void playBreakSound(){
        long id = breakSound.play();
        breakSound.setVolume(id, 0.2f);
    }

    public void playClash() {
        long id = clashSound.play();
        clashSound.setVolume(id, 0.2f);
    }

    public void playKey() {
        long id = keySound.play();
        keySound.setVolume(id, 0.3f);
    }
}

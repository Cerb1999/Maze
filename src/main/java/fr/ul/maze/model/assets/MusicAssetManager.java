package fr.ul.maze.model.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import fr.ul.maze.view.map.RigidSquare;

import java.util.Objects;

public class MusicAssetManager {
    private final AssetManager assetManager;
    private static final String MUSIC_PATH = "music/";
    private static final String MENU_MUSIC = "mymusic.mp3";
    private static final String GAME_MUSIC = "mysical_theme.mp3";
    private static final String GAMEOVER_MUSIC = "mymusic.mp3";

    private static Music bMusic;

    private MusicAssetManager(){
        assetManager = new AssetManager();
        //assetManager.load(MUSIC_PATH + MENU_MUSIC, Music.class);
        assetManager.load(MUSIC_PATH + GAME_MUSIC, Music.class);
        //assetManager.load(MUSIC_PATH + GAMEOVER_MUSIC, Music.class);
        assetManager.finishLoading();
    }

    private static class MusicAssetManagerSingletonHolder{
        private final static MusicAssetManager instance = new MusicAssetManager();
    }

    public static MusicAssetManager getInstance()
    {
        return MusicAssetManagerSingletonHolder.instance;
    }

    public void dispose(){
        if(assetManager!=null)assetManager.dispose();
        if(bMusic!=null)bMusic.dispose();
    }

    /**
     * Method to load music.
     * @param musicName The name of the music that is being looked up.
     * @return Music
     */
    private Music getManagedMusic(String musicName){
        Music music = assetManager.get(MUSIC_PATH + musicName, Music.class);
        return music;
    }

    public void stopMusic(){
        if (bMusic != null){
            bMusic.stop();
            bMusic = null;
        }
    }

    public void playMenuMusic(){
        this.stopMusic();
        bMusic = this.getManagedMusic(MENU_MUSIC);
        bMusic.setLooping(true);
        bMusic.play();
    }

    public void playGameMusic(){
        this.stopMusic();
        bMusic = this.getManagedMusic(GAME_MUSIC);
        bMusic.setLooping(true);
        bMusic.play();
    }

    public void playGameoverMusic(){
        this.stopMusic();
        bMusic = this.getManagedMusic(GAMEOVER_MUSIC);
        bMusic.setLooping(true);
        bMusic.play();
    }
}

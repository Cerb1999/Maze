package fr.ul.maze.model.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import fr.ul.maze.view.map.RigidSquare;

import java.util.Objects;

public class MazeAssetManager {

    private final AssetManager assetManager;
    private static final String ATLAS_NAME = "MazeAtlas.atlas"; //one atlas or multiple in the future ?

    private MazeAssetManager(){
        assetManager = new AssetManager();
        assetManager.load(ATLAS_NAME, TextureAtlas.class);
        assetManager.finishLoading();
    }

    private static class MazeAssetManagerSingletonHolder{
        private final static MazeAssetManager instance = new MazeAssetManager();
    }

    public static MazeAssetManager getInstance()
    {
        return MazeAssetManagerSingletonHolder.instance;
    }

    public void dispose(){
        if(assetManager!=null)assetManager.dispose();
    }

    /**
     * Convenience method to safely load textures.
     * If the texture isn't found, a blank one is created and the error is logged.
     * @param imageName The name of the image that is being looked up.
     * @return TextureRegionDrawable
     */
    public TextureRegionDrawable getManagedTexture(String imageName){
        TextureRegionDrawable textureRegionDrawable= new TextureRegionDrawable(assetManager.get(ATLAS_NAME, TextureAtlas.class).findRegion(imageName));
        if(Objects.isNull(textureRegionDrawable.getRegion())){
            Gdx.app.error(getClass().getCanonicalName(), "Couldn't get managed texture: " + imageName);
            textureRegionDrawable = this.getEmptyTexture();
        }
        return textureRegionDrawable;
    }
    public TextureRegionDrawable getEmptyTexture() {
        return new TextureRegionDrawable(new TextureRegion(new Texture(new Pixmap(RigidSquare.WIDTH, RigidSquare.HEIGHT, Pixmap.Format.RGBA8888))));
    }
}

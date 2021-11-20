package fr.ul.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ObjectMap;
import fr.ul.maze.model.GameState;
import fr.ul.maze.model.State;
import fr.ul.maze.view.*;

public class MazeGame extends Game {
	private static final String ATLAS_NAME = "MazeAtlas.atlas"; //one atlas or multiple in the future ?
	private ObjectMap<Class<? extends View>, View> screens = new ObjectMap<>();
	private AssetManager assetManager;
	private GameState gameState;

	@Override
	public void create () {
		assetManager = new AssetManager();
		assetManager.load(ATLAS_NAME, TextureAtlas.class);
		assetManager.finishLoading();

		gameState = new GameState();
		this.newGame();

		loadScreens();
		this.changeScreen(MenuView.class);

		gameState.constructLevel();
	}

	@Override
	public void dispose () {
		// Dispose of views
		setScreen(null);
		screens.forEach((e) -> e.value.dispose());

		// Dispose of the model
		gameState.dispose();

		// Dispose of any other resources
		assetManager.dispose();
	}

	/**
	 * Create a new game
	 */
	public void newGame() {
		gameState.newGame(this);
		this.switchScreen();
	}

	public void switchScreen() {
		switch (gameState.getState()) {
			case GAME_RUNNING:
				this.changeScreen(GameView.class);
				break;
			case GAME_PAUSED:
				this.changeScreen(PauseView.class);
				break;
			case GAME_LAUNCHING:
				this.changeScreen(MenuView.class);
				break;
			case GAME_LEVEL_END:
				this.changeScreen(LevelTransitionView.class);
				gameState.stopTimer();
				break;
			case GAME_OVER:
				this.changeScreen(GameOverView.class);
				gameState.stopTimer();
				break;
		}
	}

	public void switchState(State state) {
		gameState.changeState(state);
		switchScreen();
	}

	/**
	 * Method used to change the current Screen
	 * @param key The name of the view that is being looked up.
	 */
	public void changeScreen(Class<? extends View> key) {
		this.setScreen(screens.get(key));
	}

	/**
	 * Method used to pre-load all screens which may be used in the game in a array.
	 */
	public void loadScreens() {
		screens.put(MenuView.class, new MenuView(this, gameState));
		screens.put(PauseView.class, new PauseView(this, gameState));
		screens.put(LevelTransitionView.class, new LevelTransitionView(this, gameState));
		screens.put(GameOverView.class, new GameOverView(this, gameState));
		screens.put(GameView.class, new GameView(this, gameState));
	}

	/**
	 * Convenience method to safely load textures.
	 * If the texture isn't found, a blank one is created and the error is logged.
	 * @param imageName The name of the image that is being looked up.
	 * @return TextureRegionDrawable
	 */
	public TextureRegionDrawable getManagedTexture(String imageName){
		try {
			TextureRegionDrawable textureRegionDrawable= new TextureRegionDrawable(assetManager.get(ATLAS_NAME, TextureAtlas.class).findRegion(imageName));
			if(textureRegionDrawable.getRegion()==null)throw new Exception("getRegion() for " + imageName);
			return textureRegionDrawable;
		} catch(Exception e) {
			Gdx.app.error(getClass().getCanonicalName(), "Couldn't get managed texture.", e);
			return getEmptyTexture();
		}
	}
	public TextureRegionDrawable getEmptyTexture() {
		return new TextureRegionDrawable(new TextureRegion(new Texture(new Pixmap(64,64, Pixmap.Format.RGBA8888))));
	}
}

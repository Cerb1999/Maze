package fr.ul.maze;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import compiler.Options;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.assets.MazeAssetManager;
import fr.ul.maze.model.assets.MusicAssetManager;
import fr.ul.maze.model.assets.SoundAssetManager;
import fr.ul.maze.view.screens.*;
import java.util.concurrent.atomic.AtomicReference;


public final class MazeGame extends Game {
	private MasterScreen screen;
	private final AtomicReference<MasterState> state;

	private Stage stage;

	public MazeGame() {
		this.state = new AtomicReference<>(new MasterState());
	}

	@Override
	public void create() {
		if (Options.DEBUG) Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.setApplicationLogger(new ColoredStderrLogger());

		Box2D.init();
		MazeAssetManager.getInstance();

		this.stage = new Stage(new ScreenViewport());
		this.screen = new MasterScreen(this.state);

		this.stage.setDebugAll(Options.DEBUG);

		this.screen.switchScreen(this.screen.MENU_SCREEN.get());
		this.setScreen(this.screen);
	}

	@Override
	public void dispose() {
		this.screen.dispose();
		MazeAssetManager.getInstance().dispose();
		MusicAssetManager.getInstance().dispose();
		SoundAssetManager.getInstance().dispose();
	}
}
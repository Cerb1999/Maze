package fr.ul.maze;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		config.width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		new LwjglApplication(new MazeGame(), config);
	}
}

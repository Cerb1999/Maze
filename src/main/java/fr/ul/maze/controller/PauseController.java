package fr.ul.maze.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import fr.ul.maze.MazeGame;
import fr.ul.maze.model.GameState;
import fr.ul.maze.model.State;

public class PauseController implements InputProcessor {
    MazeGame mazeGame;
    GameState gameState;

    public PauseController(MazeGame mazeGame, GameState gameState) {
        this.mazeGame = mazeGame;
        this.gameState = gameState;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.ESCAPE: {
                gameState.switchState();
                State state = gameState.getState();
                if(state == State.GAME_RUNNING) gameState.restartTimer();
                else if(state == State.GAME_PAUSED) gameState.stopTimer();
                mazeGame.switchScreen();
                break;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

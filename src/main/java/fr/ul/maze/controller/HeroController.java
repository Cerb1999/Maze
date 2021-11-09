package fr.ul.maze.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import fr.ul.maze.MazeGame;
import fr.ul.maze.model.GameState;
import fr.ul.maze.model.HeroMoveState;

public class HeroController implements InputProcessor {
    MazeGame mazeGame;
    GameState gameState;

    public HeroController(MazeGame mazeGame, GameState gameState) {
        this.mazeGame = mazeGame;
        this.gameState = gameState;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT: gameState.getHero().moveHero(HeroMoveState.LEFT); break;
            case Input.Keys.RIGHT: gameState.getHero().moveHero(HeroMoveState.RIGHT); break;
            case Input.Keys.UP: gameState.getHero().moveHero(HeroMoveState.UP); break;
            case Input.Keys.DOWN: gameState.getHero().moveHero(HeroMoveState.DOWN); break;
            case Input.Keys.SPACE: gameState.getHero().attack(); break;
            case Input.Keys.R: gameState.getHero().die(); break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        gameState.getHero().moveHero(HeroMoveState.IDLE);
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

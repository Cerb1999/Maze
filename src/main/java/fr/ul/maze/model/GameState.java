package fr.ul.maze.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import fr.ul.maze.MazeGame;
import fr.ul.maze.controller.WorldContactListener;
import fr.ul.maze.model.generator.RandomMazeGenerator;

import java.util.concurrent.atomic.AtomicReference;

public class GameState{
    private State state;
    private AtomicReference<Level> level;
    private World world;
    private Hero hero;
    private MazeGame mazeGame;
    private Stage stage;
    private Pause pause;

    /**
     * Start a new game.
     */
    public void newGame(MazeGame mazeGame) {
        changeState(State.GAME_RUNNING);
        this.mazeGame = mazeGame;
        this.level = new AtomicReference<>(new RandomMazeGenerator().generateMaze(1));
        world = new World(new Vector2(0, 0), true); //world for physics (box2d)
        world.setContactListener(new WorldContactListener(this));
        pause = new Pause(mazeGame);
    }

    public void dispose() {

    }

    public void nextLevel(){
        this.level.set(new RandomMazeGenerator().generateMaze(this.level.get().getNumber()+1));
        System.out.println("Level: " + this.level.get().getNumber());
        this.stage.clear();
        world = new World(new Vector2(0, 0), true); //world for physics (box2d)
        world.setContactListener(new WorldContactListener(this));
        this.constructLevel();
    }

    public void constructLevel(){
        this.level.updateAndGet(level -> {
            int currentLine = 0;
            int currentLineLength = 0;

            for (Cell c : level) {
                if(level.getHeroPosition().equals(new Position(currentLineLength, currentLine))){
                    PathCell pathCellStart = new PathCell();
                    pathCellStart.createCell(mazeGame, world, currentLineLength*64, currentLine*64);
                    stage.addActor(pathCellStart);
                    hero = new Hero(mazeGame, world, currentLineLength*64, currentLine*64); // random position
                    stage.addActor(getHero());
                    hero.setZIndex(Integer.MAX_VALUE-1);
                }
                else if(c.isMob()){
                    PathCell pathCellMobStart = new PathCell();
                    pathCellMobStart.createCell(mazeGame, world, currentLineLength*64, currentLine*64);
                    stage.addActor(pathCellMobStart);
                    Mob mob = new Mob(mazeGame, world, currentLineLength*64, currentLine*64);
                    stage.addActor(mob);
                    mob.setZIndex(Integer.MAX_VALUE-1);
                }
                else{
                    c.createCell(mazeGame, world, currentLineLength*64, currentLine*64);
                    stage.addActor(c);
                    if (c.isWall()) c.setZIndex(Integer.MAX_VALUE);
                }

                if (++currentLineLength >= Level.WIDTH) {
                    currentLine++;
                    currentLineLength = 0;
                    System.out.println();
                }
            }
            return level;
        });
    }

    public int getLevelNumber() {
        return level.get().getNumber();
    }

    public void changeState(State state) {
        this.state = state;
    }

    public void switchState() {
        state = (state == State.GAME_RUNNING) ? State.GAME_PAUSED : State.GAME_RUNNING;
    }

    public State getState() {
        return state;
    }

    public World getWorld() {
        return world;
    }

    public Hero getHero() {
        return hero;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Pause getPause() {
        return pause;
    }
}

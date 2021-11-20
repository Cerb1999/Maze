package fr.ul.maze.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import fr.ul.maze.MazeGame;
import fr.ul.maze.controller.WorldContactListener;
import fr.ul.maze.model.generator.RandomMazeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GameState{
    private static final int BASE_TIMER = 100;
    private State state;
    private AtomicReference<Level> level;
    private World world;
    private Hero hero;
    private MazeGame mazeGame;
    private Stage stage;
    private TimerTask timerTask;
    /**
     * Start a new game.
     */
    public void newGame(MazeGame mazeGame) {
        changeState(State.GAME_LAUNCHING);
        this.timerTask = new TimerTask(this);
        this.mazeGame = mazeGame;
        this.level = new AtomicReference<>(new RandomMazeGenerator().generateMaze(1));
        world = new World(new Vector2(0, 0), true); //world for physics (box2d)
        world.setContactListener(new WorldContactListener(this));
    }

    public void dispose() {

    }

    public void nextLevel(){
        changeState(State.GAME_LEVEL_END);
        this.level.set(new RandomMazeGenerator().generateMaze(this.level.get().getNumber()+1));
        System.out.println("Level: " + this.level.get().getNumber());
        timerTask.refreshCountdown();
        this.stage.clear();
        world = new World(new Vector2(0, 0), true); //world for physics (box2d)
        world.setContactListener(new WorldContactListener(this));
        this.constructLevel();
    }

    public void constructLevel(){
        this.level.updateAndGet(level -> {
            int currentLine = 0;
            int currentLineLength = 0;

            List<Entity> entities = new ArrayList<>();
            List<Cell> walls = new ArrayList<>();

            for (Cell c : level) {
                if(level.getHeroPosition().equals(new Position(currentLineLength, currentLine))){
                    PathCell pathCellStart = new PathCell();
                    pathCellStart.createCell(mazeGame, world, currentLineLength*64, currentLine*64);
                    stage.addActor(pathCellStart);
                    hero = new Hero(mazeGame, world, currentLineLength*64, currentLine*64); // random position
                    stage.addActor(getHero());
                }
                else if(c.isMob()){
                    PathCell pathCellMobStart = new PathCell();
                    pathCellMobStart.createCell(mazeGame, world, currentLineLength*64, currentLine*64);
                    stage.addActor(pathCellMobStart);
                    Mob mob = new Mob(mazeGame, world, currentLineLength*64, currentLine*64);
                    stage.addActor(mob);
                    entities.add(mob);
                }
                else{
                    c.createCell(mazeGame, world, currentLineLength*64, currentLine*64);
                    stage.addActor(c);
                    if (c.isWall()) walls.add(c);
                }

                if (++currentLineLength >= Level.WIDTH) {
                    currentLine++;
                    currentLineLength = 0;
                    System.out.println();
                }
            }
            hero.toFront();
            //Update other entities' z in order for them to always stay visible
            for(Entity enti : entities){
                enti.toFront();
            }
            //Same for walls
            for(Cell wall : walls){
                wall.toFront();
            }
            return level;
        });
    }
    public void changeState(State state) {
        this.state = state;
    }

    public void switchState() {
        state = (state == State.GAME_RUNNING) ? State.GAME_PAUSED : State.GAME_RUNNING;
    }

    public void checkHeroState() {
        switch (hero.actionState) {
            case DYING:
                changeState(State.GAME_OVER);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
    public AtomicReference<Level> getLevel() { return level; }
    public String getTime() {
        return timerTask.getTime()+"";
    }

    public MazeGame getMazeGame() {
        return mazeGame;
    }
    public int getLevelNumber() {
        return level.get().getNumber();
    }

    public void refreshTimer() {
        timerTask.refreshCountdown();
        timerTask.start();
    }
    public void launchTimer() {
        timerTask.launch();
    }
    public void restartTimer() {
        timerTask.start();
    }
    public void stopTimer() {
        timerTask.stop();
    }
    public boolean dyingHeroState() {
        return hero.actionState == EntityActionState.DYING;
    }
}

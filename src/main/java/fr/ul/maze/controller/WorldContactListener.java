package fr.ul.maze.controller;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import fr.ul.maze.model.*;

public class WorldContactListener implements ContactListener {
    private final GameState gameState;

    public WorldContactListener(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        //Redirect mob if he persist to walk against a wall or another mob
        if(((contact.getFixtureA().getUserData().equals("Wall") || contact.getFixtureA().getUserData() instanceof Mob)&& contact.getFixtureB().getUserData() instanceof Mob) || (contact.getFixtureB().getUserData().equals("Wall") && contact.getFixtureA().getUserData() instanceof Mob)){
            Mob mob;
            if(contact.getFixtureB().getUserData() instanceof Mob)
                mob = (Mob) contact.getFixtureB().getUserData();
            else
                mob = (Mob) contact.getFixtureA().getUserData();
            Direction nextMove = Direction.getRandomDirection();
            while(mob.getMoveState()==nextMove)
                nextMove = Direction.getRandomDirection();
            mob.addNbWallMoveRandom();
            if(mob.getNbWallMoveRandom()>=2) nextMove = Direction.get90DegreDirection(nextMove);
            mob.move(nextMove);
        }//instanceof needed
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    @Override
    public void beginContact(Contact contact) {
        if((contact.getFixtureA().getUserData().equals("Hero") && contact.getFixtureB().getUserData().equals("Ladder")) || (contact.getFixtureB().getUserData().equals("Hero") && contact.getFixtureA().getUserData().equals("Ladder"))) {
            gameState.nextLevel();
            gameState.getMazeGame().switchScreen();
        } else if((contact.getFixtureA().getUserData().equals("HeroSword") && contact.getFixtureB().getUserData() instanceof Mob) || (contact.getFixtureB().getUserData().equals("HeroSword") && contact.getFixtureA().getUserData() instanceof Mob)) {
            Mob mob;
            if (contact.getFixtureB().getUserData() instanceof Mob)
                mob = (Mob) contact.getFixtureB().getUserData();
            else
                mob = (Mob) contact.getFixtureA().getUserData();
            mob.hurt(1);

        //partie test alex
        } else if((contact.getFixtureA().getUserData().equals("Hero") && contact.getFixtureB().getUserData() instanceof Mob) || (contact.getFixtureB().getUserData().equals("Hero") && contact.getFixtureA().getUserData() instanceof Mob)) {
            Hero hero = gameState.getHero();
            hero.hurt(1);
            gameState.checkHeroState();
            if(gameState.dyingHeroState()) {
                gameState.stopTimer();
                //TODO arrêter les mobs ?
                Timer timer = new Timer();
                Timer.Task task = timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run () {
                        gameState.getMazeGame().switchScreen();
                    }
                }, 2);
            }
            //instanceof needed
        }
    }
}

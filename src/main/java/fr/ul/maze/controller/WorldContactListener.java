package fr.ul.maze.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import fr.ul.maze.model.GameState;
import fr.ul.maze.model.Mob;

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

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    @Override
    public void beginContact(Contact contact) {
        if((contact.getFixtureA().getUserData().equals("Hero") && contact.getFixtureB().getUserData().equals("Ladder")) || (contact.getFixtureB().getUserData().equals("Hero") && contact.getFixtureA().getUserData().equals("Ladder")))
            gameState.nextLevel();
        if((contact.getFixtureA().getUserData().equals("HeroSword") && contact.getFixtureB().getUserData() instanceof Mob) || (contact.getFixtureB().getUserData().equals("HeroSword") && contact.getFixtureA().getUserData() instanceof Mob)){
            Mob mob;
            if(contact.getFixtureB().getUserData() instanceof Mob)
                mob = (Mob) contact.getFixtureB().getUserData();
            else
                mob = (Mob) contact.getFixtureA().getUserData();
            mob.hurt(1);
        }//instanceof needed

    }
}

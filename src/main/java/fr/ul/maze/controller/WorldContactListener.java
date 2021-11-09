package fr.ul.maze.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import fr.ul.maze.model.GameState;

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
    }
}

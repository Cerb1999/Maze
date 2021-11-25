package fr.ul.maze.controller.contact;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import exceptions.Exn;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.entities.Hero;
import fr.ul.maze.model.entities.Mob;
import fr.ul.maze.view.screens.MapScreen;
import fr.ul.maze.view.screens.MasterScreen;

import java.util.concurrent.atomic.AtomicReference;

public final class MasterContactController implements ContactListener {
    private final EndLevelController endLevelController;
    private final MobAttackController mobAttackController;
    private final AtomicReference<MasterState> state;
    private final MapScreen mapScreen;
    private final MasterScreen master;

    public MasterContactController(AtomicReference<MasterState> state, MapScreen mapScreen, MasterScreen masterScreen) {
        this.endLevelController = new EndLevelController();
        this.mobAttackController = new MobAttackController(masterScreen);
        this.state = state;
        this.mapScreen = mapScreen;
        this.master = masterScreen;
    }

    @Override
    public void beginContact(Contact contact) {
            if((contact.getFixtureA().getUserData() instanceof Hero && contact.getFixtureB().getUserData().equals("Ladder")) || (contact.getFixtureB().getUserData() instanceof Hero && contact.getFixtureA().getUserData().equals("Ladder")))
            endLevelController.nextLevel(this.state, this.mapScreen, this.master);
        else if((contact.getFixtureA().getUserData().equals("HeroSword") && contact.getFixtureB().getUserData() instanceof Mob) || (contact.getFixtureB().getUserData().equals("HeroSword") && contact.getFixtureA().getUserData() instanceof Mob)){
            Mob mob;
            if(contact.getFixtureB().getUserData() instanceof Mob)
                mob = (Mob) contact.getFixtureB().getUserData();
            else
                mob = (Mob) contact.getFixtureA().getUserData();
            mob.damage(1);
        //instanceof needed
        } else if((contact.getFixtureA().getUserData() instanceof Hero && contact.getFixtureB().getUserData() instanceof Mob) || (contact.getFixtureB().getUserData() instanceof Hero && contact.getFixtureA().getUserData() instanceof Mob))
        {
            Mob mob;
            Hero hero;
            if(contact.getFixtureB().getUserData() instanceof Mob) {
                mob = (Mob) contact.getFixtureB().getUserData();
                hero = (Hero) contact.getFixtureA().getUserData();
            } else {
                mob = (Mob) contact.getFixtureA().getUserData();
                hero = (Hero) contact.getFixtureB().getUserData();
            }
            mobAttackController.attack(mob, hero);
        } else if((contact.getFixtureA().getUserData() instanceof Hero && contact.getFixtureB().getUserData() instanceof Mob) || (contact.getFixtureB().getUserData() instanceof Hero && contact.getFixtureA().getUserData() instanceof Mob)){
            Hero hero;
            if(contact.getFixtureA().getUserData() instanceof Hero) {
                    hero = (Hero) contact.getFixtureA().getUserData();
                    hero.damage(1);
                } else {
                    hero = (Hero) contact.getFixtureB().getUserData();
                    hero.damage(1);
                }
        }
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
}

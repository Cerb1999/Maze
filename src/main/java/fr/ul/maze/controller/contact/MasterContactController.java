package fr.ul.maze.controller.contact;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import exceptions.Exn;
import fr.ul.maze.model.entities.Mob;

public final class MasterContactController implements ContactListener {
    private final EndLevelController endLevelController;
    private final MobAttackController mobAttackController;

    public MasterContactController() {
        this.endLevelController = new EndLevelController();
        this.mobAttackController = new MobAttackController();
    }

    @Override
    public void beginContact(Contact contact) {
        if((contact.getFixtureA().getUserData().equals("Hero") && contact.getFixtureB().getUserData().equals("Ladder")) || (contact.getFixtureB().getUserData().equals("Hero") && contact.getFixtureA().getUserData().equals("Ladder")))
            Exn.todo("endLevelController.nextLevel(state)");
            //endLevelController.nextLevel();
        else if((contact.getFixtureA().getUserData().equals("HeroSword") && contact.getFixtureB().getUserData() instanceof Mob) || (contact.getFixtureB().getUserData().equals("HeroSword") && contact.getFixtureA().getUserData() instanceof Mob)){
            Mob mob;
            if(contact.getFixtureB().getUserData() instanceof Mob)
                mob = (Mob) contact.getFixtureB().getUserData();
            else
                mob = (Mob) contact.getFixtureA().getUserData();
            mob.damage(1);
        }//instanceof needed

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
package fr.ul.maze.controller.contact;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import exceptions.Exn;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.entities.Hero;
import fr.ul.maze.model.entities.Mob;
import fr.ul.maze.model.entities.items.Item;
import fr.ul.maze.view.screens.MapScreen;
import fr.ul.maze.view.screens.MasterScreen;

import java.util.concurrent.atomic.AtomicReference;

public final class MasterContactController implements ContactListener {
    private final EndLevelController endLevelController;
    private final MobAttackController mobAttackController;
    private final NoAttackController noAttackController;
    private final AtomicReference<MasterState> state;
    private final MapScreen mapScreen;
    private final MasterScreen master;

    public MasterContactController(AtomicReference<MasterState> state, MapScreen mapScreen, MasterScreen masterScreen) {
        this.endLevelController = new EndLevelController();
        this.mobAttackController = new MobAttackController(masterScreen, state);
        this.noAttackController = new NoAttackController();
        this.state = state;
        this.mapScreen = mapScreen;
        this.master = masterScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        if ((contact.getFixtureA().getUserData().equals("Hero") && contact.getFixtureB().getUserData() instanceof Item || (contact.getFixtureB().getUserData().equals("Hero") && contact.getFixtureA().getUserData() instanceof Item))) {
            Item item;
            if((contact.getFixtureB().getUserData() instanceof Item))
                item = (Item) contact.getFixtureB().getUserData();
            else item = (Item) contact.getFixtureA().getUserData();
            switch (item.getItemType()) {
                case LADDER:
                    endLevelController.nextLevel(this.state, this.mapScreen, this.master);
                    break;
                case LIFEUP:
                    state.get().getHero().get().heal(1);
                    item.remove();
                    break;
                case NOATTACK:
                    noAttackController.disableWeapon(state);
                    item.remove();
                    break;
            }
        }
        else if((contact.getFixtureA().getUserData().equals("HeroSword") && contact.getFixtureB().getUserData() instanceof Mob) || (contact.getFixtureB().getUserData().equals("HeroSword") && contact.getFixtureA().getUserData() instanceof Mob)){
            Mob mob;
            if(contact.getFixtureB().getUserData() instanceof Mob)
                mob = (Mob) contact.getFixtureB().getUserData();
            else
                mob = (Mob) contact.getFixtureA().getUserData();
            mob.damage(1);
        //instanceof needed
        } else if((contact.getFixtureA().getUserData().equals("Hero") && contact.getFixtureB().getUserData() instanceof Mob) || (contact.getFixtureB().getUserData().equals("Hero") && contact.getFixtureA().getUserData() instanceof Mob))
        {
            Mob mob;
            Hero hero = state.get().getHero().get();
            if(contact.getFixtureB().getUserData() instanceof Mob) {
                mob = (Mob) contact.getFixtureB().getUserData();
            } else {
                mob = (Mob) contact.getFixtureA().getUserData();
            }
            mobAttackController.attack(mob, hero);
        } else if((contact.getFixtureA().getUserData().equals("Hero") && contact.getFixtureB().getUserData() instanceof Mob) || (contact.getFixtureB().getUserData().equals("Hero") && contact.getFixtureA().getUserData() instanceof Mob)){
            Hero hero = state.get().getHero().get();;
            hero.damage(1);
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

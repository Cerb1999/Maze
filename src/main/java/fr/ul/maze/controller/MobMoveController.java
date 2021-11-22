package fr.ul.maze.controller;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import exceptions.Exn;
import fr.ul.maze.model.Direction;
import fr.ul.maze.model.MasterState;
import fr.ul.maze.model.entities.EntityActionState;
import fr.ul.maze.model.entities.Hero;
import fr.ul.maze.model.entities.Mob;
import fr.ul.maze.model.map.Square;
import fr.ul.maze.model.maze.GraphNode;
import fr.ul.maze.view.map.RigidSquare;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public final class MobMoveController {
    private final Timer.Task action;
    private final AtomicReference<Mob> mob;
    private final AtomicReference<MasterState> state;
    private Vector2 nextPosition;

    public MobMoveController(final AtomicReference<MasterState> state, final AtomicReference<Mob> mob) {
        this.mob = mob;
        this.state = state;

        this.action = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                moveMob();
            }
        }, 0, 0.1f);
    }

    private void moveMob() {
        float mobPosX = mob.get().getPosition().x;
        float mobPosY = mob.get().getPosition().y;

        Hero hero = this.state.get().getHero().get();
        Vector2 heroPosition = new Vector2(Math.round(hero.getPosition().x/ RigidSquare.WIDTH), Math.round(hero.getPosition().y/ RigidSquare.HEIGHT));
        Vector2 myPosition = new Vector2(Math.round(mobPosX / RigidSquare.WIDTH), Math.round(mobPosY / RigidSquare.HEIGHT));

        if (nextPosition != null) {
            Vector2 next = new Vector2(nextPosition.x * RigidSquare.WIDTH, nextPosition.y * RigidSquare.HEIGHT);

            Direction d = Direction.IDLE;
            if (mobPosX - next.x > 0.5)
                d = Direction.LEFT;
            else if (next.x - mobPosX > 0.5)
                d = Direction.RIGHT;
            else if (mobPosY - next.y > 0.5)
                d = Direction.DOWN;
            else if (next.y - mobPosY > 0.5)
                d = Direction.UP;

            this.move(d);

            if (d != Direction.IDLE)
                return;
            nextPosition = null;
        }

        GraphPath<GraphNode> path = this.state.get().getLevel().get().findPath(myPosition, heroPosition);

        if (path.getCount() < 2)
            return;

        nextPosition = path.get(1).fst; // only consider the first next cell to move to
    }

    /**
     * Method used to move the body of the Mob.
     * Apply force to the body depending of the direction.
     * @param d direction where which the mob move.
     */
    public void move(Direction d) {
        AtomicReference<Mob> mob = this.mob;

        mob.updateAndGet(h -> {
            h.updateBody(body -> {
                if(h.getActionState() == EntityActionState.IDLE){
                    if(d==Direction.UPRIGHT || d==Direction.UPLEFT) h.setMoveState(Direction.UP);
                    else if(d==Direction.DOWNRIGHT || d==Direction.DOWNLEFT) h.setMoveState(Direction.DOWN);
                    else h.setMoveState(d);
                    //System.out.println(h.getMoveState());
                    switch(d) {
                        case UP:
                            body.setLinearVelocity(0,h.getWalkSpeed());
                            break;
                        case UPRIGHT:
                            body.setLinearVelocity(h.getWalkSpeed(), h.getWalkSpeed());
                            break;
                        case UPLEFT:
                            body.setLinearVelocity(-h.getWalkSpeed(), h.getWalkSpeed());
                            break;
                        case DOWN:
                            body.setLinearVelocity(0,-h.getWalkSpeed());
                            break;
                        case DOWNRIGHT:
                            body.setLinearVelocity(h.getWalkSpeed(), -h.getWalkSpeed());
                            break;
                        case DOWNLEFT:
                            body.setLinearVelocity(-h.getWalkSpeed(), -h.getWalkSpeed());
                            break;
                        case RIGHT:
                            body.setLinearVelocity(h.getWalkSpeed(),0);
                            break;
                        case LEFT:
                            body.setLinearVelocity(-h.getWalkSpeed(),0);
                            break;
                        case IDLE:
                            body.setLinearVelocity(0,0);
                            break;
                    }
                }
                else
                    body.setLinearVelocity(0,0);
            });
            return h;
        });
    }
}

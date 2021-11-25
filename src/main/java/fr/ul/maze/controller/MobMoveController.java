package fr.ul.maze.controller;

import com.badlogic.gdx.Gdx;
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
import fr.ul.maze.model.maze.Maze;
import fr.ul.maze.view.map.RigidSquare;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public final class MobMoveController {
    private final AtomicReference<Mob> mob;
    private final AtomicReference<MasterState> state;
    private Vector2 nextPosition;

    public MobMoveController(final AtomicReference<MasterState> state, final AtomicReference<Mob> mob) {
        this.mob = mob;
        this.state = state;
    }

    public void moveMob() {
        Vector2 realPosition = mob.get().getPosition();

        float mobPosX = realPosition.x;
        float mobPosY = realPosition.y;

        Vector2 relativePosition = new Vector2(
                (float) Math.ceil(realPosition.x / RigidSquare.WIDTH) - 1,
                (float) Math.ceil(realPosition.y / RigidSquare.HEIGHT) - 1
        );

        Hero hero = this.state.get().getHero().get();
        Vector2 heroPosition = new Vector2(
                (float) Math.ceil(hero.getPosition().x / RigidSquare.WIDTH) - 1,
                (float) Math.ceil(hero.getPosition().y / RigidSquare.HEIGHT) - 1
        );

        if (nextPosition != null) {
            Vector2 next = new Vector2(
                    nextPosition.x * RigidSquare.WIDTH + RigidSquare.WIDTH / 2f,
                    nextPosition.y * RigidSquare.HEIGHT + RigidSquare.HEIGHT / 2f
            );

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

        GraphPath<GraphNode> path = this.state.get().getLevel().get().findPath(relativePosition, heroPosition);

        Gdx.app.debug(getClass().getCanonicalName(), "X: " + mobPosX + " Y: " + mobPosY);
        Gdx.app.debug(getClass().getCanonicalName(), "Finding path from " + relativePosition + " to " + heroPosition);

        if (path.getCount() < 2)
            return;

        Gdx.app.debug(getClass().getCanonicalName(), "Path found! Moving towards it");

        nextPosition = path.get(1).fst; // only consider the first next cell to move to

        Gdx.app.debug(getClass().getCanonicalName(), "Next position to chase: " + this.nextPosition);
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

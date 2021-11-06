package fr.ul.maze.model;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Simple class representing characteristics of the Hero
 */
public class Hero {
    /**
     * Fixed base hero hit points pool
     */
    private static final int BASE_HP = 3;

    /**
     * fixed base hero base movement speed;
     */
    private static final int BASE_MS = 100;

    /**
     * fixed hero attack power
     */
    private static final int BASE_ATK = 10;

    /**
     * fixed hero life and movement speed count threshold values
     */
    private static final int THRESHOLD = 0;

    /**
     * hero life value
     */
    private AtomicReference<Integer> hp = new AtomicReference<>(BASE_HP);

    /**
     * hero movement speed value
     */
    private AtomicReference<Integer> ms = new AtomicReference<>(BASE_MS);

    /**
     * hero attack power value
     */
    private AtomicReference<Integer> atk = new AtomicReference<>(BASE_ATK);


    /**
     * Lose life value up to death threshold
     * @param hp hero life value lost
     */
    public void loseLife(int hp) {
        int val = this.hp.updateAndGet(value -> Math.max(value - hp, THRESHOLD));
        assert (val >= THRESHOLD) : "inconsistent life loss";
    }

    /**
     * Regenerate life value up to base hit points
     * @param hp hero life value regained
     */
    public void gainLife(int hp) {
        int val = this.hp.updateAndGet(value -> Math.min(value + hp, BASE_HP));
        assert (val >= BASE_HP) : "inconsistent life gain";
    }

    /**
     * Hero slow down
     * @param ms movement speed gain
     */
    public void accelerate(int ms) {
        this.ms.set(this.ms.get() + ms);
    }

    /**
     * Hero speed up
     * @param ms movement speed loss
     */
    public void decelerate(int ms) {
        int val = this.ms.updateAndGet(value -> Math.max(value - ms, THRESHOLD));
        assert (val >= THRESHOLD) : "inconsistent movement speed slow value";
    }

    /**
     * Hero power up
     * @param atk attack power gain
     */
    public void powerUp(int atk) {
        this.atk.set(this.atk.get() + atk);
    }

    /**
     * Hero power down
     * @param atk attack power gain
     */
    public void powerDown(int atk) {
        int val = this.atk.updateAndGet(value -> Math.max(value - atk, THRESHOLD + 1));
        assert (val > THRESHOLD) : "inconsistent attack power value";
    }

    /**
     * check death
     * @return hero death state
     */
    public boolean isDead() {
        return hp.get() == 0;
    }
}

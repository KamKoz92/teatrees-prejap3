package com.epam.prejap.teatrees.pause;

import com.epam.prejap.teatrees.game.Waiter;

/**
 * Provides pause functionality to the game.
 * Optionally, it allows player to set re-run delay in seconds.
 *
 * @author Maciej Blok
 */
public class Pause {

    private boolean pause;
    private Waiter waiter;
    private int millisToSecondsMultiplier = 1000;

    public  Pause(Waiter waiter) {
        pause = false;
        this.waiter = waiter;
    }
    private Pause(boolean pause, Waiter waiter) {
        this.pause = pause;
        this.waiter = waiter;
    }

    /**
     * Switches boolean pause flag.
     */
    void pause() {
        pause = !pause;
    }

    /**
     * Delays re-run of the game with specified delay time.
     */
    void waitForIt() {
        waiter.waitForIt();
    }

    /**
     * Initializes new Waiter object.
     * That Waiter object is used to perform re-run delay in pause deactivation step.
     *
     * @param sleepTime value of re-run delay once the pause is deactivated in seconds
     */
    Pause initiateWaiter(int sleepTime) {
        return new Pause(true, new Waiter(sleepTime * millisToSecondsMultiplier));
    }

    /**
     * Checks if {@code pause} flag being set to {code true}.
     *
     * @return boolean
     */
    boolean isPause() {
        return pause;
    }

}

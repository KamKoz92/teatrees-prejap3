package com.epam.prejap.teatrees.game;

import java.util.concurrent.TimeUnit;

/**
 * The {@code Waiter} class allows to pause code execution by invoking
 * {@link #waitForIt()}.
 * Wait time is specified in the constructor and can be decreased by
 * {@link #decreaseCycleDelay(int)}.
 * 
 * @since 0.1
 */
public class Waiter {
    private final int DELAY_DECREASE = 100;
    private int milliseconds;

    /**
     * Initializes a newly created {@code Waiter} object with an internal
     * delay value set to the value of the passed argument.
     * 
     * @param milliseconds internal delay time in milliseconds
     */
    public Waiter(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    /**
     * Decreases the objects's delay value by 100 milliseconds every 10 points.
     * Minimum delay is capped at 100 milliseconds.
     * 
     * @param score game score
     * @since 0.6
     */
    public void decreaseCycleDelay(int score) {
        if (score % 10 == 0 && milliseconds > DELAY_DECREASE) {
            milliseconds -= DELAY_DECREASE;
        }
    }

    /**
     * Causes to pause code execution for the time specified
     * in the object's internal delay variable.
     */
    public void waitForIt() {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException ignore) {
        }
    }
}

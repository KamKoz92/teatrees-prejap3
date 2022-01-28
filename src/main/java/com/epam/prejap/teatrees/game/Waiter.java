package com.epam.prejap.teatrees.game;

import java.util.concurrent.TimeUnit;

public class Waiter {

    private int milliseconds;

    /**
     * Constructs a newly allocated {@code Waiter} object with an internal
     * delay value set to the value of the passed argument.
     * 
     * @param milliseconds internal delay time in milliseconds
     * @since 0.1
     */
    public Waiter(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    /**
     * Decreases the objects's delay value by 100 milliseconds every 10 points.
     * Minimum delay is capped at 100 milliseconds.
     * 
     * @param score game score
     * @since 0.3
     */
    public void decreaseCycleDelay(int score) {
        if (score % 10 == 0 && milliseconds > 100) {
            milliseconds -= 100;
        }

    }

    /**
     * Causes to pause code execution for the time specified
     * in the object's internal delay variable.
     * 
     * @since 0.1
     */
    public void waitForIt() {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException ignore) {
        }
    }
}

package com.epam.prejap.teatrees.game;

import java.util.concurrent.TimeUnit;

public class Waiter {

    private int milliseconds;

    public Waiter(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    /**
     * Decreases the delay between cycles by 100 milliseconds every 10 points.
     * Minimum delay is capped at 100 milliseconds.
     * 
     * @param score game score
     * @since 0.2
     */
    public void decreaseCycleDelay(int score) {
        if (score % 10 == 0 && milliseconds > 100) {
            milliseconds -= 100;
        }
    }

    public void waitForIt() {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException ignore) {
        }
    }
}

package com.epam.prejap.teatrees.pause;

import com.epam.prejap.teatrees.game.Waiter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

@Test
public class PauseTest {

    private Pause pause;
    private final int millisToSecondsMultiplier = 1000;

    @DataProvider
    public static Object[][] correctDelayValue() {
        return new Object[][]{
                {1}
                , {0}
                , {2}
        };
    }

    @DataProvider
    public static Object[][] negativeDelayValue() {
        return new Object[][]{
                {-1}
                , {-3}
                , {-2}
        };
    }

    @BeforeMethod
    public void setupPause() {
        pause = new Pause(new Waiter(0));
    }

    @Test
    public void invokingPauseSwitchesPauseFlag() {
        // given pause
        // when
        pause.pause();
        // then
        assertTrue(pause.isPause(), "Expected isPause to be true but was " + pause.isPause());
    }

    @Test(dataProvider = "correctDelayValue")
    public void pauseFollowedByDelayNumberMakesItThisNumberLonger(int delay) {
        // given dp
        // when
        long noPauseStartTime = System.currentTimeMillis();
        noPause();
        long noPauseFinishTime = System.currentTimeMillis();
        long noPauseTime = (noPauseFinishTime - noPauseStartTime) / millisToSecondsMultiplier;

        long pauseStartTime = System.currentTimeMillis();
        pause(delay);
        long pauseFinishTime = System.currentTimeMillis();
        long pauseTime = (pauseFinishTime - pauseStartTime) / millisToSecondsMultiplier;
        // then
        String delaySeconds = String.format("%02d:%02d", 00, delay);
        String delayDifference = String.format("%02d:%02d", 00, (pauseTime - noPauseTime));
        assertEquals(delay, pauseTime - noPauseTime, "Expected difference in execution time " +
                delaySeconds + " seconds" + " but was " + delayDifference + ".");
    }

    @Test(dataProvider = "negativeDelayValue")
    public void negativeDelayValueDoesntTriggerRerunDelay(int delay) {
        // given dp
        // when
        long pauseStartTime = System.currentTimeMillis();
        pause(delay);
        long pauseFinishTime = System.currentTimeMillis();
        long pauseTime = (pauseFinishTime - pauseStartTime);
        // then
        assertEquals(pauseTime, 0, "Expected pause time 0 seconds" + " but was " + pauseTime);
    }

    /**
     * Empty method on purpose to distinguish same code execution with and without pause.
     */
    private void noPause() {
    }

    private void pause(int delay) {
        pause = pause.initiateWaiter(delay);
        pause.waitForIt();
    }

}

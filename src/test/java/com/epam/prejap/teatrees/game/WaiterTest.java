package com.epam.prejap.teatrees.game;

import java.util.stream.IntStream;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WaiterTest {
    /**
     * Test waiter delay after calling decreaseCycleDelay().
     * @param waiter {@link Waiter} object to be tested.
     * @param numberOfFuntionCalls number of {@link Waiter#decreaseCycleDelay(int)} calls.
     * @param expectedTime expected {@link Waiter#waitForIt()} execution time.
     * @param possibleError measurement error.
     */
    @Test(dataProvider = "waiter")
    public void testCycleDelay(Waiter waiter, int numberOfFuntionCalls, int expectedTime, int possibleError) {

        IntStream.range(0, numberOfFuntionCalls).forEach(x -> waiter.decreaseCycleDelay(numberOfFuntionCalls * 10));

        long start = System.currentTimeMillis();
        waiter.waitForIt();
        long stop = System.currentTimeMillis();
        long actualTime = stop - start;

        Assert.assertTrue(Math.abs(actualTime - expectedTime) < possibleError);
    }

    @DataProvider
    public static Object[][] waiter() {
        return new Object[][] {
                { new Waiter(500), 0, 500, 10 },
                { new Waiter(500), 1, 400, 10 },
                { new Waiter(500), 2, 300, 10 },
                { new Waiter(500), 3, 200, 10 },
                { new Waiter(500), 4, 100, 10 },
                { new Waiter(500), 5, 100, 10 }
        };
    }
}

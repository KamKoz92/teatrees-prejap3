package com.epam.prejap.teatrees.game;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.stream.IntStream;

import static org.testng.Assert.assertTrue;

public class WaiterTest {
    private final int POSSIBLE_ERROR = 20;

    @Test(dataProvider = "waiter", groups = "waiting")
    public void testCycleDelay(int numberOfFunctionCalls, int expectedTime) {
        Waiter waiter = new Waiter(500);
        IntStream.range(0, numberOfFunctionCalls)
                .forEach(x -> waiter.decreaseCycleDelay(numberOfFunctionCalls * 10));

        long start = System.currentTimeMillis();
        waiter.waitForIt();
        long stop = System.currentTimeMillis();
        long actualTime = stop - start;

        assertTrue(Math.abs(actualTime - expectedTime) < POSSIBLE_ERROR);
    }

    @DataProvider
    public static Object[][] waiter() {
        return new Object[][]{
                {0, 500},
                {1, 400},
                {2, 300},
                {3, 200},
                {4, 100},
                {5, 100}
        };
    }
}

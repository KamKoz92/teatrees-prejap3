package com.epam.prejap.teatrees.game;

import java.util.stream.IntStream;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WaiterTest {
    private final int POSSIBLE_ERROR = 10;

    @Test(dataProvider = "waiter")
    public void testCycleDelay(int numberOfFuntionCalls, int expectedTime) {
        Waiter waiter = new Waiter(500);
        IntStream.range(0, numberOfFuntionCalls).forEach(x -> waiter.decreaseCycleDelay(numberOfFuntionCalls * 10));

        long start = System.currentTimeMillis();
        waiter.waitForIt();
        long stop = System.currentTimeMillis();
        long actualTime = stop - start;

        assertTrue(Math.abs(actualTime - expectedTime) < POSSIBLE_ERROR);
    }

    @DataProvider
    public static Object[][] waiter() {
        return new Object[][] {
                {0, 500 },
                {1, 400 },
                {2, 300 },
                {3, 200 },
                {4, 100 },
                {5, 100 }
        };
    }
}

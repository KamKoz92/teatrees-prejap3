package com.epam.prejap.teatrees.block;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Mariusz Bal
 */
@Test(groups = "blocks")
public class LBlockTest {
    @DataProvider
    public static Object[][] lBlockCoordinates() {
        return new Object[][]{
                {0, 0, 1},
                {0, 1, 0},
                {1, 0, 1},
                {1, 1, 0},
                {2, 0, 1},
                {2, 1, 1},
        };
    }

    public void shouldHaveProperAmountOfRows() {
        int expectedRows = 3;
        LBlock lBlock = new LBlock();
        int actualRows = lBlock.rows();
        assertEquals(actualRows, expectedRows,
                String.format("The L block should have %d rows", expectedRows));
    }

    public void shouldHaveProperAmountOfColumns() {
        int expectedColumns = 2;
        LBlock lBlock = new LBlock();
        int actualColumns = lBlock.cols();
        assertEquals(actualColumns, expectedColumns,
                String.format("The L block should have %d columns", expectedColumns));
    }

    @Test(dataProvider = "lBlockCoordinates")
    public void shouldHaveProperValueAtMatrixCoordinates(int row, int column, int expectedValue) {
        LBlock lBlock = new LBlock();
        byte actualValue = lBlock.dotAt(row, column);
        assertEquals(actualValue, expectedValue,
                String.format("The L block should have a %d at coordinates (%d, %d)", expectedValue, row, column));
    }
}

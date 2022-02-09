package com.epam.prejap.teatrees.block;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * HBlock tests based on @Jovhar Isayev {@link ZBlockTest}
 *
 * @author Mariusz Bal
 */
@Test(groups = "blocks")
public class HBlockTest {
    @DataProvider
    public static Object[][] hBlockCoordinates() {
        return new Object[][]{
                {0, 0, 1},
                {0, 1, 0},
                {0, 2, 1},
                {1, 0, 1},
                {1, 1, 1},
                {1, 2, 1},
                {2, 0, 1},
                {2, 1, 0},
                {2, 2, 1},
        };
    }

    public void shouldHaveProperAmountOfRows() {
        int expectedRows = 3;
        HBlock hBlock = new HBlock();
        int actualRows = hBlock.rows();
        assertEquals(actualRows, expectedRows,
                String.format("The H block should have %d rows", expectedRows));
    }

    public void shouldHaveProperAmountOfColumns() {
        int expectedColumns = 3;
        HBlock hBlock = new HBlock();
        int actualColumns = hBlock.cols();
        assertEquals(actualColumns, expectedColumns,
                String.format("The H block should have %d columns", expectedColumns));
    }

    @Test(dataProvider = "hBlockCoordinates")
    public void shouldHaveProperValueAtMatrixCoordinates(int row, int column, int expectedValue) {
        HBlock hBlock = new HBlock();
        byte actualValue = hBlock.dotAt(row, column);
        assertEquals(actualValue, expectedValue,
                String.format("The H block should have a %d at coordinates (%d, %d)", expectedValue, row, column));
    }
}

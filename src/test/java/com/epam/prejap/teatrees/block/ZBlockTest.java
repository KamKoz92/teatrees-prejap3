package com.epam.prejap.teatrees.block;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test(groups = "blocks")
public class ZBlockTest {
    @DataProvider
    public static Object[][] provideCoordinatesOfValuesForZShape() {
        return new Object[][]{
                {0, 0, 1},
                {0, 1, 1},
                {0, 2, 0},
                {1, 0, 0},
                {1, 1, 1},
                {1, 2, 1},
        };
    }

    public void shouldHaveExpectedColumnSize() {
        // given
        ZBlock zBlock = new ZBlock();
        int expectedColumnSize = 3;
        // when
        int colSize = zBlock.cols();
        // then
        assertEquals(colSize, expectedColumnSize);
    }

    public void shouldHaveExpectedRowSize() {
        // given
        ZBlock zBlock = new ZBlock();
        int expectedRowSize = 2;
        // when
        int rowSize = zBlock.rows();
        // then
        assertEquals(rowSize, expectedRowSize);
    }

    @Test(dataProvider = "provideCoordinatesOfValuesForZShape")
    public void shouldHaveExpectedValuesAtCoordinatesFromDP(int rowIndex, int colIndex, int expectedValue) {
        // given
        ZBlock zBlock = new ZBlock();
        // when
        byte actualValue = zBlock.dotAt(rowIndex, colIndex);
        // then
        assertEquals(actualValue, expectedValue);
    }
}

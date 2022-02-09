package com.epam.prejap.teatrees.block;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test(groups = "blocks")
public class YBlockTest {
    @DataProvider
    public static Object[][] provideCoordinatesOfValuesForYShape() {
        return new Object[][]{
                {0, 0, 1},
                {0, 1, 0},
                {0, 2, 1},
                {1, 0, 0},
                {1, 1, 1},
                {1, 2, 0},
                {2, 0, 0},
                {2, 1, 1},
                {2, 2, 0},
        };
    }

    public void shouldHaveExpectedColumnSize() {
        // given
        YBlock yBlock = new YBlock();
        int expectedValue = 3;
        // when
        int colSize = yBlock.cols();
        // then
        assertEquals(colSize, expectedValue);
    }

    public void shouldHaveExpectedRowSize() {
        // given
        YBlock yBlock = new YBlock();
        int expectedValue = 3;
        // when
        int rowSize = yBlock.rows();
        // then
        assertEquals(rowSize, expectedValue);
    }

    @Test(dataProvider = "provideCoordinatesOfValuesForYShape")
    public void shouldHaveExpectedValuesAtGivenCoordinates(int rowIndex, int colIndex, int expectedValue) {
        // given
        YBlock yBlock = new YBlock();
        // when
        byte actualValue = yBlock.dotAt(rowIndex, colIndex);
        // then
        assertEquals(actualValue, expectedValue);
    }
}

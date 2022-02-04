package com.epam.prejap.teatrees.game;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(groups = "grid")
public class GridTest {

    @Test(dataProvider = "gridsWithoutCompleteLines")
    public void shouldNotChangeGrid(byte[][] initGrid, byte[][] expected) {
        // Given
        Grid grid = new Grid(initGrid);
        // When
        grid.removeCompleteLines();
        // Then
        assertEquals(grid, new Grid(expected));
    }

    @Test(dataProvider = "gridsWithCompleteLines")
    public void shouldRemoveCompleteLines(byte[][] initGrid, byte[][] expected) {
        // Given
        Grid grid = new Grid(initGrid);
        // When
        grid.removeCompleteLines();
        // Then
        assertEquals(grid, new Grid(expected));
    }

    @Test(dataProvider = "dataForIsEmptyCell")
    public void shouldReturnTrueForEmptyCellFalseOtherwise(int[] indexes, boolean expected) {
        // Given
        byte[][] bytes = new byte[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                {1, 1, 0, 0, 1, 1, 1, 0, 1, 1},
                {1, 1, 0, 0, 1, 1, 1, 0, 1, 1}};
        Grid grid = new Grid(bytes);
        // When
        // Then
        assertEquals(grid.isCellEmpty(indexes[0], indexes[1]), expected);
    }

    //TODO: cleanCell
    @Test(dataProvider = "dataForCleanCell")
    public void shouldPassIfCellValueIsChangedTo0(int[] indexes, boolean expected) {
        // Given
        byte[][] bytes = new byte[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                {1, 1, 0, 0, 1, 1, 1, 0, 1, 1},
                {1, 1, 0, 0, 1, 1, 1, 0, 1, 1}};
        Grid grid = new Grid(bytes);
        // When
        grid.cleanCell(indexes[0], indexes[1]);
        // Then
        assertEquals(grid.isCellEmpty(indexes[0], indexes[1]), expected);
    }

    //TODO: fillCell
    @Test(dataProvider = "dataForFillCell")
    public void shouldPassIfCellValueIsChangedToDot(int[] indexesAndDot, boolean expected) {
        // Given
        byte[][] bytes = new byte[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                {1, 1, 0, 0, 1, 1, 1, 0, 1, 1},
                {1, 1, 0, 0, 1, 1, 1, 0, 1, 1}};
        Grid grid = new Grid(bytes);
        // When
        grid.cleanCell(indexesAndDot[0], indexesAndDot[1]);
        // Then
        assertEquals(grid.isCellEmpty(indexesAndDot[0], indexesAndDot[1]), expected);
    }

    @DataProvider
    private static Object[][] gridsWithoutCompleteLines() {
        return new Object[][]{
                new Object[]{
                        new byte[][]{
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0}
                        },
                        new byte[][]{
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0}
                        }
                },
                new Object[]{
                        new byte[][]{
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                {1, 1, 0, 1, 1, 0, 0, 0, 0, 0},
                                {1, 1, 0, 1, 1, 0, 0, 0, 0, 0}
                        },
                        new byte[][]{
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                {1, 1, 0, 1, 1, 0, 0, 0, 0, 0},
                                {1, 1, 0, 1, 1, 0, 0, 0, 0, 0}
                        }
                }
        };
    }

    @DataProvider
    private static Object[][] gridsWithCompleteLines() {
        return new Object[][]{
                new Object[]{
                        new byte[][]{
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                                {1, 1, 0, 0, 1, 1, 1, 0, 1, 1},
                                {1, 1, 0, 0, 1, 1, 1, 0, 1, 1}
                        },
                        new byte[][]{
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                                {1, 1, 0, 0, 1, 1, 1, 0, 1, 1},
                                {1, 1, 0, 0, 1, 1, 1, 0, 1, 1}
                        }
                },
                new Object[]{
                        new byte[][]{
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
                        },
                        new byte[][]{
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0}
                        }
                },
                new Object[]{
                        new byte[][]{
                                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                                {1, 1, 0, 0, 1, 1, 1, 0, 1, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                {1, 1, 0, 0, 1, 1, 1, 0, 1, 1}
                        },
                        new byte[][]{
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                                {0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                                {1, 1, 0, 0, 1, 1, 1, 0, 1, 1},
                                {1, 1, 0, 0, 1, 1, 1, 0, 1, 1}
                        }
                }
        };
    }

    @DataProvider
    private static Object[][] dataForIsEmptyCell() {
        return new Object[][]{
                {new int[] {1, 0}, true}, {new int[] {4, 2}, true}, {new int[] {0, 0}, false}, {new int[] {4, 1}, false}
        };
    }

    @DataProvider
    private static Object[][] dataForCleanCell() {
        return new Object[][]{
                {new int[] {1, 0}, true}, {new int[] {4, 2}, true}, {new int[] {0, 0}, true}, {new int[] {4, 1}, true}
        };
    }

    @DataProvider
    private static Object[][] dataForFillCell() {
        return new Object[][]{
                {new int[] {1, 0, 1}, true}, {new int[] {4, 2, 1}, true}
        };
    }
}

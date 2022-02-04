package com.epam.prejap.teatrees.block;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Random;

import static org.testng.Assert.assertEquals;

@Test(groups = "blocks")
public class RotatedBlockTest {
    private void assertEqualsBlocks(Block actual, Block expected) {
        assertEquals(actual.rows(), expected.rows());
        assertEquals(actual.cols(), expected.cols());

        SoftAssert sa = new SoftAssert();
        for (int i = 0; i < actual.rows(); i++)
            for (int j = 0; j < actual.cols(); j++) {
                sa.assertEquals(actual.dotAt(i, j), expected.dotAt(i, j),
                        String.format("Dot mismatch for [%d][%d]: actual = %d, expected = %d",
                                i, j, actual.dotAt(i, j), expected.dotAt(i, j)));
            }
        sa.assertAll();
    }

    @Test
    public void rotationDoesNotModifyOriginalBlock() {
        // given
        Block testBlock = new ZBlock();
        Block baseBlock = new ZBlock();
        // when
        new RotatedBlock(testBlock);
        // then
        assertEqualsBlocks(testBlock, baseBlock);
    }

    private static class MockedBlock extends Block {
        MockedBlock(byte[][] dots) {
            super(dots);
        }
    }

    private static final MockedBlock line   = new MockedBlock(new byte[][]{{1, 1, 1, 1}});
    private static final MockedBlock axe    = new MockedBlock(new byte[][]{{1, 0, 1, 0},
                                                                           {0, 1, 0, 0},
                                                                           {1, 1, 1, 1}});
    private static final MockedBlock colors = new MockedBlock(new byte[][]{{1, 2, 3},
                                                                           {4, 5, 6}});

    @DataProvider
    public static Object[][] rotatableBlocks() {
        return new Object[][]{
                { new OBlock() },
                { new ZBlock() },
                { new RotatedBlock(new ZBlock()) },
                { line },
                { axe },
                { colors },
        };
    }

    @Test(dataProvider = "rotatableBlocks")
    public void rotatedRowsEqualsInitialCols(Block block) {
        // given
        int initialCols = block.cols();
        // when
        int rotatedRows = new RotatedBlock(block).rows();
        // then
        assertEquals(rotatedRows, initialCols);
    }

    @Test(dataProvider = "rotatableBlocks")
    public void rotatedColsEqualsInitialRows(Block block) {
        // given
        int initialRows = block.rows();
        // when
        int rotatedCols = new RotatedBlock(block).cols();
        // then
        assertEquals(rotatedCols, initialRows);
    }

    @DataProvider
    public static Object[][] rotatableBlocksWithRotations() {
        return new Object[][]{
                { new OBlock(), new OBlock() },
                { new ZBlock(), new MockedBlock(new byte[][]{{0, 1}, {1, 1}, {1, 0}}) },
                { new RotatedBlock(new ZBlock()), new ZBlock() },
                { line,   new MockedBlock(new byte[][]{{1}, {1}, {1}, {1}, {1}}) },
                { axe,    new MockedBlock(new byte[][]{{1, 0, 1}, {1, 1, 0}, {1, 0, 1}, {1, 0, 0}}) },
                { colors, new MockedBlock(new byte[][]{{4, 1}, {5, 2}, {6, 3}})},
        };
    }

    @Test(dataProvider = "rotatableBlocks")
    public void fourRotationsTogetherEqualsInitial(Block block) {
        // given
        Block testBlock = block;
        // when
        for(int i = 0; i < 4; ++i)
            testBlock = new RotatedBlock(testBlock);
        // then
        assertEqualsBlocks(testBlock, block);
    }

    private static class RandomBlock extends Block {
        RandomBlock() {
            super(randomDots());
        }

        private static byte[][] randomDots() {
            Random rand = new Random();
            int rows = rand.nextInt(1, 6);
            int cols = rand.nextInt(1, 6);
            byte[][] dots = new byte[rows][cols];
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    dots[i][j] = (byte)rand.nextInt(5);
            return dots;
        }
    }

    @Test(dependsOnMethods = "fourRotationsTogetherEqualsInitial")
    public void fourRotationsTogetherEqualsInitialRandom() {
        for(int i = 0; i < 1000; ++i)
            fourRotationsTogetherEqualsInitial(new RandomBlock());
    }
}

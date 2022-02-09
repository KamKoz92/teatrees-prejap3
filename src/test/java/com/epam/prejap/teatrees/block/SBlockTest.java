package com.epam.prejap.teatrees.block;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(groups = "blocks")
public class SBlockTest {

    private static final int EXPECTED_HEIGHT = 2;
    private static final int EXPECTED_WIDTH = 3;
    private static final byte[][] EXPECTED_DOTS = new byte[][] {{0, 1, 1}, {1, 1, 0}};

    private SBlock sBlock;

    @BeforeTest
    void setup() {
        sBlock = new SBlock();
    }

    public void shouldHaveProperWidth() {
        assertEquals(sBlock.cols(), EXPECTED_WIDTH);
    }

    public void shouldHaveProperHeight() {
        assertEquals(sBlock.rows(), EXPECTED_HEIGHT);
    }

    public void shouldHaveProperShape() {
        assertEquals(recreateBlockShapeFromIndividualDots(sBlock), EXPECTED_DOTS);
    }

    private byte[][] recreateBlockShapeFromIndividualDots(SBlock sBlock) {
        var shape = new byte [sBlock.rows()][sBlock.cols()];
        for(var i = 0; i < sBlock.rows(); ++i) {
            for (var j = 0; j < sBlock.cols(); ++j) {
                shape[i][j] = sBlock.dotAt(i, j);
            }
        }
        return shape;
    }
}

package com.epam.prejap.teatrees.game;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import com.epam.prejap.teatrees.block.BlockFeed;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PlayfieldTest {
    private byte[][] expectedGridAfterFirstBlock;
    private byte[][] expectedGridAfterKeyDown;
    private byte[][] expectedGridAfterSecondBlock;
    private ByteArrayOutputStream consoleContent = new ByteArrayOutputStream();

    @BeforeTest
    void BeforeTest() {
        expectedGridAfterFirstBlock = new byte[][] {
                { 0, 0, 1, 1, 0, 0, },
                { 0, 0, 1, 1, 0, 0, },
                { 0, 0, 0, 0, 0, 0, },
                { 0, 0, 0, 0, 0, 0, },
                { 0, 0, 0, 0, 0, 0, },
        };
        expectedGridAfterKeyDown = new byte[][] {
                { 0, 0, 0, 0, 0, 0, },
                { 0, 0, 0, 0, 0, 0, },
                { 0, 0, 0, 0, 0, 0, },
                { 0, 0, 1, 1, 0, 0, },
                { 0, 0, 1, 1, 0, 0, },
        };
        expectedGridAfterSecondBlock = new byte[][] {
                { 0, 0, 1, 1, 0, 0, },
                { 0, 0, 1, 1, 0, 0, },
                { 0, 0, 0, 0, 0, 0, },
                { 0, 0, 1, 1, 0, 0, },
                { 0, 0, 1, 1, 0, 0, },
        };
    }

    @Test(groups = "bottomMove")
    public void testBottomMove() {
        Playfield playfield = new Playfield(5, 6, new BlockFeed(new MyRandom()),
                new Printer(new PrintStream(this.consoleContent)));
        SoftAssert sa = new SoftAssert();

        playfield.nextBlock();
        sa.assertEquals(playfield.grid, expectedGridAfterFirstBlock);

        playfield.downArrowPressed = true;
        playfield.move(Move.NONE);
        sa.assertEquals(playfield.grid, expectedGridAfterKeyDown);

        playfield.nextBlock();
        sa.assertEquals(playfield.grid, expectedGridAfterSecondBlock);

        sa.assertAll();
    }

    class MyRandom extends Random {
        @Override
        public int nextInt(int i) {
            return 0;
        }
    }

}

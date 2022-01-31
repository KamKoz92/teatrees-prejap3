package com.epam.prejap.teatrees.game;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.epam.prejap.teatrees.block.Block;
import com.epam.prejap.teatrees.block.BlockFeed;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PlayFieldTest {
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


    @Test(groups = "bottom-move-test")
    public void testBottomMove() {
        Playfield playfield = new Playfield(5, 6, new TestBlockFeed(), new Printer(new PrintStream(this.consoleContent)));
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

    class TestBlockFeed extends BlockFeed {
        @Override
        public Block nextBlock() {
            return blocks.get(0).get();
        }
    }

}

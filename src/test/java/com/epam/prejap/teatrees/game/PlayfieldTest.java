package com.epam.prejap.teatrees.game;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.stream.IntStream;

import com.epam.prejap.teatrees.block.BlockFeed;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PlayfieldTest {
    private Integer[] expectedGridAfterFirstBlock;
    private Integer[] expectedGridAfterKeyDown;
    private Integer[] expectedGridAfterSecondBlock;
    private ByteArrayOutputStream consoleContent = new ByteArrayOutputStream();

    PlayfieldTest() {
        initializeGridsForBottomMove();
    }

    void initializeGridsForBottomMove() {
        expectedGridAfterFirstBlock = new Integer[] {
                0, 0, 1, 1, 0, 0,
                0, 0, 1, 1, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0 };
        expectedGridAfterKeyDown = new Integer[] {
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 1, 1, 0, 0,
                0, 0, 1, 1, 0, 0 };
        expectedGridAfterSecondBlock = new Integer[] {
                0, 0, 1, 1, 0, 0,
                0, 0, 1, 1, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 1, 1, 0, 0,
                0, 0, 1, 1, 0, 0 };
    }

    @Test(groups = "bottomMove")
    public void testBottomMove() {
        Playfield playfield = new Playfield(5, 6, new BlockFeed(new MyRandom()),
                new Printer(new PrintStream(this.consoleContent)));
        SoftAssert sa = new SoftAssert();

        playfield.nextBlock();
        byte[] arrayAfterFirstBlock = this.consoleContent.toByteArray();
        Integer[] outAfterFirstBlock = IntStream.range(0, arrayAfterFirstBlock.length)
                .mapToObj(b -> arrayAfterFirstBlock[b])
                .filter(f -> f == ' ' || f == '#').map(i -> (i == ' ' ? 0 : 1)).toArray(Integer[]::new);
        sa.assertEquals(outAfterFirstBlock, expectedGridAfterFirstBlock);

        this.consoleContent.reset();
        playfield.downArrowPressed = true;
        playfield.move(Move.NONE);
        byte[] arrayAfterKeyDown = this.consoleContent.toByteArray();
        Integer[] outAfterKeyDown = IntStream.range(0, arrayAfterKeyDown.length).mapToObj(b -> arrayAfterKeyDown[b])
                .filter(f -> f == ' ' || f == '#').map(i -> (i == ' ' ? 0 : 1)).toArray(Integer[]::new);
        sa.assertEquals(outAfterKeyDown, expectedGridAfterKeyDown);

        this.consoleContent.reset();
        playfield.nextBlock();
        byte[] arrayAfterSecondBlock = this.consoleContent.toByteArray();
        Integer[] outAfterSecondBlock = IntStream.range(0, arrayAfterSecondBlock.length)
                .mapToObj(b -> arrayAfterSecondBlock[b])
                .filter(f -> f == ' ' || f == '#').map(i -> (i == ' ' ? 0 : 1)).toArray(Integer[]::new);
        sa.assertEquals(outAfterSecondBlock, expectedGridAfterSecondBlock);

        sa.assertAll();
    }

    class MyRandom extends Random {
        @Override
        public int nextInt(int i) {
            return 0;
        }
    }
}

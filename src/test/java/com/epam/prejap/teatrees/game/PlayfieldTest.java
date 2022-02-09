package com.epam.prejap.teatrees.game;

import static org.testng.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.stream.IntStream;

import com.epam.prejap.teatrees.block.Block;
import com.epam.prejap.teatrees.block.BlockFeed;
import com.epam.prejap.teatrees.block.MockedBlock;
import com.epam.prejap.teatrees.block.RotatedBlock;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Test
public class PlayfieldTest {

        private final Class<Playfield> playfieldClass = Playfield.class;

        private Playfield createPlayfield(byte[][] grid) {
                Printer printer = new Printer(new PrintStream(new ByteArrayOutputStream()));
                BlockFeed blockFeed = new BlockFeed(new Random());
                Playfield playfield = new Playfield(grid.length, grid[0].length, blockFeed, printer);
                for (int i = 0; i < grid.length; ++i)
                        for (int j = 0; j < grid[0].length; ++j)
                                playfield.grid.fillCell(i, j, grid[i][j]);
                return playfield;
        }

        private Block mockedZBlock() {
                return new MockedBlock(new byte[][] { { 1, 1, 0 },
                                { 0, 1, 1 } });
        }

        public void rotateBlockInEmptySpace() {
                // given
                var playfieldGrid = new byte[][] { { 0, 0, 0, 0, 0 }, // {0, 0, 0, 0, 0}
                                { 0, 0, 0, 0, 0 }, // {0, 1, 1, 0, 0}
                                { 0, 0, 0, 0, 0 }, // {0, 0, 1, 1, 0}
                                { 0, 0, 0, 0, 0 }, // {0, 0, 0, 0, 0}
                                { 0, 0, 0, 0, 0 } }; // {0, 0, 0, 0, 0}

                var expectedGrid = new Grid(new byte[][] { { 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 0 },
                                { 0, 0, 1, 0, 0 },
                                { 0, 1, 1, 0, 0 },
                                { 0, 1, 0, 0, 0 } });

                Block block = mockedZBlock();

                Playfield playfield = createPlayfield(playfieldGrid);
                playfield.block = block;
                playfield.row = 1;
                playfield.rowShadow = 3;
                playfield.col = 1;

                // when
                boolean movedDown = playfield.move(Move.UP);

                // then
                SoftAssert sa = new SoftAssert();
                sa.assertTrue(movedDown);
                sa.assertEquals(playfield.row, 2);
                sa.assertEquals(playfield.rowShadow, 2);
                sa.assertEquals(playfield.col, 1);
                sa.assertTrue(playfield.block instanceof RotatedBlock);
                sa.assertEquals(playfield.grid, expectedGrid);
                sa.assertAll();
        }

        public void rotateBlockCloseToBottom() {
                // given
                var playfieldGrid = new byte[][] { { 0, 0, 0, 0, 0 }, // {0, 0, 0, 0, 0}
                                { 0, 0, 0, 0, 0 }, // {0, 0, 0, 0, 0}
                                { 0, 0, 0, 0, 0 }, // {0, 1, 1, 0, 0}
                                { 0, 0, 0, 0, 0 }, // {0, 0, 1, 1, 0}
                                { 0, 0, 0, 0, 0 } }; // {0, 0, 0, 0, 0}

                var expectedGrid = new Grid(new byte[][] { { 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 0 },
                                { 0, 0, 1, 0, 0 },
                                { 0, 1, 1, 0, 0 },
                                { 0, 1, 0, 0, 0 } });

                Block block = mockedZBlock();

                Playfield playfield = createPlayfield(playfieldGrid);
                playfield.block = block;
                playfield.row = 2;
                playfield.rowShadow = 3;
                playfield.col = 1;

                // when
                boolean movedDown = playfield.move(Move.UP);

                // then
                SoftAssert sa = new SoftAssert();
                sa.assertFalse(movedDown);
                sa.assertEquals(playfield.row, 2);
                sa.assertEquals(playfield.rowShadow, 2);
                sa.assertEquals(playfield.col, 1);
                sa.assertTrue(playfield.block instanceof RotatedBlock);
                sa.assertEquals(playfield.grid, expectedGrid);
                sa.assertAll();
        }

        public void rotateBlockCloseToLeftEdge() {
                // given
                var playfieldGrid = new byte[][] { { 0, 0, 0, 0, 0 }, // {1, 1, 0, 0, 0}
                                { 0, 0, 0, 0, 0 }, // {0, 1, 1, 0, 0}
                                { 0, 0, 0, 0, 0 }, // {0, 0, 0, 0, 0}
                                { 0, 0, 0, 0, 0 }, // {0, 0, 0, 0, 0}
                                { 0, 0, 0, 0, 0 } }; // {0, 0, 0, 0, 0}

                var expectedGrid = new Grid(new byte[][] { { 0, 0, 0, 0, 0 },
                                { 0, 1, 0, 0, 0 },
                                { 1, 1, 0, 0, 0 },
                                { 1, '.', 0, 0, 0 },
                                { '.', 0, 0, 0, 0 } });

                Block block = mockedZBlock();

                Playfield playfield = createPlayfield(playfieldGrid);
                playfield.block = block;
                playfield.row = 0;
                playfield.rowShadow = 3;
                playfield.col = 0;

                // when
                boolean movedDown = playfield.move(Move.UP);

                // then
                SoftAssert sa = new SoftAssert();
                sa.assertTrue(movedDown);
                sa.assertEquals(playfield.row, 1);
                sa.assertEquals(playfield.rowShadow, 2);
                sa.assertEquals(playfield.col, 0);
                sa.assertTrue(playfield.block instanceof RotatedBlock);
                sa.assertEquals(playfield.grid, expectedGrid);
                sa.assertAll();
        }

        public void rotateBlockInLimitedSpace() {
                // given
                var playfieldGrid = new byte[][] { { 2, 2, 2, 2, 2 }, // {2, 2, 2, 2, 2}
                                { 2, 0, 0, 2, 2 }, // {2, 1, 1, 2, 2}
                                { 2, 0, 0, 0, 2 }, // {2, 0, 1, 1, 2}
                                { 2, 0, 2, 2, 2 }, // {2, 0, 2, 2, 2}
                                { 2, 2, 2, 2, 2 } }; // {2, 2, 2, 2, 2}

                var expectedGrid = new Grid(new byte[][] { { 2, 2, 2, 2, 2 },
                                { 2, 0, 1, 2, 2 },
                                { 2, 1, 1, 0, 2 },
                                { 2, 1, 2, 2, 2 },
                                { 2, 2, 2, 2, 2 } });

                Block block = mockedZBlock();

                Playfield playfield = createPlayfield(playfieldGrid);
                playfield.block = block;
                playfield.row = 1;
                playfield.rowShadow = 1;
                playfield.col = 1;

                // when
                boolean movedDown = playfield.move(Move.UP);

                // then
                SoftAssert sa = new SoftAssert();
                sa.assertFalse(movedDown);
                sa.assertEquals(playfield.row, 1);
                sa.assertEquals(playfield.rowShadow, 1);
                sa.assertEquals(playfield.col, 1);
                sa.assertTrue(playfield.block instanceof RotatedBlock);
                sa.assertEquals(playfield.grid, expectedGrid);
                sa.assertAll();
        }

        public void rotateBlockTooCloseToBottom() {
                // given
                var playfieldGrid = new byte[][] { { 0, 0, 0, 0, 0 }, // {0, 0, 0, 0, 0}
                                { 0, 0, 0, 0, 0 }, // {0, 0, 0, 0, 0}
                                { 0, 0, 0, 0, 0 }, // {0, 0, 0, 0, 0}
                                { 0, 0, 0, 0, 0 }, // {0, 1, 1, 0, 0}
                                { 0, 0, 0, 0, 0 } }; // {0, 0, 1, 1, 0}

                var expectedGrid = new Grid(new byte[][] { { 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 0 },
                                { 0, 1, 1, 0, 0 },
                                { 0, 0, 1, 1, 0 } });

                Block block = mockedZBlock();

                Playfield playfield = createPlayfield(playfieldGrid);
                playfield.block = block;
                playfield.row = 3;
                playfield.rowShadow = 3;
                playfield.col = 1;

                // when
                boolean movedDown = playfield.move(Move.UP);

                // then
                SoftAssert sa = new SoftAssert();
                sa.assertFalse(movedDown);
                sa.assertEquals(playfield.row, 3);
                sa.assertEquals(playfield.rowShadow, 3);
                sa.assertEquals(playfield.col, 1);
                sa.assertSame(playfield.block, block);
                sa.assertEquals(playfield.grid, expectedGrid);
                sa.assertAll();
        }

        public void rotateBlockTooCloseToRightEdge() {
                // given
                var playfieldGrid = new byte[][] { { 0, 0, 0, 0, 0 }, // {0, 0, 0, 0, 0}
                                { 0, 0, 0, 0, 0 }, // {0, 0, 0, 0, 1}
                                { 0, 0, 0, 0, 0 }, // {0, 0, 0, 1, 1}
                                { 0, 0, 0, 0, 0 }, // {0, 0, 0, 1, 0}
                                { 0, 0, 0, 0, 0 } }; // {0, 0, 0, 0, 0}

                var expectedGrid = new Grid(new byte[][] { { 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 1 },
                                { 0, 0, 0, 1, 1 },
                                { 0, 0, 0, 1, 0 } });

                Block block = new RotatedBlock(mockedZBlock());

                Playfield playfield = createPlayfield(playfieldGrid);
                playfield.block = block;
                playfield.row = 1;
                playfield.rowShadow = 2;
                playfield.col = 3;

                // when
                boolean movedDown = playfield.move(Move.UP);

                // then
                SoftAssert sa = new SoftAssert();
                sa.assertTrue(movedDown);
                sa.assertEquals(playfield.row, 2);
                sa.assertEquals(playfield.rowShadow, 2);
                sa.assertEquals(playfield.col, 3);
                sa.assertSame(playfield.block, block);
                sa.assertEquals(playfield.grid, expectedGrid);
                sa.assertAll();
        }

        public void rotateBlockImpossible() {
                // given
                var playfieldGrid = new byte[][] { { 2, 2, 2, 2, 2 }, // {2, 2, 2, 2, 2}
                                { 2, 0, 0, 2, 2 }, // {2, 1, 1, 2, 2}
                                { 2, 0, 0, 0, 2 }, // {2, 0, 1, 1, 2}
                                { 2, 2, 2, 2, 2 }, // {2, 0, 2, 2, 2}
                                { 2, 2, 2, 2, 2 } }; // {2, 2, 2, 2, 2}

                var expectedGrid = new Grid(new byte[][] { { 2, 2, 2, 2, 2 },
                                { 2, 1, 1, 2, 2 },
                                { 2, 0, 1, 1, 2 },
                                { 2, 2, 2, 2, 2 },
                                { 2, 2, 2, 2, 2 } });

                Block block = mockedZBlock();

                Playfield playfield = createPlayfield(playfieldGrid);
                playfield.block = block;
                playfield.row = 1;
                playfield.rowShadow = 1;
                playfield.col = 1;

                // when
                boolean movedDown = playfield.move(Move.UP);

                // then
                SoftAssert sa = new SoftAssert();
                sa.assertFalse(movedDown);
                sa.assertEquals(playfield.row, 1);
                sa.assertEquals(playfield.rowShadow, 1);
                sa.assertEquals(playfield.col, 1);
                sa.assertSame(playfield.block, block);
                sa.assertEquals(playfield.grid, expectedGrid);
                sa.assertAll();
        }

        @Test(groups = "nextBlock")
        public void shouldHintBlockBecomeCurrentlyPlayedBlock() throws IllegalAccessException {
                // given
                Playfield playfield = new Playfield(10, 10, new BlockFeed(new Random()), new FakePrinter());
                Object hintBlock = getBlock(playfield, "hintBlock");

                // when
                playfield.nextBlock();

                // then
                assertEquals(getBlock(playfield, "block"), hintBlock);
        }

        private Object getBlock(Playfield playfield, String block) throws IllegalAccessException {
                Object hintBlock = null;
                Field[] fields = playfieldClass.getFields();
                for (Field field : fields) {
                        if (field.getName().equals(block)) {
                                field.setAccessible(true);
                                hintBlock = field.get(playfield);
                        }
                }
                return hintBlock;
        }

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
                playfield.move(Move.NONE);
                byte[] arrayAfterKeyDown = this.consoleContent.toByteArray();
                Integer[] outAfterKeyDown = IntStream.range(0, arrayAfterKeyDown.length)
                                .mapToObj(b -> arrayAfterKeyDown[b])
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

/**
 * It's used only in shouldHintBlockBecomeCurrentlyPlayedBlock test.
 * Tested method Playfield::nextBlock starts a method call chain that ends with
 * Printer::draw. Printing is outside
 * the scope of this test thus instance of this class is passed to Playfield's
 * constructor.
 *
 * @see PlayfieldTest#shouldHintBlockBecomeCurrentlyPlayedBlock()
 * @see Playfield#nextBlock()
 * @see Printer#draw(byte[][], Block)
 */
class FakePrinter extends Printer {
        FakePrinter() {
                super(System.out);
        }

        @Override
        void draw(byte[][] grid, Block hintBlock) {
        }
}

package com.epam.prejap.teatrees.block;

/**
 * Frame for Y shaped tetris block.
 *
 * @author Jovhar Isayev
 * @see Block
 */
final class YBlock extends Block {
    private static final byte[][] IMAGE = new byte[][]{
            {1, 0, 1},
            {0, 1, 0},
            {0, 1, 0}
    };

    YBlock() {
        super(IMAGE);
    }
}

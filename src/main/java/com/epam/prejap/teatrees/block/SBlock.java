package com.epam.prejap.teatrees.block;

/**
 * Represents Tetris S block.
 *
 * @author Dominik Żebracki
 * @see Block
 */
final class SBlock extends Block {

    private static final byte[][] SBLOCK_IMAGE = {
        {0, 1, 1},
        {1, 1, 0},
    };

    public SBlock() {
        super(SBLOCK_IMAGE);
    }
}

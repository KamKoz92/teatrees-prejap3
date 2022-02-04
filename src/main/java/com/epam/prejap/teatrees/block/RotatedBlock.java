package com.epam.prejap.teatrees.block;

/**
 * Clockwise rotation for blocks in Tetris game.
 *
 * @author Krzysztof Janas
 * @see Block
 */
public final class RotatedBlock extends Block {
    /**
     * Create new Block that is rotated version of given block.
     *
     * The given block is not modified.
     *
     * @param block to rotate
     */
    public RotatedBlock(Block block) {
        super(rotatedDots(block));
    }

    private static byte[][] rotatedDots(Block block) {
        byte[][] dots = new byte[block.cols()][block.rows()];
        for (int i = 0; i < block.cols(); i++)
            for (int j = 0; j < block.rows(); j++)
                dots[i][j] = block.dotAt(block.rows - j - 1, i);
        return dots;
    }
}

package com.epam.prejap.teatrees.block;

/**
 * L block representation
 *
 * @author Mariusz Bal
 */
final class LBlock extends Block {

    private static final byte[][] IMAGE = {
            {1, 0},
            {1, 0},
            {1, 1},
    };

    LBlock() {
        super(IMAGE);
    }
}

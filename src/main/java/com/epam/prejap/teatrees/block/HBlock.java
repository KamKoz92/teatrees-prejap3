package com.epam.prejap.teatrees.block;

/**
 * H block representation
 *
 * @author Mariusz Bal
 */
final class HBlock extends Block {

    private static final byte[][] IMAGE = {
            {1, 0, 1},
            {1, 1, 1},
            {1, 0, 1},
    };

    HBlock() {
        super(IMAGE);
    }
}

package com.epam.prejap.teatrees.block;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class BlockFeed implements BlockSupplier {

    private final Random rnd;
    private final List<Supplier<Block>> blocks = List.of(
            HBlock::new, OBlock::new, ZBlock::new, SBlock::new, YBlock::new, LBlock::new
    );

    public BlockFeed(Random random) {
        this.rnd = random;
    }

    @Override
    public Block nextBlock() {
        return blocks.get(rnd.nextInt(blocks.size())).get();
    }
}

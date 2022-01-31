package com.epam.prejap.teatrees.player;

import com.epam.prejap.teatrees.game.Move;

import java.util.Optional;
import java.util.random.RandomGenerator;

/**
 * Represents a player in an automated game. The player's movements are randomly generated.
 *
 * @see Player
 */
public class RandomPlayer implements Player {

    private RandomGenerator random;

    /**
     * Instantiate a new <code>RandomPlayer</code>
     *
     * @param random
     * @return instance of this class
     */
    public RandomPlayer(RandomGenerator random) {
        this.random = random;
    }

    /**
     * Generates Optional of Move, randomly selected from the available move values.
     *
     * @return Optional of Move
     * @see Move
     * @see Player#nextMove()
     */
    @Override
    public Optional<Move> nextMove() {
        return Optional.of(Move.values()[random.nextInt(Move.values().length)]);
    }
}

package com.epam.prejap.teatrees.player;

import com.epam.prejap.teatrees.game.Move;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.testng.Assert.assertTrue;

public class RandomPlayerTest {

    @Test(groups = "nextMove")
    public void everyAvailableMoveCanBeMadeByRandomPlayer() {
        //Given
        RandomPlayer randomPlayer = new RandomPlayer(new MyRandom());
        int numberOfMoveVariants = Move.values().length;
        List<Move> randomPlayersMoves = new ArrayList<>();
        //When
        for (int i = 0; i < numberOfMoveVariants; i++) {
            randomPlayersMoves.add(randomPlayer.nextMove().orElseThrow());
        }
        //Then
        assertTrue(randomPlayersMoves.containsAll(List.of(Move.values())));
    }
}

class MyRandom extends Random {
    private int counter = -1;

    public int nextInt(int numberOfMoveVariants) {
        counter += 1;
        return counter >= numberOfMoveVariants ? 0 : counter;
    }
}

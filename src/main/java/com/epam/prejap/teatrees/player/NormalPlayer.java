package com.epam.prejap.teatrees.player;

import java.util.Optional;

import com.epam.prejap.teatrees.game.Move;
import com.epam.prejap.teatrees.keyboard.Key;
import com.epam.prejap.teatrees.keyboard.KeySubscriber;
import com.epam.prejap.teatrees.keyboard.KeyLoggerImp;

public class NormalPlayer implements Player, KeySubscriber {

    private Move nextMove = Move.NONE;

    public NormalPlayer() {
        KeyLoggerImp.getKeyLogger().subscribeForKeys(this, Key.VC_UP, Key.VC_LEFT, Key.VC_RIGHT);
    }

    @Override
    public void accept(Key key) {
        int keyCode = key.getKeyCode();
        switch (keyCode) {
            case Key.VC_UP -> nextMove = Move.UP;
            case Key.VC_LEFT -> nextMove = Move.LEFT;
            case Key.VC_RIGHT -> nextMove = Move.RIGHT;
        }
    }

    @Override
    public Optional<Move> nextMove() {
        Move tempMove = nextMove;
        nextMove = Move.NONE;
        return Optional.of(tempMove);
    }

}

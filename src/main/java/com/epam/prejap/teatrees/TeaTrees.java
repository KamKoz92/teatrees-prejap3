package com.epam.prejap.teatrees;

import java.io.InputStreamReader;
import java.util.Random;

import com.epam.prejap.teatrees.block.BlockFeed;
import com.epam.prejap.teatrees.game.Move;
import com.epam.prejap.teatrees.game.Playfield;
import com.epam.prejap.teatrees.game.Printer;
import com.epam.prejap.teatrees.game.Waiter;
import com.epam.prejap.teatrees.keyboard.ConsumerEvent;
import com.epam.prejap.teatrees.keyboard.Key;
import com.epam.prejap.teatrees.keyboard.KeyLogger;
import com.epam.prejap.teatrees.keyboard.KeyLoggerImp;
import com.epam.prejap.teatrees.pause.Pause;
import com.epam.prejap.teatrees.pause.PauseMonitor;
import com.epam.prejap.teatrees.player.Player;
import com.epam.prejap.teatrees.player.RandomPlayer;

class TeaTrees implements ConsumerEvent {

    private final Playfield playfield;
    private final Waiter waiter;
    private final Player player;
    private final PauseMonitor pauseMonitor;

    public TeaTrees(Playfield playfield, Waiter waiter, Player player, PauseMonitor pauseMonitor) {
        this.playfield = playfield;
        this.waiter = waiter;
        this.player = player;
        this.pauseMonitor = pauseMonitor;
    }

    public Score play() {
        boolean moved;
        int score = 0;
        do {
            moved = false;

            playfield.nextBlock();

            waiter.decreaseCycleDelay(++score);
            boolean nextMove;
            do {
                pauseMonitor.monitor();
                waiter.waitForIt();
                Move move = player.nextMove().orElse(Move.NONE);
                moved |= (nextMove = playfield.move(move));
            } while (nextMove);

        } while (moved);

        return new Score(score);
    }

    public static void main(String[] args) {
        int rows = 10;
        int cols = 20;
        int delay = 500;
        KeyLogger keyLogger = new KeyLoggerImp();
        var feed = new BlockFeed();
        var printer = new Printer(System.out);
        var playfield = new Playfield(rows, cols, feed, printer);
        var waiter = new Waiter(0);
        var game = new TeaTrees(playfield, new Waiter(delay), new RandomPlayer(new Random()),
                new PauseMonitor(new InputStreamReader(System.in), new Pause(waiter)));

        keyLogger.subscribeForKey(Key.VC_SPACE, game::accept);
        keyLogger.subscribeForKey(Key.VC_R, game::accept);
        int a = 1;

        while (a < 500000) {

        }
        System.out.println("Score: " + 1);
        // System.out.println("Score: " + score.points());
    }

    @Override
    public void accept(Integer keyCode) {
        if (keyCode == Key.VC_SPACE) {
            System.out.println("space pressed from game");
        } else if (keyCode == Key.VC_R) {
            System.out.println("r pressed from game");
        }
    }
}

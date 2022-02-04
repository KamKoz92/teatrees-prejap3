package com.epam.prejap.teatrees.pause;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

/**
 * Monitors if player provided input during a gameplay.
 *
 * @author Maciej Blok
 */
public class PauseMonitor {

    private final Reader rdr;
    private final Scanner scanner;
    private Pause pause;

    public PauseMonitor(InputStreamReader rdr, Pause pause) {

        this.rdr = rdr;
        this.scanner = new Scanner(rdr);
        this.pause = pause;
    }

    /**
     * Invokes monitoring of the game.
     * In case the input is detected, it checks if the input causes pause activating/deactivating, or it sets re-run
     * delay time. In such case invokes the appropriate action from the corresponding Pause class side.
     */
    public void monitor() {
        if (!pause.isPause()) waitForPauseActivation();
        monitorPauseState();
    }

    private void waitForPauseActivation() {
        try {
            if (rdr.ready()) {
                String input = scanner.next();
                if (input.equals("p")) {
                    pause.pause();
                }
            }

        } catch (IOException e) {
            System.err.println("Could not read the input.");

        }
    }

    private void monitorPauseState() {
        while (pause.isPause()) {
            String input = scanner.next();
            if (input.equals("p")) {
                pause.pause();
                pause.waitForIt();
            } else if (input.matches("\\d") || input.matches("\\d{2}") || input.matches("\\d{3}")) {
                pause = pause.initiateWaiter(Integer.parseInt(input));
            }
        }
    }

}

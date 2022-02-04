package com.epam.prejap.teatrees.game;

import com.epam.prejap.teatrees.block.Block;
import com.epam.prejap.teatrees.block.BlockFeed;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class Playfield {

    private final Grid grid;
    private final int rows;
    private final int cols;
    private final Printer printer;
    private final BlockFeed feed;

    private Block block;
    private int row;
    private int col;
    boolean downArrowPressed;

    public Playfield(int rows, int cols, BlockFeed feed, Printer printer) {
        this.rows = rows;
        this.cols = cols;
        this.feed = feed;
        this.printer = printer;
        grid = new Grid(new byte[rows][cols]);
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("There was a problem registering the native hook: " + e.getMessage());
        }
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent key) {
                if (key.getKeyCode() == NativeKeyEvent.VC_DOWN) {
                    downArrowPressed = true;
                }
            }
        });
        
    }

    /**
     * Before next block appears on the playfield, all complete lines should be removed and replaced with empty
     * lines on the top.
     */
    public void nextBlock() {
        grid.removeCompleteLines();
        block = feed.nextBlock();
        row = 0;
        col = (cols - block.cols()) / 2;
        show();
    }

    public boolean move(Move move) {
        hide();
        boolean moved;
        if (downArrowPressed) {
            moveAllWayDown();
            moved = downArrowPressed = false;
        } else {
            switch (move) {
                case LEFT -> moveLeft();
                case RIGHT -> moveRight();
            }
            moved = moveDown();
        }
        show();
        return moved;
    }

    private void moveRight() {
        move(0, 1);
    }

    private void moveLeft() {
        move(0, -1);
    }

    private boolean moveDown() {
        return move(1, 0);
    }

    private void moveAllWayDown() {
        move(checkMoveDepth(), 0);
    }

    private int checkMoveDepth() {
        int depth = 0;
        while (isValidMove(block, ++depth, 0))
            ;
        return --depth;
    }

    private boolean move(int rowOffset, int colOffset) {
        boolean moved = false;
        if (isValidMove(block, rowOffset, colOffset)) {
            doMove(rowOffset, colOffset);
            moved = true;
        }
        return moved;
    }

    private boolean isValidMove(Block block, int rowOffset, int colOffset) {
        for (int i = 0; i < block.rows(); i++) {
            for (int j = 0; j < block.cols(); j++) {
                var dot = block.dotAt(i, j);
                if (dot > 0) {
                    int newRow = row + i + rowOffset;
                    int newCol = col + j + colOffset;
                    if (newRow >= rows || newCol >= cols || !grid.isCellEmpty(newRow, newCol)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void hide() {
        forEachBrick((i, j, dot) -> grid.cleanCell(row + i, col + j));
    }

    private void show() {
        forEachBrick((i, j, dot) -> grid.fillCell(row + i, col + j, dot));
        printer.draw(grid.getGrid());
    }

    private void doMove(int rowOffset, int colOffset) {
        row += rowOffset;
        col += colOffset;
    }

    private void forEachBrick(BrickAction action) {
        for (int i = 0; i < block.rows(); i++) {
            for (int j = 0; j < block.cols(); j++) {
                var dot = block.dotAt(i, j);
                if (dot > 0) {
                    action.act(i, j, dot);
                }
            }
        }
    }

    private interface BrickAction {
        void act(int i, int j, byte dot);
    }
}

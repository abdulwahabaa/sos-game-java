package com.abdulwahab.sosgame.ai;

import com.abdulwahab.sosgame.core.GameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer {

    private final GameLogic game;
    private final Random random = new Random();

    public ComputerPlayer(GameLogic game) {
        this.game = game;
    }

    // Small helper class to describe one move
    public static class Move {
        public final int row;
        public final int col;
        public final char letter;

        public Move(int row, int col, char letter) {
            this.row = row;
            this.col = col;
            this.letter = letter;
        }
    }

    public Move chooseMove() {
        int size = game.getSize();
        char[][] board = game.getBoard();

        // Collect all empty cells
        List<int[]> emptyCells = new ArrayList<>();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board[r][c] == '\0') {
                    emptyCells.add(new int[]{r, c});
                }
            }
        }

        // No move available (should only happen when board is full)
        if (emptyCells.isEmpty()) {
            return null;
        }

        // Pick a random empty cell
        int[] cell = emptyCells.get(random.nextInt(emptyCells.size()));
        char letter = random.nextBoolean() ? 'S' : 'O';

        return new Move(cell[0], cell[1], letter);
    }
}

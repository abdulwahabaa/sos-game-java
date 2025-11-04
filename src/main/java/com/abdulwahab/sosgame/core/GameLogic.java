package com.abdulwahab.sosgame.core;

import com.abdulwahab.sosgame.mode.GameMode;
import com.abdulwahab.sosgame.model.SOSLine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

// GameLogic class handles core functionality of game

public class GameLogic {
    public enum Player { RED, BLUE }

//Used enum to represent both players

    private final char[][] board;
    //Multidimensional array to represent game board
    private final int size;
    //Size of board
    private Player currentPlayer = Player.RED;
    //Track players turn, I chose red to start
    private GameMode mode;

    public GameLogic(int size) {
        if (size < 3 ) {
            throw new IllegalArgumentException("Size must be greater than or equal to 3");
            //Creates minimum board size of 3
        }
        this.board = new char[size][size];
        this.size = size;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    } //sets the rule system the game is using
    public GameMode getMode() {
        return mode;
    } //returns which game mode is currently active

    public boolean playerMove (int row, int col, char letter ) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            System.out.println("Out of bounds");
            return false;
            //Gives message to player if they place a letter outside board
        }
        if (board[row][col] != '\0') {
            System.out.println("Placement is already full");
            return false;
            //Gives message to player if they try to place a letter on top of another
        }
        board[row][col] = letter;
        nextTurn();
        return true;
    }

    public void nextTurn() {
        if (currentPlayer == Player.RED) {
            currentPlayer = Player.BLUE;
        }
        else {
            currentPlayer = Player.RED;
        }
        //Checks which players turn it is, if red then changes to blue
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public char[][] getBoard() {
        return board;
    }
    public int getSize() {
        return size;
    }
    //Getter methods

    public boolean placeLetter(int row, int col, char letter) {
        if (row < 0 || row >= size || col < 0 || col >= size) return false;
        if (board[row][col] != '\0') return false;
        board[row][col] = letter;
        return true;
    } //Places a letter on board without switching turns

    public boolean isBoardFull() {
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                if (board[r][c] == '\0') return false;
        return true;
    }
        //Checks whether placing a letter at row,col creates any SOS pattern
    public List<SOSLine> findNewSOSAt(int row, int col) {
        List<SOSLine> lines = new ArrayList<>();
        BiPredicate<Integer,Integer> in = (r,c) -> r >= 0 && r < size && c >= 0 && c < size;
        char[][] b = board;
        int[][] dirs = { {0,1}, {1,0}, {1,1}, {1,-1} };

        for (int[] d : dirs) {
            int dr = d[0], dc = d[1];

            int r1 = row - dr, c1 = col - dc;
            int r3 = row + dr, c3 = col + dc;
            if (in.test(r1,c1) && in.test(r3,c3))
                if (b[row][col] == 'O' && b[r1][c1] == 'S' && b[r3][c3] == 'S')
                    lines.add(new SOSLine(r1, c1, r3, c3));

            int rm = row + dr, cm = col + dc;
            int re = row + 2*dr, ce = col + 2*dc;
            if (in.test(rm,cm) && in.test(re,ce))
                if (b[row][col] == 'S' && b[rm][cm] == 'O' && b[re][ce] == 'S')
                    lines.add(new SOSLine(row, col, re, ce));

            int rs = row - 2*dr, cs = col - 2*dc;
            int rm2 = row - dr, cm2 = col - dc;
            if (in.test(rs,cs) && in.test(rm2,cm2))
                if (b[rs][cs] == 'S' && b[rm2][cm2] == 'O' && b[row][col] == 'S')
                    lines.add(new SOSLine(rs, cs, row, col));
        }
        return lines;
    }

}
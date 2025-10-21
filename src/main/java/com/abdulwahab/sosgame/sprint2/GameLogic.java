package com.abdulwahab.sosgame.sprint2;

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

    public GameLogic(int size, char[][] board) {
        if (size < 3 ) {
            throw new IllegalArgumentException("Size must be greater than or equal to 3");
            //Creates minimum board size of 3
        }
        this.board = new char[size][size];
        this.size = size;
    }

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
}
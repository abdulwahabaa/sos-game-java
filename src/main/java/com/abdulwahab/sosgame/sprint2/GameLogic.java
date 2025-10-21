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
}

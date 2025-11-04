package com.abdulwahab.sosgame.mode;
//This lets GameLogic defer scoring/win logic to a selected mode class

import com.abdulwahab.sosgame.core.GameLogic;
import com.abdulwahab.sosgame.model.SOSLine;

import java.util.List;


public abstract class GameMode {
    protected final GameLogic game;
    public GameMode(GameLogic game) { this.game = game; }

    // Handles a move
    public abstract List<SOSLine> handleMove(int row, int col, char letter);

    //True if the game should end
    public abstract boolean isGameOver();

    //Winner if decided
    public abstract GameLogic.Player getWinner();
}
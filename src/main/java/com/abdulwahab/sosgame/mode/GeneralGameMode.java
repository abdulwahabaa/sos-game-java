package com.abdulwahab.sosgame.mode;

import com.abdulwahab.sosgame.core.GameLogic;
import com.abdulwahab.sosgame.model.SOSLine;

import java.util.List;

public class GeneralGameMode extends GameMode{
    private int redScore = 0;
    private int blueScore = 0;

    public GeneralGameMode(GameLogic game) { super(game); }

    @Override
    public List<SOSLine> handleMove(int row, int col, char letter) {
        if (!game.placeLetter(row, col, letter)) return List.of();

        List<SOSLine> lines = game.findNewSOSAt(row, col);
        int gained = lines.size();

        if (gained == 0) {
            game.nextTurn(); // no points → alternate
        } else {
            if (game.getCurrentPlayer() == GameLogic.Player.RED) redScore += gained;
            else blueScore += gained; // points → same player goes again
        }
        return lines;
    }

    @Override
    public boolean isGameOver() {
        return game.isBoardFull();
    }

    @Override
    public GameLogic.Player getWinner() {
        if (redScore > blueScore) return GameLogic.Player.RED;
        if (blueScore > redScore) return GameLogic.Player.BLUE;
        return null; // draw
    }

    // Optional getters
    public int getRedScore() { return redScore; }
    public int getBlueScore() { return blueScore; }
}

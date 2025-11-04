package com.abdulwahab.sosgame.mode;

import java.util.List;
import com.abdulwahab.sosgame.core.GameLogic;
import com.abdulwahab.sosgame.model.SOSLine;

public class SimpleGameMode extends GameMode {
    private GameLogic.Player winner = null;

    public SimpleGameMode(GameLogic game) { super(game); }

    @Override
    public List<SOSLine> handleMove(int row, int col, char letter) {
        if (!game.placeLetter(row, col, letter)) return List.of();

        List<SOSLine> lines = game.findNewSOSAt(row, col);
        if (!lines.isEmpty()) {
            // First SOS ends the game
            winner = game.getCurrentPlayer();
        } else {
            game.nextTurn();
        }
        return lines;
    }

    @Override
    public boolean isGameOver() {
        return winner != null || game.isBoardFull();
    }

    @Override
    public GameLogic.Player getWinner() {
        return winner; // null means draw/ongoing
    }
}
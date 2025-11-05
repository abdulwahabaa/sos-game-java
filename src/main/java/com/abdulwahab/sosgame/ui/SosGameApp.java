package com.abdulwahab.sosgame.ui;

import com.abdulwahab.sosgame.core.GameLogic;
import com.abdulwahab.sosgame.mode.SimpleGameMode;

import javax.swing.*;

public class SosGameApp {
    public static void main(String[] args) {
        GameLogic game = new GameLogic(8);
        //Makes a new game with size 8
        game.setMode(new SimpleGameMode(game));
        SosGameUI ui = new SosGameUI(game);
        //Shows the game window using UI
        ui.setVisible(true);
    }
}

package com.abdulwahab.sosgame.sprint2;
import javax.swing.*;


public class SosGameApp {
    public static void main(String[] args) {
        GameLogic game = new GameLogic(8);
        //Makes a new game with size 8
        SosGameUI ui = new SosGameUI(game);
        //Shows the game window using UI
        ui.setVisible(true);
    }
}

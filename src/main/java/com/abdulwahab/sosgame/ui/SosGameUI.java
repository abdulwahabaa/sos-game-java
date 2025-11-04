package com.abdulwahab.sosgame.ui;

import com.abdulwahab.sosgame.core.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SosGameUI extends JFrame {

    private final GameLogic game;
    private final JButton[][] boardButtons;
    private final JLabel turnLabel;

    private final JRadioButton blueS, blueO, redS, redO;
    private final JRadioButton simpleGame, generalGame;
    private final JTextField boardSizeField;

    public SosGameUI(GameLogic game) {
        this.game = game;
        this.boardButtons = new JButton[game.getSize()][game.getSize()];


        setTitle("SOS Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //Window setup

        JPanel topPanel = new JPanel();
        simpleGame = new JRadioButton("Simple game", true); //Default
        generalGame = new JRadioButton("General game");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(simpleGame);
        modeGroup.add(generalGame);

       boardSizeField = new JTextField(String.valueOf(game.getSize()), 3);
       //Shows current board

        topPanel.add(new JLabel("SOS"));
        topPanel.add(simpleGame);
        topPanel.add(generalGame);
        topPanel.add(new JLabel("Board size:"));
        topPanel.add(boardSizeField);
        topPanel.setBackground(Color.WHITE);
        //White background for game
        add(topPanel, BorderLayout.NORTH);
        //Game mode and board size


        JPanel bluePanel = new JPanel();
        bluePanel.setLayout(new BoxLayout(bluePanel, BoxLayout.Y_AXIS));
        blueS = new JRadioButton("S", true);
        blueO = new JRadioButton("O");
        ButtonGroup blueGroup = new ButtonGroup();
        blueGroup.add(blueS);
        blueGroup.add(blueO);
        bluePanel.add(new JLabel("Blue player"));
        bluePanel.add(blueS);
        bluePanel.add(blueO);
        bluePanel.setBackground(Color.WHITE);
        add(bluePanel, BorderLayout.WEST);
        //Blue players controls

        JPanel redPanel = new JPanel();
        redPanel.setLayout(new BoxLayout(redPanel, BoxLayout.Y_AXIS));
        redS = new JRadioButton("S", true);
        redO = new JRadioButton("O");
        ButtonGroup redGroup = new ButtonGroup();
        redGroup.add(redS);
        redGroup.add(redO);
        redPanel.add(new JLabel("Red player"));
        redPanel.add(redS);
        redPanel.add(redO);
        redPanel.setBackground(Color.WHITE);
        add(redPanel, BorderLayout.EAST);
        //Red players controls



        JPanel boardPanel = new JPanel(new GridLayout(game.getSize(), game.getSize()));         //Center section
        boardPanel.setBackground(Color.WHITE);
        for (int row = 0; row < game.getSize(); row++) {
            for (int col = 0; col < game.getSize(); col++) {
                JButton button = new JButton("");
                button.setFont(new Font("Arial", Font.BOLD, 20));
                button.setBackground(Color.WHITE);
                int finalRow = row;
                int finalCol = col;

                button.addActionListener((ActionEvent e) -> handleMove(finalRow, finalCol, button));
                //Event runs after player clicks the button

                boardButtons[row][col] = button;
                boardPanel.add(button);
            }
        }
        add(boardPanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel();
        turnLabel = new JLabel("Current turn: " + game.getCurrentPlayer());
        bottomPanel.add(turnLabel);
        bottomPanel.setBackground(Color.WHITE);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        //Displays window
        setLocationRelativeTo(null);
        //Center window on screen
    }

    private void handleMove(int row, int col, JButton button) {
        char letter;

        if (game.getCurrentPlayer() == GameLogic.Player.RED) {
            letter = redS.isSelected() ? 'S' : 'O';
        } else {
            letter = blueS.isSelected() ? 'S' : 'O';
        }

        boolean moveSuccess = game.playerMove(row, col, letter);
        //Trys placing letter on board

        if (moveSuccess) {
            button.setText(String.valueOf(letter));
            updateTurnLabel();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move! Try again.");
            //Invalid move message if square is filled
        }
    }
    private void updateTurnLabel() {
        turnLabel.setText("Current turn: " + game.getCurrentPlayer());
    }
}
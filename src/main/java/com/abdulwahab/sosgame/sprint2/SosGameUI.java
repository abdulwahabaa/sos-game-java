package com.abdulwahab.sosgame.sprint2;

import javax.swing.*;
import java.awt.*;

public class SosGameUI extends JFrame {

    public SosGameUI() {
        setTitle("SosGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //Window setup

        JPanel topPanel = new JPanel();
        JRadioButton simpleGame = new JRadioButton("Simple game", true);
        JRadioButton generalGame = new JRadioButton("General game");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(simpleGame);
        modeGroup.add(generalGame);

        JTextField boardSizeField = new JTextField("5", 3);

        topPanel.add(new JLabel("SOS"));
        topPanel.add(simpleGame);
        topPanel.add(generalGame);
        topPanel.add(new JLabel("Board size:"));
        topPanel.add(boardSizeField);
        add(topPanel, BorderLayout.NORTH);
        //Game mode and board size


        pack();
        setLocationRelativeTo(null);
    }
}
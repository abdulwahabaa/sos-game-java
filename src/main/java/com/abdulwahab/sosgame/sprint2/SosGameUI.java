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


        JPanel bluePanel = new JPanel();
        bluePanel.setLayout(new BoxLayout(bluePanel, BoxLayout.Y_AXIS));
        JRadioButton blueS = new JRadioButton("S", true);
        JRadioButton blueO = new JRadioButton("O");
        ButtonGroup blueGroup = new ButtonGroup();
        blueGroup.add(blueS);
        blueGroup.add(blueO);
        bluePanel.add(new JLabel("Blue player"));
        bluePanel.add(blueS);
        bluePanel.add(blueO);
        add(bluePanel, BorderLayout.WEST);
        //Blue players controls

        JPanel redPanel = new JPanel();
        redPanel.setLayout(new BoxLayout(redPanel, BoxLayout.Y_AXIS));
        JRadioButton redS = new JRadioButton("S", true);
        JRadioButton redO = new JRadioButton("O");
        ButtonGroup redGroup = new ButtonGroup();
        redGroup.add(redS);
        redGroup.add(redO);
        redPanel.add(new JLabel("Red player"));
        redPanel.add(redS);
        redPanel.add(redO);
        add(redPanel, BorderLayout.EAST);
        //Red players controls



        pack();
        setLocationRelativeTo(null);
    }
}
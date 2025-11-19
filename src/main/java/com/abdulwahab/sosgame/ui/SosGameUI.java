package com.abdulwahab.sosgame.ui;

import com.abdulwahab.sosgame.ai.ComputerPlayer;
import com.abdulwahab.sosgame.core.GameLogic;
import com.abdulwahab.sosgame.model.SOSLine;
import com.abdulwahab.sosgame.mode.GeneralGameMode;
import com.abdulwahab.sosgame.mode.SimpleGameMode;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;


public class SosGameUI extends JFrame {

    private final GameLogic game;
    private final JButton[][] boardButtons;
    private final JLabel turnLabel;

    private final JRadioButton blueHuman, blueComputer;
    private final JRadioButton redHuman, redComputer;

    private final JRadioButton blueS, blueO, redS, redO;
    private final JRadioButton simpleGame, generalGame;
    private final JTextField boardSizeField;

    private Overlay overlay;
    private JLayeredPane boardLayer;
    private JPanel boardPanel;
    //overlay for drawing SOS lines

    private static final int CELL_SIZE = 40;

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

        // Switch game mode immediately when selected
        simpleGame.addActionListener(e -> {
            game.setMode(new SimpleGameMode(game));
            updateTurnLabel();
        });

        generalGame.addActionListener(e -> {
            game.setMode(new GeneralGameMode(game));
            updateTurnLabel();
        });


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

        blueHuman = new JRadioButton("Human", true);
        blueComputer = new JRadioButton("Computer");
        ButtonGroup blueTypeGroup = new ButtonGroup();
        blueTypeGroup.add(blueHuman);
        blueTypeGroup.add(blueComputer);
        //blue player type

        blueS = new JRadioButton("S", true);
        blueO = new JRadioButton("O");
        ButtonGroup blueGroup = new ButtonGroup();
        blueGroup.add(blueS);
        blueGroup.add(blueO);

        bluePanel.add(new JLabel("Blue player"));
        bluePanel.add(blueHuman);
        bluePanel.add(blueS);
        bluePanel.add(blueO);
        bluePanel.add(blueComputer);
        bluePanel.setBackground(Color.WHITE);
        add(bluePanel, BorderLayout.WEST);
        //Blue players controls

        JPanel redPanel = new JPanel();
        redPanel.setLayout(new BoxLayout(redPanel, BoxLayout.Y_AXIS));

        redHuman = new JRadioButton("Human", true);
        redComputer = new JRadioButton("Computer");
        ButtonGroup redTypeGroup = new ButtonGroup();
        redTypeGroup.add(redHuman);
        redTypeGroup.add(redComputer);

        redS = new JRadioButton("S", true);
        redO = new JRadioButton("O");
        ButtonGroup redGroup = new ButtonGroup();
        redGroup.add(redS);
        redGroup.add(redO);

        redPanel.add(new JLabel("Red player"));
        redPanel.add(redHuman);
        redPanel.add(redS);
        redPanel.add(redO);
        redPanel.add(redComputer);
        redPanel.setBackground(Color.WHITE);
        add(redPanel, BorderLayout.EAST);
        //Red players controls

        buildBoardLayer();

        JPanel bottomPanel = new JPanel(new BorderLayout());
        turnLabel = new JLabel("Current turn: " + game.getCurrentPlayer());
        bottomPanel.add(turnLabel, BorderLayout.WEST);

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> startNewGame());
        bottomPanel.add(newGameButton, BorderLayout.EAST);

        bottomPanel.setBackground(Color.WHITE);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        if (game.getMode()==null){
            game.setMode(new SimpleGameMode(game));
        }

        //if starting player is computer
        runComputerTurnsIfNeeded();
    }   // default to simple game

    private void buildBoardLayer() {
        int n = game.getSize();

        // Grid of buttons no fixed preferred size so it stretches
        boardPanel = new JPanel(new GridLayout(n, n));
        boardPanel.setBackground(Color.WHITE);

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                JButton btn = new JButton("");
                btn.setFont(new Font("Arial", Font.BOLD, 18));  // will update on resize
                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.BLACK);
                btn.setFocusPainted(false);
                btn.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));

                int rr = r, cc = c;

                btn.addActionListener((ActionEvent e) -> {
                    if (!isCurrentPlayerHuman() || game.getMode().isGameOver()){
                        return;
                    }
                    handleMove(rr, cc, btn, null);  //null is human; lettern form radios
                    runComputerTurnsIfNeeded();
                });

                boardButtons[r][c] = btn;
                boardPanel.add(btn);
            }
        }

        // layers resize together
        JPanel boardStack = new JPanel();
        boardStack.setLayout(new OverlayLayout(boardStack));
        boardStack.setBackground(Color.WHITE);

        // Overlay sits on top and repaints using current size
        overlay = new Overlay(() -> boardStack.getWidth(), () -> boardStack.getHeight(), n);
        overlay.setOpaque(false);

        // overlay is on top
        boardStack.add(overlay);
        boardStack.add(boardPanel);

        // update button fonts so letters scale a bit
        boardStack.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override public void componentResized(java.awt.event.ComponentEvent e) {
                int cellW = boardStack.getWidth() / Math.max(1, n);
                int cellH = boardStack.getHeight() / Math.max(1, n);
                int cell = Math.min(cellW, cellH);
                int fontSize = Math.max(12, (int)(cell * 0.45));
                Font f = new Font("Arial", Font.BOLD, fontSize);
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        boardButtons[i][j].setFont(f);
                    }
                }
                overlay.repaint();
            }
        });

        add(boardStack, BorderLayout.CENTER);
    }


    private void handleMove(int row, int col, JButton button, Character forcedLetter) {
        if (game.getMode().isGameOver()) {
            return;
        }

        char letter;
        if (forcedLetter != null) {
            letter = forcedLetter;
        } else {
            letter = (game.getCurrentPlayer() == GameLogic.Player.RED)
                    ? (redS.isSelected() ? 'S' : 'O')
                    : (blueS.isSelected() ? 'S' : 'O');
        }

        // Remembers board state before move
        char before = game.getBoard()[row][col];
        GameLogic.Player mover = game.getCurrentPlayer();

        var lines = game.getMode().handleMove(row, col, letter);

        // Valid move check
        boolean placed = (before == '\0') && (game.getBoard()[row][col] == letter);

        if (!placed) {
            if (forcedLetter == null) {
                JOptionPane.showMessageDialog(this, "Invalid move! Try again.");
            }
            return;
        }


        // shows the letter
        button.setText(String.valueOf(letter));
        button.setForeground(Color.BLACK);   // << important
        button.repaint();

        // Draws SOS lines if needed
        if (!lines.isEmpty()) {
            Color color = (mover == GameLogic.Player.RED) ? Color.RED : Color.BLUE;
            overlay.addLines(lines, color);
        }

        updateTurnLabel();

        if (game.getMode().isGameOver()) {
            var w = game.getMode().getWinner();
            JOptionPane.showMessageDialog(this, (w == null) ? "Draw!" : (w + " wins!"));
        }
    }

    private boolean isCurrentPlayerHuman() {
        if (game.getCurrentPlayer() == GameLogic.Player.BLUE) {
            return blueHuman.isSelected();
        } else {
            return redHuman.isSelected();
        }
    }// who is a human/computer

    private boolean isCurrentPlayerComputer() {
        if (game.getCurrentPlayer() == GameLogic.Player.BLUE) {
            return blueComputer.isSelected();
        } else {
            return redComputer.isSelected();
        }
    }

    private void runComputerTurnsIfNeeded() {
        while (!game.getMode().isGameOver() && isCurrentPlayerComputer()) {
            ComputerPlayer ai = new ComputerPlayer(game);
            ComputerPlayer.Move move = ai.chooseMove();
            if (move == null) {
                return; // no legal moves
            }
            JButton btn = boardButtons[move.row][move.col];
            handleMove(move.row, move.col, btn, move.letter);
        }
    }//let computer play its moves while it is the current player


    private void updateTurnLabel() {
        turnLabel.setText("Current turn: " + game.getCurrentPlayer());
    }

    private void startNewGame() {
        int newSize;
        try {
            newSize = Integer.parseInt(boardSizeField.getText().trim());
            if (newSize < 3) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Board size must be an integer ≥ 3.");
            return;
        }

        GameLogic fresh = new GameLogic(newSize);
        if (simpleGame.isSelected()) fresh.setMode(new SimpleGameMode(fresh));
        else                            fresh.setMode(new GeneralGameMode(fresh));

        SosGameUI newUI = new SosGameUI(fresh);
        newUI.setVisible(true);
        dispose();
    }

    // Draws colored lines over the grid; computes cell size from the live container size
    private static class Overlay extends JComponent {
        interface IntSupplier { int get(); }

        private static class ColoredLine {
            final com.abdulwahab.sosgame.model.SOSLine line; final Color color;
            ColoredLine(com.abdulwahab.sosgame.model.SOSLine l, Color c) { this.line = l; this.color = c; }
        }
        private final java.util.List<ColoredLine> lines = new java.util.ArrayList<>();
        private final IntSupplier widthSupplier, heightSupplier;
        private final int n; // board size

        Overlay(IntSupplier widthSupplier, IntSupplier heightSupplier, int n) {
            this.widthSupplier = widthSupplier;
            this.heightSupplier = heightSupplier;
            this.n = n;
        }

        void addLines(java.util.List<com.abdulwahab.sosgame.model.SOSLine> sosLines, Color color) {
            for (var l : sosLines) lines.add(new ColoredLine(l, color));
            repaint();
        }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int W = Math.max(1, widthSupplier.get());
            int H = Math.max(1, heightSupplier.get());
            // dynamic cell size
            int cell = Math.min(W / Math.max(1, n), H / Math.max(1, n));

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(Math.max(2f, cell * 0.08f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            for (ColoredLine cl : lines) {
                g2.setColor(cl.color);
                int x1 = cl.line.c1 * cell + cell / 2;
                int y1 = cl.line.r1 * cell + cell / 2;
                int x2 = cl.line.c2 * cell + cell / 2;
                int y2 = cl.line.r2 * cell + cell / 2;
                g2.drawLine(x1, y1, x2, y2);
            }
            g2.dispose();
        }
    }
}
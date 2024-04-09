package Puissance4Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUI extends JFrame {
    private JButton[][] gridButtons;
    private JLabel currentPlayerLabel;
    private Game game;
    private JLabel scoreJ1Label;
    private JLabel scoreJ2Label;

    public GameUI(Game game) {
        this.game = game;
        setTitle("Puissance 4 - Jeu en cours");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 7));

        gridButtons = new JButton[6][7];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                gridButtons[i][j] = new JButton();
                gridButtons[i][j].setBackground(Color.WHITE);
                gridButtons[i][j].addActionListener(new ButtonClickListener(j));
                panel.add(gridButtons[i][j]);
            }
        }

        currentPlayerLabel = new JLabel("Joueur en jeu " + game.getCurrentPlayerName() +" Signe : " + game.getCurrentPlayerSymbol());
        panel.add(currentPlayerLabel);

        scoreJ1Label = new JLabel("Score de " + game.player1.getName() +" Signe : " + game.player1.getSymbol()+ " : " + game.getScoreJ1());
        panel.add(scoreJ1Label);

        scoreJ2Label = new JLabel("Score de " + game.player2.getName() +" Signe : " + game.player2.getSymbol()+ " : " + game.getScoreJ2());
        panel.add(scoreJ2Label);

        JButton restartButton = new JButton("Recommencer");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        panel.add(restartButton);

        add(panel);
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int col;

        public ButtonClickListener(int col) {
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!game.isGameOver() && !game.isColumnFull(col)) {
                int lastRow = game.getLastRow(col);
                game.dropToken(col);
                gridButtons[lastRow][col].setText(Character.toString(game.getCurrentPlayerSymbol()));
                gridButtons[lastRow][col].setBackground((game.getCurrentPlayerSymbol() == 'X') ? Color.RED : Color.YELLOW);

                if (game.checkForWin(lastRow, col)) {
                    game.endGame();
                } else if (game.isGridFull()) {
                    game.endGameWithDraw();
                }
                game.switchPlayer();

                currentPlayerLabel.setText("Joueur en jeu " + game.getCurrentPlayerName() +" Signe : " + game.getCurrentPlayerSymbol());

                if (game.player2.getName().equals("IA")){
                    int col2 = AIPlayer.chooseMove(game.getGrid());
                    int ailastrow = game.getLastRow(col2);
                    game.dropToken(col2);
                    gridButtons[ailastrow][col2].setText(Character.toString(game.getCurrentPlayerSymbol()));
                    gridButtons[ailastrow][col2].setBackground((game.getCurrentPlayerSymbol() == 'X') ? Color.RED : Color.YELLOW);

                    if (game.checkForWin(ailastrow, col2)) {
                        game.endGame();
                    } else if (game.isGridFull()) {
                        game.endGameWithDraw();
                    }
                    game.switchPlayer();
                    currentPlayerLabel.setText("Joueur en jeu " + game.getCurrentPlayerName() +" Signe : " + game.getCurrentPlayerSymbol());

                }
            }
            updateScores();
        }
    }

    public void updateScores() {
        scoreJ1Label.setText("Score de " + game.player1.getName() +" Signe : " + game.player1.getSymbol()+ " : " + game.getScoreJ1());
        scoreJ2Label.setText("Score de " + game.player2.getName() +" Signe : " + game.player2.getSymbol()+ " : " + game.getScoreJ2());
    }

    protected void resetGame() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                gridButtons[i][j].setText("");
                gridButtons[i][j].setBackground(Color.WHITE);
            }
        }
        game.resetGame();

        if(!game.player2.getName().equals("IA")){
        game.switchStartPlayer();
        }

        currentPlayerLabel.setText("Joueur en jeu : " + game.getCurrentPlayerName() + " Signe : " + game.getCurrentPlayerSymbol());
        updateScores();
    }

}

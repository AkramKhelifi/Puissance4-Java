package Puissance4Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUI extends JFrame {
    private JButton[][] gridButtons;
    private JLabel currentPlayerLabel;
    private Game game;

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

        currentPlayerLabel = new JLabel("C'est le tour de " + game.getCurrentPlayerName() +" Signe : " + game.getCurrentPlayerSymbol());
        panel.add(currentPlayerLabel);

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
                } else {
                    game.switchPlayer();
                    currentPlayerLabel.setText("C'est le tour de " + game.getCurrentPlayerName() + " - Signe : " + game.getCurrentPlayerSymbol());;
                }
            }
        }
    }

    private void resetGame() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                gridButtons[i][j].setText("");
                gridButtons[i][j].setBackground(Color.WHITE);
            }
        }
        game.resetGame();
        currentPlayerLabel.setText("Tour du joueur : " + game.getCurrentPlayerSymbol());
    }


}

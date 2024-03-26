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

        currentPlayerLabel = new JLabel("Tour du joueur : " + game.getCurrentPlayerSymbol());
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
            if (game.dropToken(col)) {
                int lastRow = game.getLastRow(col);
                if (game.checkForWin(lastRow, col)) {
                    JOptionPane.showMessageDialog(null, "Le joueur " + game.getCurrentPlayerSymbol() + " a gagné!", "Fin de la partie", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }else{
                gridButtons[lastRow+1][col].setText(Character.toString(game.getCurrentPlayerSymbol()));
                gridButtons[lastRow+1][col].setBackground((game.getCurrentPlayerSymbol() == 'X') ? Color.RED : Color.YELLOW);
                game.switchPlayer();
                currentPlayerLabel.setText("Tour du joueur : " + game.getCurrentPlayerSymbol());}

                if (game.isGameOver()) {
                    JOptionPane.showMessageDialog(null, "Le joueur " + game.getCurrentPlayerSymbol() + " a gagné!", "Fin de la partie", JOptionPane.INFORMATION_MESSAGE);
                } else if (game.isGridFull()) {
                    JOptionPane.showMessageDialog(null, "Match nul!", "Fin de la partie", JOptionPane.INFORMATION_MESSAGE);
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

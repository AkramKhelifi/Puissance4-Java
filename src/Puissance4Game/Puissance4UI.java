package Puissance4Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Puissance4UI extends JFrame {
    private JLabel player1Label;
    private JTextField player1NameField;
    private JLabel player2Label;
    private JTextField player2NameField;
    private JButton aiPlayerButton;
    private JButton startButton;

    public Puissance4UI() {
        setTitle("Puissance 4");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        player1Label = new JLabel("Joueur 1 (X): ");
        player1NameField = new JTextField();
        player2Label = new JLabel("Joueur 2 (O): ");
        player2NameField = new JTextField();
        aiPlayerButton = new JButton("IA");
        startButton = new JButton("Commencer la partie");


        add(player1Label);
        add(player1NameField);
        add(player2Label);
        add(player2NameField);
        add(new JLabel());
        add(new JLabel());
        add(aiPlayerButton);
        add(startButton);

        aiPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player2Label.setText("IA (O): ");
                player2NameField.setEditable(false);
                player2NameField.setText("IA");
            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isPlayer1NameValid()) {
                    String player1Name = player1NameField.getText();
                    String player2Name = player2NameField.getText();

                    if (aiPlayerButton.isEnabled() && player2Name.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Veuillez entrer le nom du Joueur 2.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Player player1 = new Player(player1Name, 'X');
                    Player player2;

                    if (aiPlayerButton.isEnabled()) {
                        player2 = new AIPlayer(player2Name, 'O');
                    } else {
                        if (!isPlayer2NameValid()) {
                            JOptionPane.showMessageDialog(null, "Veuillez entrer le nom du Joueur 2.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        player2 = new Player(player2Name, 'O');
                    }

                    launchGameUI(player1, player2);
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer le nom du Joueur 1.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void launchGameUI(Player player1, Player player2) {
        Game game = new Game(player1, player2);
        GameUI gameUI = new GameUI(game);
        gameUI.setVisible(true);
        setVisible(false);
    }

    private boolean isPlayer1NameValid() {
        return !player1NameField.getText().trim().isEmpty();
    }

    private boolean isPlayer2NameValid() {
        return !player2NameField.getText().trim().isEmpty();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Puissance4UI ui = new Puissance4UI();
                ui.setVisible(true);
            }
        });
    }
}

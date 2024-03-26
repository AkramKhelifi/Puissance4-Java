package Puissance4Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Puissance4UI extends JFrame {
    private JLabel player1Label;
    private JTextField player1NameField;
    private JLabel player2Label;
    private JTextField player2NameField;
    private JButton humanPlayerButton;
    private JButton aiPlayerButton;
    private JButton startButton;

    public Puissance4UI() {
        setTitle("Puissance 4");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        player1Label = new JLabel("Joueur 1 (X): ");
        player1NameField = new JTextField();
        player2Label = new JLabel("Joueur 2 (O): ");
        player2NameField = new JTextField();
        humanPlayerButton = new JButton("Joueur Humain");
        aiPlayerButton = new JButton("IA");
        startButton = new JButton("Commencer la partie");

        add(player1Label);
        add(player1NameField);
        add(player2Label);
        add(player2NameField);
        add(new JLabel());
        add(new JLabel());
        add(humanPlayerButton);
        add(aiPlayerButton);
        add(new JLabel());
        add(startButton);

        humanPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player2Label.setText("Joueur 2 (O): ");
                player2NameField.setEditable(true);
                aiPlayerButton.setEnabled(true);
            }
        });

        aiPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player2Label.setText("IA (O): ");
                player2NameField.setEditable(false);
                player2NameField.setText("IA");
                humanPlayerButton.setEnabled(false);
            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isPlayer1NameValid()) {
                    String player1Name = player1NameField.getText();
                    String player2Name = player2NameField.getText();

                    Player player1 = new Player(player1Name, 'X');
                    Player player2;
                    if (aiPlayerButton.isEnabled()) {
                        player2 = new Player(player2Name, 'O');
                    } else {
                        player2 = new AIPlayer(player2Name, 'O');
                    }

                    launchGameUI(player1, player2);
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer le nom du Joueur 1.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    startButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (isPlayer1NameValid()) {
                String player1Name = player1NameField.getText();

                if (!aiPlayerButton.isEnabled() && isPlayer2NameValid() == false) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer le nom du Joueur 2.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Player player1 = new Player(player1Name, 'X');
                Player player2;
                if (aiPlayerButton.isEnabled()) {
                    player2 = new Player(player2Name, 'O');
                } else {
                    player2 = new AIPlayer(player2Name, 'O');
                }

                launchGameUI(player1, player2);
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez entrer le nom du Joueur 1.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    });


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

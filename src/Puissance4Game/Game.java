package Puissance4Game;

import javax.swing.*;
import java.util.Arrays;

public class Game {
    public Player player1;
    public Player player2;
    private char[][] grid;
    private char currentPlayerSymbol;
    private String currentPlayerName;
    private boolean gameOver;
    private int lastCol;
    private int scoreJ1 = 0;
    private int scoreJ2 = 0;
    private Player startPlayer;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.startPlayer = player1;
        this.currentPlayerName = player1.getName();
        this.currentPlayerSymbol = player1.getSymbol();
        this.grid = new char[6][7];
        this.gameOver = false;
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < 6; i++) {
            Arrays.fill(grid[i], ' ');
        }
    }

    protected void switchStartPlayer(){
        if (startPlayer == player1) {
            startPlayer = player2;
            currentPlayerSymbol = player2.getSymbol();
            currentPlayerName = player2.getName();
        } else {
            startPlayer = player1;
            currentPlayerName = player1.getName();
            currentPlayerSymbol = player1.getSymbol();
        }
    }

    protected void switchPlayer() {
        if (currentPlayerSymbol == player1.getSymbol()) {
            currentPlayerSymbol = player2.getSymbol();
            currentPlayerName = player2.getName();
        } else {
            currentPlayerSymbol = player1.getSymbol();
            currentPlayerName = player1.getName();
        }
    }

    protected boolean checkForWin(int row, int col) {
        char currentPlayerSymbol = grid[row][col];

        int count = 1;
        int j = col - 1;
        while (j >= 0 && grid[row][j] == currentPlayerSymbol) {
            count++;
            j--;
        }
        j = col + 1;
        while (j < 7 && grid[row][j] == currentPlayerSymbol) {
            count++;
            j++;
        }
        if (count >= 4) {
            return true;
        }

        count = 1;
        int i = row - 1;
        while (i >= 0 && grid[i][col] == currentPlayerSymbol) {
            count++;
            i--;
        }
        i = row + 1;
        while (i < 6 && grid[i][col] == currentPlayerSymbol) {
            count++;
            i++;
        }
        if (count >= 4) {
            return true;
        }

        count = 1;
        i = row - 1;
        j = col - 1;
        while (i >= 0 && j >= 0 && grid[i][j] == currentPlayerSymbol) {
            count++;
            i--;
            j--;
        }
        i = row + 1;
        j = col + 1;
        while (i < 6 && j < 7 && grid[i][j] == currentPlayerSymbol) {
            count++;
            i++;
            j++;
        }
        if (count >= 4) {
            return true;
        }

        count = 1;
        i = row - 1;
        j = col + 1;
        while (i >= 0 && j < 7 && grid[i][j] == currentPlayerSymbol) {
            count++;
            i--;
            j++;
        }
        i = row + 1;
        j = col - 1;
        while (i < 6 && j >= 0 && grid[i][j] == currentPlayerSymbol) {
            count++;
            i++;
            j--;
        }
        if (count >= 4) {
            return true;
        }

        return false;
    }

    public boolean dropToken(int col) {
        for (int i = 5; i >= 0; i--) {
            if (grid[i][col] == ' ') {
                grid[i][col] = currentPlayerSymbol;
                lastCol = col;
                return true;
            }
        }
        return false;
    }

    public int getLastRow(int col) {
        for (int i = 5; i >= 0; i--) {
            if (grid[i][col] == ' ') {
                return i;
            }
        }
        return -1;
    }

    public boolean isColumnFull(int col) {
        return grid[0][col] != ' ';
    }

    public boolean isGridFull() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (grid[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    protected void endGame() {
        JOptionPane.showMessageDialog(null, "Le joueur " + getCurrentPlayerName() + " (" + getCurrentPlayerSymbol() + ") a gagné!", "Fin de la partie", JOptionPane.INFORMATION_MESSAGE);
        HistoryLogger.logGameResult(getCurrentPlayerName(), getCurrentPlayerName().equals(player1.getName()) ? player2.getName() : player1.getName());
        gameOver = true;

        if (getCurrentPlayerName().equals(player1.getName())) {
            scoreJ1++;
        } else {
            scoreJ2++;
        }

        HistoryLogger.logScores(scoreJ1, scoreJ2, player1.getName(), player2.getName());
    }

    public int getScoreJ1() {
        return scoreJ1;
    }

    public int getScoreJ2() {
        return scoreJ2;
    }

    protected void endGameWithDraw() {
        JOptionPane.showMessageDialog(null, "Match nul!", "Fin de la partie", JOptionPane.INFORMATION_MESSAGE);
        gameOver = true;
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }
    public char getCurrentPlayerSymbol() {
        return currentPlayerSymbol;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public char[][] getGrid() {
        return grid;
    }

    public void resetGame() {
        initializeGrid();
        gameOver = false;
    }


}

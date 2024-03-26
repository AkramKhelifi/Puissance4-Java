package Puissance4Game;

import javax.swing.*;
import java.util.Arrays;

public class Game {
    private Player player1;
    private Player player2;
    private char[][] grid;
    private char currentPlayer;
    private boolean gameOver;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1.getSymbol();
        this.grid = new char[6][7];
        this.gameOver = false;
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < 6; i++) {
            Arrays.fill(grid[i], ' ');
        }
    }

    protected void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    protected boolean checkForWin(int row, int col) {
        int count = 1;

        // Horizontal check
        int j = col - 1;
        while (j >= 0 && grid[row][j] == currentPlayer) {
            count++;
            j--;
        }
        j = col + 1;
        while (j < 7 && grid[row][j] == currentPlayer) {
            count++;
            j++;
        }
        if (count >= 4) {
            return true;
        }

        // Vertical check
        count = 1;
        int i = row - 1;
        while (i >= 0 && grid[i][col] == currentPlayer) {
            count++;
            i--;
        }
        i = row + 1;
        while (i < 6 && grid[i][col] == currentPlayer) {
            count++;
            i++;
        }
        if (count >= 4) {
            return true;
        }

        // Diagonal check 1
        count = 1;
        i = row - 1;
        j = col - 1;
        while (i >= 0 && j >= 0 && grid[i][j] == currentPlayer) {
            count++;
            i--;
            j--;
        }
        i = row + 1;
        j = col + 1;
        while (i < 6 && j < 7 && grid[i][j] == currentPlayer) {
            count++;
            i++;
            j++;
        }
        if (count >= 4) {
            return true;
        }

        // Diagonal check 2
        count = 1;
        i = row - 1;
        j = col + 1;
        while (i >= 0 && j < 7 && grid[i][j] == currentPlayer) {
            count++;
            i--;
            j++;
        }
        i = row + 1;
        j = col - 1;
        while (i < 6 && j >= 0 && grid[i][j] == currentPlayer) {
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
                grid[i][col] = currentPlayer;
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
        return -1; // Should not reach here, because isColumnFull check should be done before calling this method
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

    private void endGame() {
        JOptionPane.showMessageDialog(null, "Le joueur " + currentPlayer + " a gagné!", "Fin de la partie", JOptionPane.INFORMATION_MESSAGE);
        gameOver = true;
    }

    private void endGameWithDraw() {
        JOptionPane.showMessageDialog(null, "Match nul!", "Fin de la partie", JOptionPane.INFORMATION_MESSAGE);
        gameOver = true;
    }

    public char getCurrentPlayerSymbol() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public char[][] getGrid() {
        return grid;
    }

    public void makeAIMove() {
        if (!gameOver && player2 instanceof AIPlayer) {
            AIPlayer aiPlayer = (AIPlayer) player2;
            int col = aiPlayer.chooseMove(grid);
            dropToken(col);
        }
    }

    public void resetGame() {
        initializeGrid();
        currentPlayer = player1.getSymbol();
        gameOver = false;
    }
}

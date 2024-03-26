package Puissance4Game;
import java.util.Random;

public class AIPlayer extends Player {
    private Random random;

    public AIPlayer(String name, char symbol) {
        super(name, symbol);
        random = new Random();
    }

    public int chooseMove(char[][] grid) {
        // Simple AI logic for demonstration (randomly choose a column)
        int col;
        do {
            col = random.nextInt(grid[0].length);
        } while (!isValidMove(grid, col));

        return col;
    }

    private boolean isValidMove(char[][] grid, int col) {
        // Check if the chosen column is not full
        return grid[0][col] == ' ';
    }
}

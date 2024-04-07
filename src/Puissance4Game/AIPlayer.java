package Puissance4Game;
import java.util.Random;

public class AIPlayer extends Player {
    private static Random random;

    public AIPlayer(String name, char symbol) {
        super(name, symbol);
        random = new Random();
    }

    public static int chooseMove(char[][] grid) {
        int col;

        do {
            col = random.nextInt(grid[0].length);
        } while (grid[0][col] != ' ');

        return col;
    }


}

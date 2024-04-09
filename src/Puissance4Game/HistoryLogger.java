package Puissance4Game;

import java.io.FileWriter;
import java.io.IOException;

public class HistoryLogger {

    public static void logGameResult(String winner, String loser) {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write("Winner: " + winner + ", Loser: " + loser + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logScores(int scoreJ1, int scoreJ2, String player1Name, String player2Name) {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write(player1Name + " Score: " + scoreJ1 + ", " + player2Name + " Score: " + scoreJ2 + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

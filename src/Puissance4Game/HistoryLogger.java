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
}

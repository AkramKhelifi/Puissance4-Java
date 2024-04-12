package Puissance4Game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        connect();
    }

    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/puissance4db";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Le driver JDBC MySQL n'a pas été trouvé.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de connexion à la base de données.");
        }
    }

    public void updateOrInsertPlayerScore(String playerName, int score) {
        try {
            String queryCheck = "SELECT score FROM scores WHERE player_name = ?";
            PreparedStatement psCheck = connection.prepareStatement(queryCheck);
            psCheck.setString(1, playerName);
            ResultSet resultSet = psCheck.executeQuery();

            if (resultSet.next()) {
                int existingScore = resultSet.getInt("score");
                String queryUpdate = "UPDATE scores SET score = ? WHERE player_name = ?";
                PreparedStatement psUpdate = connection.prepareStatement(queryUpdate);
                psUpdate.setInt(1, existingScore + score);
                psUpdate.setString(2, playerName);
                psUpdate.executeUpdate();
            } else {
                String queryInsert = "INSERT INTO scores (player_name, score) VALUES (?, ?)";
                PreparedStatement psInsert = connection.prepareStatement(queryInsert);
                psInsert.setString(1, playerName);
                psInsert.setInt(2, score);
                psInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

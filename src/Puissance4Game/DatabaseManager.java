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
            // Charge le pilote JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Remplacez par votre URL de connexion, nom d'utilisateur et mot de passe
            String url = "jdbc:mysql://localhost:3306/puissance4db?serverTimezone=UTC"; // Ajout du paramètre serverTimezone pour éviter les problèmes de timezone
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
            // Vérifie si le joueur existe
            String queryCheck = "SELECT score FROM scores WHERE player_name = ?";
            PreparedStatement psCheck = connection.prepareStatement(queryCheck);
            psCheck.setString(1, playerName);
            ResultSet resultSet = psCheck.executeQuery();

            if (resultSet.next()) {
                // Mise à jour du score si le joueur existe
                int existingScore = resultSet.getInt("score");
                String queryUpdate = "UPDATE scores SET score = ? WHERE player_name = ?";
                PreparedStatement psUpdate = connection.prepareStatement(queryUpdate);
                psUpdate.setInt(1, existingScore + score);
                psUpdate.setString(2, playerName);
                psUpdate.executeUpdate();
            } else {
                // Insertion d'un nouveau joueur et son score
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

    // Fermez la connexion à la base de données lorsque vous n'en avez plus besoin
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

package org.zayanma.model;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataManager {
    private Connection conny = null;

    public DataManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            String jarPath = DataManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            String jarDirectory = new File(jarPath).getParent();
            String databasePath = jarDirectory + File.separator + "userdata.sqlite";

            if (!Files.exists(Paths.get(databasePath))) {
                System.out.println("Database doesn't exist, creating a new one...");
                createDatabase(databasePath);
            }

            conny = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            conny.setAutoCommit(false);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private void createDatabase(String databasePath) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);

            // Create the profile table
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS profiles (\n" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "profileName TEXT NOT NULL\n" +
                            ");"
            );

            // Create the tracked accounts table
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS tracked_accounts (\n" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "profileName TEXT NOT NULL,\n" +
                            "username TEXT NOT NULL, \n" +
                            "tagline TEXT NOT NULL, \n" +
                            "puuid TEXT NOT NULL \n" +
                            ");"
            );
            connection.commit();
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
            System.exit(0);
        }
    }

    public void insertNewProfile(String name) {
        // Implement insertion of a new profile
    }

    public Boolean updateTrackedPlayers() {
        // Implement updating tracked players
        return false;
    }
}

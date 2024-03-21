package org.zayanma.model;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataManager {
    private Connection conny = null;
    public DataManager() {
            try {
                Class.forName("org.sqlite.JDBC");
                conny = DriverManager.getConnection("jdbc:sqlite:../userdata.sqlite");
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
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS profiles (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "profileName TEXT NOT NULL," +
                            "trackedAccounts TEXT" +
                            ")"
            );

            // Commit the transaction
            connection.commit();
            System.out.println("Database created successfully.");
        } catch (Exception e) {
            System.err.println("Error creating database: " + e.getMessage());
            System.exit(0);
        }
    }

    public Boolean updateTrackedPlayers(){
        return false;
    }
}

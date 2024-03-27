package org.zayanma.model;
import org.zayanma.controller.MainController;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

public class DataManager {
    private Connection connection;

    APIManager apiManager = MainController.apiManager;

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

            connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            connection.setAutoCommit(false);
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
                            "fullname TEXT NOT NULL, \n" +
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

    public ArrayList<String> getTrackedPlayers(String profileName){
        ArrayList<String> accounts = new ArrayList<>();

        try {
            // Create and execute SQL query to retrieve profile names
            String sql = "SELECT fullname FROM tracked_accounts WHERE profileName ='" + profileName + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Iterate through the result set and add profile names to the list
            while (resultSet.next()) {
                String account = resultSet.getString("fullname");
                accounts.add(account);
            }

            // Close resources
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public ArrayList<String> getExistingProfiles(){
        ArrayList<String> profileNames = new ArrayList<>();

        try {
            // Create and execute SQL query to retrieve profile names
            String sql = "SELECT profileName FROM profiles";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Iterate through the result set and add profile names to the list
            while (resultSet.next()) {
                String profileName = resultSet.getString("profileName");
                profileNames.add(profileName);
            }

            // Close resources
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profileNames;
    }

    public void addNewProfile(String profileName){
        try {
            // Create and execute SQL query to insert a new profile
            String sql = "INSERT INTO profiles (profileName) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, profileName);
            preparedStatement.executeUpdate();

            // Close resources
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewAccount(String region, String username, String tagline){
        try {
            apiManager = new APIManager();
            // Create and execute SQL query to insert a new profile
            String sql = "INSERT INTO tracked_accounts (profileName, username, tagline, fullname,puuid) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, MainController.currentProfile.getValue());
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, tagline);
            preparedStatement.setString(4, username + "#" + tagline);
            preparedStatement.setString(5, apiManager.puuidFromName(region, username, tagline));
            preparedStatement.executeUpdate();

            // Close resources
            connection.commit();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProfile(String profileName){
        try {
            apiManager = new APIManager();
            String sql = "DELETE FROM profiles WHERE profileName ='" + profileName + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM tracked_accounts WHERE profileName ='" + profileName + "'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            // Close resources
            connection.commit();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(String fullname){
        try {
            apiManager = new APIManager();
            String sql = "DELETE FROM tracked_accounts WHERE fullname ='" + fullname + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            // Close resources
            connection.commit();
            preparedStatement.close();
            System.out.println("account deleted :" + fullname);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

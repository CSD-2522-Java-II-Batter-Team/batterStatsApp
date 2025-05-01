/**
 * Author Name: Batter Team
 * Date: 4/30/25
 * File Name: BatterAppDB.java
 * Last Update: 4/30/25 by Seth I.
 * Program Description: Class that contains methods to manipulate a SQlite database as well as connect to it.
 */

/*
============= CHANGE LOG =============



======================================
 */

package com.batterteam.classes;

import java.sql.*;

public class BatterAppDB {

    
    // ============ Class Variables ===========
    private static String dbFilePath = "jdbc:sqlite:playerDB.sqlite";
    
    // ============ Class Methods =============
    
    // Creates Connection object from database
    private static Connection connectToDB() {
        try {
            return DriverManager.getConnection(dbFilePath);
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }  
    }
    
    // Function displays player's stats based off their first and last name
    public static Batter buildBatterObjectFromDB(String firstName, String lastName) {
        try (PreparedStatement preparedStatement = connectToDB().prepareStatement("SELECT * FROM `Players` JOIN `Teams` ON `Players`.`player_teamID` = `Teams`.`teamID`"
                                                                                     + "WHERE `playerFirstName` = ? AND `playerLastName` = ?")) {

            // Create prepared statement searching for player by last name
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            // Initialize variables for object creation
            String playerTeam = "";
            int playerAB = 0;
            int playerRuns = 0;
            int playerHits = 0;
            int playerRunsBattedIn = 0;
            int playerDoubles = 0;
            int playerTriples = 0;
            int playerHomeRuns = 0;
            int playerTotalBases = 0;
            int playerStrikeOut = 0;
            int playerBaseOnBalls = 0;
            int playerSacrificFly = 0;
            int playerSacrificBunt = 0;
            int playerHitByPitch = 0;
            int playerLeftOnBase = 0;
            int playerStolenBaseAttempts = 0;
            int playerHomePlate = 0;
                           
            // Execute search query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Process all rows of queried data until end of file - display as a black
                while (resultSet.next()) {
                    playerTeam = resultSet.getString("teamName");
                    playerAB = resultSet.getInt("atBatAmount");
                    playerRuns = resultSet.getInt("runsAmount");
                    playerHits = resultSet.getInt("hitsAmount");
                    playerRunsBattedIn = resultSet.getInt("runsBattedInAmount");
                    playerDoubles = resultSet.getInt("doubleAmount");
                    playerTriples = resultSet.getInt("tripleAmount");
                    playerHomeRuns = resultSet.getInt("homeRunAmount");
                    playerTotalBases = resultSet.getInt("totalBasesAmount");       
                    playerStrikeOut = resultSet.getInt("strikeOutAmount");
                    playerBaseOnBalls = resultSet.getInt("baseOnBallsAmount"); 
                    playerSacrificFly = resultSet.getInt("sacrificFlyAmount"); 
                    playerSacrificBunt = resultSet.getInt("sacrificBuntAmount"); 
                    playerHitByPitch = resultSet.getInt("hitByPitchAmount"); 
                    playerLeftOnBase = resultSet.getInt("leftOnBaseAmount"); 
                    playerStolenBaseAttempts = resultSet.getInt("stolenBaseAttemptAmount"); 
                    playerHomePlate = resultSet.getInt("homePlateAmount"); 
                }
                
                Batter b = new Batter(firstName, lastName, playerTeam, playerAB, playerHits, playerHomeRuns, playerStrikeOut, playerRunsBattedIn, playerRuns,
                                        playerDoubles, playerTriples, playerTotalBases, playerBaseOnBalls, playerSacrificFly, playerSacrificBunt, 
                                            playerHitByPitch, playerLeftOnBase, playerStolenBaseAttempts, playerHomePlate);
                return b;
                
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    // Function displays player's stats based off their first and last name
    public static void viewStats(String firstName, String lastName) {
        try (PreparedStatement preparedStatement = connectToDB().prepareStatement("SELECT * FROM `Players` JOIN `Teams` ON `Players`.`player_teamID` = `Teams`.`teamID`"
                                                                                     + "WHERE `playerFirstName` = ? AND `playerLastName` = ?")) {

            // Create prepared statement searching for player by last name
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            // Execute search query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Process all rows of queried data until end of file - display as a black
                while (resultSet.next()) {
                    Console.println("First Name: " + resultSet.getString("playerFirstName"));
                    Console.println("Last Name: " + resultSet.getString("playerLastName"));
                    Console.println("Team: " + resultSet.getString("teamName"));
                    Console.println("AB: " + resultSet.getInt("atBatAmount"));
                    Console.println("R: " + resultSet.getInt("runsAmount"));
                    Console.println("H: " + resultSet.getInt("hitsAmount"));
                    Console.println("RBI: " + resultSet.getInt("runsBattedInAmount"));
                    Console.println("2B: " + resultSet.getInt("doubleAmount"));
                    Console.println("3B: " + resultSet.getInt("tripleAmount"));
                    Console.println("3B: " + resultSet.getInt("tripleAmount"));
                    Console.println("HR: " + resultSet.getInt("homeRunAmount"));
                    Console.println("TB: " + resultSet.getInt("totalBasesAmount"));       
                    Console.println("SO: " + resultSet.getInt("strikeOutAmount")); 
                    Console.println("BB: " + resultSet.getInt("baseOnBallsAmount")); 
                    Console.println("SF: " + resultSet.getInt("sacrificFlyAmount")); 
                    Console.println("SH: " + resultSet.getInt("sacrificBuntAmount")); 
                    Console.println("HBP: " + resultSet.getInt("hitByPitchAmount")); 
                    Console.println("LOB: " + resultSet.getInt("leftOnBaseAmount")); 
                    Console.println("SB-ATT: " + resultSet.getInt("stolenBaseAttemptAmount")); 
                    Console.println("HP: " + resultSet.getInt("homePlateAmount")); 
                }
                Console.println();
                
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    // Returns a String of the team a player is on based on their assigned player_teamID
    public static String getTeamFromTeamID(int teamID) {
        try (PreparedStatement preparedStatement = connectToDB().prepareStatement("SELECT `teamName` FROM `Teams` WHERE `teamID` = ?")) {

            // Create prepared statement searching for team by player_teamID
            preparedStatement.setInt(1, teamID);

            // Execute search query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Process row of queried data until end of file - display as a black
                if (resultSet.next() == false) return "TEAM NOT FOUND";
                else return resultSet.getString("teamName");             
                
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);            
        }
        return "TEAM NOT FOUND";
    }
    
}

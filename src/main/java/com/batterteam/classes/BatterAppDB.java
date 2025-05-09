/**
 * Author Name: Batter Team
 * Date: 4/30/25
 * File Name: BatterAppDB.java
 * Last Update: 5/03/25 by Seth I.
 * Program Description: Class that contains methods to manipulate a SQlite database as well as connect to it.
 */

/*
============= CHANGE LOG =============
5/03/25 - Seth I. - After updating database file I updated methods buildBatterObjectFromDBSingleGame AND viewStatsSingleGame to reflect db change
5/04/25 - Seth I. - Added several methods to display, add, and update information from database. 
5/06/25 - Seth I. - Added methods to retrieve gameIDs and dates

======================================
 */

package com.batterteam.classes;

import java.sql.*;
import java.util.ArrayList;

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
    
    // Function saves a batter object based on the batters first and last name, date of game played, and the team they're on
    public static Batter buildFullBatterObjectFromDBSingleGame(String firstName, String lastName, String gameDate, String teamName) {
        
        String queryAsString = "SELECT P.playerFirstName, P.playerLastName, T.teamName, PPGS.playerPosition, PPGS.atBatAmount, PPGS.runsAmount," + 
                                    " PPGS.hitsAmount, PPGS.runsBattedInAmount, PPGS.doubleAmount, PPGS.tripleAmount, PPGS.homeRunAmount," +
                                    " PPGS.totalBasesAmount, PPGS.strikeOutAmount, PPGS.baseOnBallsAmount, PPGS.sacrificFlyAmount," +
                                    " PPGS.sacrificBuntAmount, PPGS.hitByPitchAmount, PPGS.leftOnBaseAmount, PPGS.stolenBaseAttemptAmount, PPGS.homePlateAmount," +
                                    " PG.dateOfGame" +
                                " FROM `Players` P JOIN `Player_Per_Game_Stats` PPGS ON P.`playerID` = PPGS.`playerID`" + 
                                    " JOIN `Played_Games` PG ON PPGS.`gameID` = PG.gameID" +
                                    " JOIN `Game_Teams` GT ON PG.`gameID` = GT.`gameID`" + 
                                    " JOIN `Teams` T ON GT.`teamID` = T.`teamID`" +
                               	" WHERE P.`playerFirstName` = ? AND P.`playerLastName` = ? AND PG.dateOfGame = ? AND T.`teamName` = ?;";
        
        try (PreparedStatement preparedStatement = connectToDB().prepareStatement(queryAsString)) {

            // Create prepared statement searching for player by last name
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, gameDate);
            preparedStatement.setString(4, teamName);

            // Initialize variables for object creation
            String playerTeam = "";
            String playerPosition = "";
            String playedGameDate = "";
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
                    playerPosition = resultSet.getString("playerPosition");
                    playedGameDate = resultSet.getString("dateOfGame");
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
                
                Batter b = new Batter(firstName, lastName, playerTeam, playerPosition, playedGameDate, playerAB, playerHits, playerHomeRuns, playerStrikeOut, playerRunsBattedIn, playerRuns,
                                        playerDoubles, playerTriples, playerTotalBases, playerBaseOnBalls, playerSacrificFly, playerSacrificBunt, 
                                            playerHitByPitch, playerLeftOnBase, playerStolenBaseAttempts, playerHomePlate);
                return b;
                
            } catch (SQLException e) {
                System.out.println("buildFullBatter " + e);
            }
        } catch (SQLException e) {
            System.out.println("buildFullBatter " + e);
        }
        return null;
    }
    
    // Function saves an array of Batter objects with each Batter object contains stats of a singular batter from a singular game.
    // EXAMPLE: If Babe Ruth played 10 games...this would return 10 Babe Ruth objects each with different stats per object. 
    public static ArrayList buildFullBatterObjectsFromDBMultiGame(String firstName, String lastName, String firstGameDate, String lastGameDate, String teamName) {
        
        String queryAsString = "SELECT P.playerFirstName, P.playerLastName, T.teamName, PPGS.playerPosition, PPGS.atBatAmount, PPGS.runsAmount," +
                                    "PPGS.hitsAmount, PPGS.runsBattedInAmount, PPGS.doubleAmount, PPGS.tripleAmount, PPGS.homeRunAmount," +
                                    "PPGS.totalBasesAmount, PPGS.strikeOutAmount, PPGS.baseOnBallsAmount, PPGS.sacrificFlyAmount," +
                                    "PPGS.sacrificBuntAmount, PPGS.hitByPitchAmount, PPGS.leftOnBaseAmount, PPGS.stolenBaseAttemptAmount, PPGS.homePlateAmount," +
                                    "PG.dateOfGame" +
                                "FROM `Players` P JOIN `Player_Per_Game_Stats` PPGS ON P.`playerID` = PPGS.`playerID`" +
                                    "JOIN `Played_Games` PG ON PPGS.`gameID` = PG.gameID" +
                                    "JOIN `Game_Teams` GT ON PG.`gameID` = GT.`gameID`" +
                                    "JOIN `Teams` T ON GT.`teamID` = T.`teamID`" +
                               	"WHERE P.`playerFirstName` = 'Brent' AND P.`playerLastName` = 'Todys' AND (PG.dateOfGame BETWEEN ? AND ?) AND T.`teamName` = ?;";
        
        ArrayList batterMultiGameStats = new ArrayList<Batter>();
                
        try (PreparedStatement preparedStatement = connectToDB().prepareStatement(queryAsString)) {

            // Create prepared statement searching for player by last name
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, firstGameDate);
            preparedStatement.setString(4, lastGameDate);
            preparedStatement.setString(5, teamName);

            // Initialize variables for object creation
            String playerTeam = "";
            String playerPosition = "";
            String playedGameDate = "";
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
                    playerPosition = resultSet.getString("playerPosition");
                    playedGameDate = resultSet.getString("dateOfGame");
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
                    
                    Batter b = new Batter(firstName, lastName, playerTeam, playerPosition, playedGameDate, playerAB, playerHits, playerHomeRuns, playerStrikeOut, playerRunsBattedIn, playerRuns,
                                        playerDoubles, playerTriples, playerTotalBases, playerBaseOnBalls, playerSacrificFly, playerSacrificBunt, 
                                            playerHitByPitch, playerLeftOnBase, playerStolenBaseAttempts, playerHomePlate);
                    batterMultiGameStats.add(b);
                }                
                return batterMultiGameStats;
                
            } catch (SQLException e) {
                System.out.println("buildFullBatter " + e);
            }
        } catch (SQLException e) {
            System.out.println("buildFullBatter " + e);
        }
        return null;
    }
    
    // Function displays player's stats based off their first and last name, date of game, and team name
    public static void viewStatsSingleGame(String firstName, String lastName, String dateOfGame, String teamName) {
        
        String queryAsString = "SELECT P.playerFirstName, P.playerLastName, T.teamName, PPGS.playerPosition, PPGS.atBatAmount, PPGS.runsAmount," + 
                                    " PPGS.hitsAmount, PPGS.runsBattedInAmount, PPGS.doubleAmount, PPGS.tripleAmount, PPGS.homeRunAmount," +
                                    " PPGS.totalBasesAmount, PPGS.strikeOutAmount, PPGS.baseOnBallsAmount, PPGS.sacrificFlyAmount," +
                                    " PPGS.sacrificBuntAmount, PPGS.hitByPitchAmount, PPGS.leftOnBaseAmount, PPGS.stolenBaseAttemptAmount, PPGS.homePlateAmount," +
                                    " PG.dateOfGame" +
                                " FROM `Players` P JOIN `Player_Per_Game_Stats` PPGS ON P.`playerID` = PPGS.`playerID`" + 
                                    " JOIN `Played_Games` PG ON PPGS.`gameID` = PG.gameID" +
                                    " JOIN `Game_Teams` GT ON PG.`gameID` = GT.`gameID`" + 
                                    " JOIN `Teams` T ON GT.`teamID` = T.`teamID`" +
                               	" WHERE P.`playerFirstName` = ? AND P.`playerLastName` = ? AND PG.dateOfGame = ? AND T.`teamName` = ?;";
        
        try (PreparedStatement preparedStatement = connectToDB().prepareStatement(queryAsString)) {

            // Create prepared statement searching for player by last name
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, dateOfGame);
            preparedStatement.setString(4, teamName);

            // Execute search query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Process all rows of queried data until end of file - display as a black
                while (resultSet.next()) {
                    Console.println("First Name: " + resultSet.getString("playerFirstName"));
                    Console.println("Last Name: " + resultSet.getString("playerLastName"));
                    Console.println("Team: " + resultSet.getString("teamName"));
                    Console.println("Played Position: " + resultSet.getString("playerPosition"));
                    Console.println("Date of Game: " + resultSet.getString("dateOfGame"));
                    Console.println("AB: " + resultSet.getInt("atBatAmount"));
                    Console.println("R: " + resultSet.getInt("runsAmount"));
                    Console.println("H: " + resultSet.getInt("hitsAmount"));
                    Console.println("RBI: " + resultSet.getInt("runsBattedInAmount"));
                    Console.println("2B: " + resultSet.getInt("doubleAmount"));
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
                System.out.println("viewStats " + e);
            }
        } catch (SQLException e) {
            System.out.println("viewStats " + e);
        }
    }
    
    // Function adds a player to the database - returns the player's playerID
    public static int addBatter(Batter b, String teamName) {
        
        int teamID = getTeamIDFromTeamName(teamName);
        int newPlayerID = -1;

        String addBatterQuery = "INSERT INTO Players (playerFirstName, playerLastName, player_teamID) VALUES (?, ?, ?);";
        String getLastIDQuery = "SELECT last_insert_rowid();";

        try (PreparedStatement insertStatement = connectToDB().prepareStatement(addBatterQuery);
             PreparedStatement selectLastIDStatement = connectToDB().prepareStatement(getLastIDQuery)) {

            insertStatement.setString(1, b.getPlayerFirstName());
            insertStatement.setString(2, b.getPlayerLastName());
            insertStatement.setInt(3, teamID);
            insertStatement.executeUpdate();

            try (ResultSet resultSet = selectLastIDStatement.executeQuery()) {
                if (resultSet.next()) {
                    newPlayerID = resultSet.getInt(1);
                } else {
                    System.out.println("Error: Could not retrieve last inserted row ID.");
                }
            }

        } catch (SQLException e) {
            System.out.println("addBatter " + e);
        }

        return newPlayerID;
    }
    
    // Function adds a team to the database - returns the team's teamID
    public static int addTeam(String teamName, int teamWins, int teamLosses) {
        
        int newTeamID = -1;
        
        String addTeamQuery = "INSERT INTO Teams (teamName, teamWins, teamLosses) VALUES (?, ?, ?);";
        String getLastIDQuery = "SELECT last_insert_rowid();";
                                        
        try (PreparedStatement insertStatement = connectToDB().prepareStatement(addTeamQuery);
             PreparedStatement selectLastIDStatement = connectToDB().prepareStatement(getLastIDQuery)) {
            
            // Populate Insert Statement with Team Variables
            insertStatement.setString(1, teamName);
            insertStatement.setInt(2, teamWins);
            insertStatement.setInt(3, teamLosses);
            insertStatement.executeUpdate();
                     
            // Locate the row/id of the newely created team
            try (ResultSet resultSet = selectLastIDStatement.executeQuery()) {
                if (resultSet.next()) {
                    newTeamID = resultSet.getInt(1);
                } else {
                    System.out.println("Error: Could not retrieve last inserted row ID.");
                }
            }
     
        } catch (SQLException e) {
            System.out.println("addTeam " + e);
        }
        return newTeamID;
    } 
    
    // Function adds a Played Game to the database
    public static int addGame(String homeTeamName, String awayTeamName, String dateOfGame, String winningTeam, String venueCity, String venueState) {
        
        int homeTeamID = getTeamIDFromTeamName(homeTeamName); // Find homeTeamID from based on team name - Value will be 999 if no team was found
        int awayTeamID = getTeamIDFromTeamName(awayTeamName); // Find awayTeamID from based on team name - Value will be 999 if no team was found
        int winningTeamID = getTeamIDFromTeamName(winningTeam); // Find winningTeamID from based on team name - Value will be 999 if no team was found
        
        String addGameQuery = "INSERT INTO Played_Games (homeTeam, awayTeam, dateOfGame, winningTeam, venueCity, venueState)" + 
                                " VALUES (?, ?, ?, ?, ?, ?);";
        String getLastIDQuery = "SELECT last_insert_rowid();";
        
        try (PreparedStatement insertStatement = connectToDB().prepareStatement(addGameQuery);
             PreparedStatement selectLastIDStatement = connectToDB().prepareStatement(getLastIDQuery)) {
            
            // Populate Insert Statement with Played Game Variables
            insertStatement.setInt(1, homeTeamID);
            insertStatement.setInt(2, awayTeamID);
            insertStatement.setString(3, dateOfGame);
            insertStatement.setInt(4, winningTeamID);
            insertStatement.setString(5, venueCity);
            insertStatement.setString(6, venueState);
            insertStatement.executeUpdate();
            
            // Locate the row/id of the newely created team
            try (ResultSet resultSet = selectLastIDStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    System.out.println("Error: Could not retrieve last inserted row ID.");
                }               
            }

        } catch (SQLException e) {
            System.out.println("addGame " + e);
        }
        return 0;
    } 
    
    // Methods returns a String 
    public static String getGameDateFromGameID(int gameID) {
        
        try (PreparedStatement preparedStatement = connectToDB().prepareStatement("SELECT `dateOfGame` FROM `Played_Games` WHERE `gameID` = ?")) {

            // Create prepared statement searching for team by player_teamID
            preparedStatement.setInt(1, gameID);

            // Execute search query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Process row of queried data until end of file - display as a black
                if (resultSet.next() == false) return "GAME NOT FOUND";
                else return resultSet.getString("dateOfGame");             
                
            } catch (SQLException e) {
                System.out.println("dateFromID " + e);
            }
        } catch (SQLException e) {
            System.out.println("dateFromID " + e);            
        }
        return "GAME NOT FOUND";
    } 
    
    // Returns an array of dates for played games dates's found based on a first and last date.
    public static ArrayList getGameDateArrayInRange(String firstGameDate, String lastGameDate) {

        // Create ArrayList for array of gameIDs
        ArrayList gameDateArray = new ArrayList<String>();
        
        try (PreparedStatement preparedStatement = connectToDB().prepareStatement("SELECT `dateOfGame` FROM `Played_Games` WHERE `dateOfGame` BETWEEN ? AND ?")) {

            // Create prepared statement searching for game dates
            preparedStatement.setString(1, firstGameDate);
            preparedStatement.setString(2, lastGameDate);

            // Execute search query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                 while (resultSet.next()) {
                    String gameDateFound = resultSet.getString("dateOfGame");

                    gameDateArray.add(gameDateFound);
                }             
                
                 return gameDateArray;
            } catch (SQLException e) {
                System.out.println("getDateArray " + e);
            }
        } catch (SQLException e) {
            System.out.println("getDateArray " + e);            
        }
        return null;
    } 
    
    // Function adds a player's stats for a game to the database
    public static void addStatsPerGame(Batter b, int gameID) {
        
        String addStatsPerGameQuery = "INSERT INTO Player_Per_Game_Stats (playerID, gameID, playerPosition, atBatAmount, runsAmount, hitsAmount, runsBattedInAmount," +
                                        " baseOnBallsAmount, strikeOutAmount, leftOnBaseAmount, assistsAmount, putOutAmount, homeRunAmount, hitByPitchAmount," +
                                        " doubleAmount, tripleAmount, totalBasesAmount, homePlateAmount, onBasePercent, sacrificFlyAmount, sacrificBuntAmount, stolenBaseAttemptAmount)" +
                                      " VALUES (?, ?, ?, ?, ?, ?, ?," +
                                         " ?, ?, ?, 0, 0, ?, ?," +
                                         " ?, ?, ?, ?, 0.0, ?, ?, ?);";
        
        try (PreparedStatement preparedStatement = connectToDB().prepareStatement(addStatsPerGameQuery)) {
            
            preparedStatement.setInt(1, b.getPlayerID());
            preparedStatement.setInt(2, gameID);
            preparedStatement.setString(3, b.getPlayerPosition());
            preparedStatement.setInt(4, b.getAtBats());
            preparedStatement.setInt(5, b.getRuns());
            preparedStatement.setInt(6, b.getHits());
            preparedStatement.setInt(7, b.getRBI());
            preparedStatement.setInt(8, b.getBasesOnBalls());
            preparedStatement.setInt(9, b.getStrikeOuts());
            preparedStatement.setInt(10, b.getLeftOnBase());
            preparedStatement.setInt(11, b.getHomeRuns());
            preparedStatement.setInt(12, b.getHitByPitch());
            preparedStatement.setInt(13, b.getDoubles());
            preparedStatement.setInt(14, b.getTriples());
            preparedStatement.setInt(15, b.getTotalBases());
            preparedStatement.setInt(16, b.getHomePlate());
            preparedStatement.setInt(17, b.getSacrificFly());
            preparedStatement.setInt(18, b.getSacrificBunt());
            preparedStatement.setInt(19, b.getStolenBaseAttempts());
        
            
        } catch (SQLException e) {
            System.out.println("addStatsPerGame " + e);
        }
    
    }
    
    // Function adds a team's stats for a game to the database
    // isHomeTeam should be set to 0 for false and 1 for true
    public static void addTeamPerGame(int gameID, int teamID, int isHomeTeam) {
        String addTeamPerGameQuery = "INSERT INTO `Game_Teams`" + 
                                " VALUES (?, ?, ?);";
                                        
        try (PreparedStatement insertStatement = connectToDB().prepareStatement(addTeamPerGameQuery)) {
            
            // Populate Insert Statement with Played Game Variables
            insertStatement.setInt(1, gameID);
            insertStatement.setInt(2, teamID);
            insertStatement.setInt(3, isHomeTeam);
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("addTeamPerGame " + e);
        }
    }  

        // Function updates database with player stats for a game based on a player's ID and a game's ID
    public static void updateStats(int playerID, int gameID, String playerPosition, int atBatAmount, int runsAmount, int hitsAmount, int runsBattedInAmount, 
                                        int baseOnBallsAmount, int strikeOutAmount, int leftOnBaseAmount, int assistsAmount, int putOutAmount, int homeRunAmount,
                                            int hitByPitchAmount, int doubleAmount, int tripleAmount, int totalBasesAmount, int homePlateAmount, double onBasePercent,
                                                int sacrificFlyAmount, int sacrificBuntAmount, int stolenBaseAttemptAmount) {
        
        String updateStatsQuery = "UPDATE Player_Per_Game_Stats" +
                                    " SET playerPosition = ?," +
                                    " atBatAmount = ?," +
                                    " runsAmount = ?," +
                                    " hitsAmount = ?," +
                                    " runsBattedInAmount = ?," +
                                    " baseOnBallsAmount = ?," +
                                    " strikeOutAmount = ?," +
                                    " leftOnBaseAmount = ?," +
                                    " assistsAmount = ?," +
                                    " putOutAmount = ?," +
                                    " homeRunAmount = ?," +
                                    " hitByPitchAmount = ?," +
                                    " doubleAmount = ?," +
                                    " tripleAmount = ?," +
                                    " totalBasesAmount = ?," +
                                    " homePlateAmount = ?," +
                                    " onBasePercent = ?," +
                                    " sacrificFlyAmount = ?," +
                                    " sacrificBuntAmount = ?," +
                                    " stolenBaseAttemptAmount = ?" +
                                    " WHERE playerID = ? AND gameID = ?";                                      
        
        try (PreparedStatement preparedStatement = connectToDB().prepareStatement(updateStatsQuery)) {
            
            // Populate Insert Statement with Played Game Variables
            preparedStatement.setString(1, playerPosition);
            preparedStatement.setInt(2, atBatAmount);
            preparedStatement.setInt(3, runsAmount);
            preparedStatement.setInt(4, hitsAmount);
            preparedStatement.setInt(5, runsBattedInAmount);
            preparedStatement.setInt(6, baseOnBallsAmount);
            preparedStatement.setInt(7, strikeOutAmount);
            preparedStatement.setInt(8, leftOnBaseAmount);
            preparedStatement.setInt(9, assistsAmount);
            preparedStatement.setInt(10, putOutAmount);
            preparedStatement.setInt(11, homeRunAmount);
            preparedStatement.setInt(12, hitByPitchAmount);
            preparedStatement.setInt(13, doubleAmount);
            preparedStatement.setInt(14, tripleAmount);
            preparedStatement.setInt(15, totalBasesAmount);
            preparedStatement.setInt(16, homePlateAmount);
            preparedStatement.setDouble(17, onBasePercent);
            preparedStatement.setDouble(18, sacrificFlyAmount);
            preparedStatement.setDouble(19, sacrificBuntAmount);
            preparedStatement.setDouble(20, stolenBaseAttemptAmount);
            preparedStatement.setInt(21, playerID);
            preparedStatement.setInt(22, gameID);
                        
            // Run prepared statement
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("updateStats " + e);
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
                System.out.println("getTeamFromID " + e);
            }
        } catch (SQLException e) {
            System.out.println("getTeamFromID " + e);            
        }
        return "TEAM NOT FOUND";
    }
    
    // Returns the integer of the teamID a player is on based on a given team name
    public static int getTeamIDFromTeamName(String teamName) {
        try (PreparedStatement preparedStatement = connectToDB().prepareStatement("SELECT `teamID` FROM `Teams` WHERE `teamName` = ?")) {

            // Create prepared statement searching for team by player_teamID
            preparedStatement.setString(1, teamName);

            // Execute search query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Process row of queried data until end of file - display as a black
                if (resultSet.next() == false) return 999;
                else return resultSet.getInt("teamID"); 
                
            } catch (SQLException e) {
                System.out.println("getTeamIDFromName " + e);
            }
        } catch (SQLException e) {
            System.out.println("getTeamIDFromName " + e);            
        }
        return 999;
    }
    
}



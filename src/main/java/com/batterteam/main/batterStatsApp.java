/**
 * Author Name: Batter Team
 * Date: 4/25/25
 * File Name: batterStatsApp.java
 * Last Update: Seth I. - 5/7/25
 * Program Description: Main file AND GUI for batterStatsApp.
 */

/*
============= CHANGE LOG =============
Seth I. - 4/25/25 - created main file structure for team to add onto. The below code is an example javaFX code provided by NetBeans
Lillian H. - 4/27/25 - added basic GUI structure created with Tiffany W
Lillian H. - 4/29/25 - added finished GUI layout, minus the scene for viewing reports and logic for submitting stats
Seth I. - 4/30/25 - Added import statement for classes package.
Lillian H. - 5/1/2024 - Implemented Tiffany W's report scene layout and made a few modifications to GUI
Seth I. - 5/7/25 - Resolving issue where report wasn't properly displaying to user
Lillian H - 5/8/2025 - Added data validation
======================================
 */

package com.batterteam.main;

import java.util.Arrays;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import com.batterteam.classes.*;
import java.time.LocalDate;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Region;



/**
 * JavaFX batterStatsApp
 */
public class batterStatsApp extends Application {

    private Stage stage;
    private Scene menuScene;
    private Scene entryScene;
    private Scene reportScene;

    private TextField firstNameField = new TextField();
    private TextField lastNameField = new TextField();
    private TextField teamField = new TextField();
    private TextField atBatsField = new TextField();
    private TextField hitsField = new TextField();
    private TextField doublesField = new TextField();
    
    private TextField triplesField = new TextField();
    private TextField homeRunsField = new TextField();
    private TextField strikeOutsField = new TextField();
    
    private TextField walksField = new TextField();
    private TextField rbiField = new TextField();
    private DatePicker gameDatePicker = new DatePicker();
    private TextField oppTeamField = new TextField();
    private TextField cityField = new TextField();
    private TextField runsField = new TextField();
    private TextField baseOnBallsField = new TextField();
    private TextField sacrificeFliesField = new TextField();
    private TextField sacrificeBuntsField = new TextField();
    private TextField hitByPitchField = new TextField();
    private TextField totalBasesField = new TextField();
    private TextField leftOnBasesField = new TextField();
    private TextField stolenBasesField = new TextField();
    private TextField homePlatesField = new TextField();
    private ComboBox<String> stateCombo = new ComboBox<>();
    private ToggleGroup winnerGroup = new ToggleGroup();
    TextField reportFNameField = new TextField();
    TextField reportLNameField = new TextField();
    TextField reportTeamField = new TextField();
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Batter Stats Manager");

        menuScene = createMenuScene();
        entryScene = createEntryScene();
        reportScene = createReportScene();

        stage.setScene(menuScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        launch(args);
    }

    // Method to create main menu
    private Scene createMenuScene() {
        // Title
        Text title = new Text("Batter Stats Manager");
        title.setFont(Font.font("Arial", 24));
        
        // Main menu buttons
        Button enterStatsBtn = new Button("Enter Batter Stats");
        Button viewGameReportBtn = new Button("View Game Report");
        Button helpBtn = new Button("Help");
        Button exitBtn = new Button("Exit");

        // Design
        enterStatsBtn.setMinWidth(200);
        viewGameReportBtn.setMinWidth(200);
        helpBtn.setMinWidth(200);
        exitBtn.setMinWidth(200);

        // Page layout
        VBox menuBox = new VBox(15, title, enterStatsBtn, viewGameReportBtn, helpBtn, exitBtn);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(40));

        // Exit the app
        exitBtn.setOnAction(e -> stage.close());

        // Switch to data entry scene
        enterStatsBtn.setOnAction(e -> {
            switchScenes(entryScene);
        });
               
        // Show an information alert 
        helpBtn.setOnAction(e -> showHelpAlert());

        // Show report scene
        viewGameReportBtn.setOnAction(e -> switchScenes(reportScene));
        
        // Scene Setup
        return new Scene(menuBox, 400, 300);
    }
    
    // Method to create the data entry scene 
    private Scene createEntryScene() {
        // Title
        Text title = new Text("Enter Batter Stats");
        title.setFont(Font.font("Arial", 24));
        
        // Buttons
        Button exitBtn = new Button("Exit");
        Button resetBtn = new Button("Reset Stats");
        Button enterBtn = new Button("Enter");
        Button returnBtn = new Button("Return to Menu");

        // Button box
        HBox buttonBox = new HBox(15, enterBtn, resetBtn, returnBtn, exitBtn);
        buttonBox.setAlignment(Pos.BOTTOM_LEFT);

        // Grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(buttonBox, 0, 16, 4, 1);

        // Instructions
        Label instructionLabel = new Label("Enter a positive integer for the fields below:");
        HBox instructionBox = new HBox(instructionLabel);
        grid.add(instructionBox, 0, 5, 4, 1);
        
        // Labels
        grid.add(new Label("First Name:"), 0, 1);
        grid.add(new Label("Last Name:"), 0, 2);
        grid.add(new Label("Batter's Team:"), 0, 3);
        grid.add(new Label("Opponent Team:"), 0, 4);
        grid.add(new Label("Date Played:"), 2, 1);
        grid.add(new Label("Venue City:"), 2, 2);
        grid.add(new Label("Venue State:"), 2, 3);
        grid.add(new Label("At Bats:"), 0, 6);
        grid.add(new Label("Runs:"), 0, 7);
        grid.add(new Label("Hits"), 0, 8);
        grid.add(new Label("Runs Batted In:"), 0, 9);
        grid.add(new Label("Doubles:"), 0, 10);
        grid.add(new Label("Triples:"), 0, 11);
        grid.add(new Label("Home Runs:"), 0, 12);
        grid.add(new Label("Strike Outs:"), 2, 6);
        grid.add(new Label("Base on Balls:"), 2, 7);
        grid.add(new Label("Sacrifice Flies:"), 2, 8);
        grid.add(new Label("Sacrifice Bunts:"), 2, 9);
        grid.add(new Label("Hit By Pitches:"), 2, 10);
        grid.add(new Label("Left on Base:"), 2, 11);
        grid.add(new Label("Total Bases:"), 0, 13);
        grid.add(new Label("Home Plates:"), 2, 13);
        grid.add(new Label("Stolen Bases:"), 2, 12);
        grid.add(new Label("Winning Team: "), 0, 14);
  
        // Format and add Text Fields
        sizeTextFields();
        grid.add(firstNameField, 1, 1);
        grid.add(lastNameField, 1, 2);
        grid.add(teamField, 1, 3);
        grid.add(oppTeamField, 1, 4);
        grid.add(atBatsField, 1, 6);
        grid.add(runsField, 1, 7);
        grid.add(hitsField, 1, 8);
        grid.add(rbiField, 1, 9);
        
        grid.add(doublesField, 1, 10);
        grid.add(triplesField, 1, 11);
        grid.add(homeRunsField, 1, 12);
        
        grid.add(totalBasesField, 1, 13);
        grid.add(cityField, 3, 2);
        grid.add(strikeOutsField, 3, 6);
        grid.add(baseOnBallsField, 3, 7);
        grid.add(sacrificeFliesField, 3, 8);
        grid.add(sacrificeBuntsField, 3, 9);
        grid.add(hitByPitchField, 3, 10);
        grid.add(leftOnBasesField, 3, 11);
        grid.add(stolenBasesField, 3, 12);
        grid.add(homePlatesField, 3, 13);

        // Date Picker
        grid.add(gameDatePicker, 3, 1);
        
        // Populate Combo Box and Add to Grid
        populateComboBox();
        grid.add(stateCombo, 3, 3);
        
        // Winning team radio buttons
        RadioButton batterTeamButton = new RadioButton("Batter's Team");
        RadioButton opponentTeamButton = new RadioButton("Opponent Team");
        batterTeamButton.setToggleGroup(winnerGroup);
        opponentTeamButton.setToggleGroup(winnerGroup);
        grid.add(batterTeamButton, 1, 14);
        grid.add(opponentTeamButton, 2, 14);

        // Exit the app
        exitBtn.setOnAction(e -> stage.close());

        // Reset the fields
        resetBtn.setOnAction(e -> clearFields());

        // Return to menu
        returnBtn.setOnAction(e -> switchScenes(menuScene));
        
        // enter the data to the database
        enterBtn.setOnAction(e -> enterInfo());

        return new Scene(grid);
    }
    
    // Method to switch scenes
    public void switchScenes(Scene scene) {
        stage.setScene(scene);
    }

    // Method to clear all fields 
    public void clearFields() {
        atBatsField.clear();
        hitsField.clear();
        runsField.clear();
        doublesField.clear();
        triplesField.clear();
        homeRunsField.clear();
        strikeOutsField.clear();
        walksField.clear();
        rbiField.clear();
        totalBasesField.clear();
        sacrificeFliesField.clear();
        sacrificeBuntsField.clear();
        hitByPitchField.clear();
        leftOnBasesField.clear();
        stolenBasesField.clear();
        homePlatesField.clear();
        baseOnBallsField.clear();
    }
    
    // Create and display the alert for the help button on the main menu
    public void showHelpAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Help");
        alert.setContentText("-Enter Batter Stats: Enter information about a batter.\n\n"
                + "-View Game Report: View statistics about a batter from a specified game.\n\n"
                + "-View Cumulative Report: View statistics about batters throughout a range of games.");
        alert.showAndWait();
    }
    
    // Populate stateCombo with state abbreviations
    public void populateComboBox() {
        String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", 
            "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA",
            "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC",
            "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT",
            "VA", "WA", "WV", "WI", "WY"};
        stateCombo.getItems().addAll(Arrays.asList(states));
    }
    
    // Format the text fields
    public void sizeTextFields() {
        firstNameField.setMaxSize(100, 100);
        lastNameField.setMaxSize(100, 100);
        teamField.setMaxSize(100, 100);
        oppTeamField.setMaxSize(100, 100);
        gameDatePicker.setMaxSize(110, 100);
        cityField.setMaxSize(100, 100);
        
        atBatsField.setMaxSize(50, 50);
        atBatsField.setAlignment(Pos.CENTER_RIGHT);
        runsField.setMaxSize(50, 50);
        hitsField.setMaxSize(50, 50);
        rbiField.setMaxSize(50, 50);
        doublesField.setMaxSize(50, 50);
        triplesField.setMaxSize(50, 50);
        homeRunsField.setMaxSize(50, 50);
        totalBasesField.setMaxSize(50, 50);
        strikeOutsField.setMaxSize(50, 50);
        baseOnBallsField.setMaxSize(50, 50);
        sacrificeFliesField.setMaxSize(50, 50);
        sacrificeBuntsField.setMaxSize(50, 50);
        hitByPitchField.setMaxSize(50, 50);
        leftOnBasesField.setMaxSize(50, 50);
        stolenBasesField.setMaxSize(50, 50);
        homePlatesField.setMaxSize(50, 50);
    }
    
    public Scene createReportScene() {
        Label titleLabel = new Label("Cumulative Player Stats");

        // First and Last name text fields
        reportFNameField.setPromptText("First Name");
        reportLNameField.setPromptText("Last Name");
        reportTeamField.setPromptText("Team Name");
        
        HBox nameBox = new HBox(10, reportFNameField, reportLNameField, reportTeamField);
        nameBox.setAlignment(Pos.CENTER);

        // Radio buttons for single or multiple games
        RadioButton singleGameRadio = new RadioButton("Single Game");
        RadioButton multipleGamesRadio = new RadioButton("Multiple Games");
        ToggleGroup group = new ToggleGroup();
        singleGameRadio.setToggleGroup(group);
        multipleGamesRadio.setToggleGroup(group);
        singleGameRadio.setSelected(true);

        HBox radioBox = new HBox(20, singleGameRadio, multipleGamesRadio);
        radioBox.setAlignment(Pos.CENTER);

        // Date pickers
        DatePicker singleGameDate = new DatePicker();
        DatePicker startDate = new DatePicker();
        DatePicker endDate = new DatePicker();
        HBox multiDateBox = new HBox(10, startDate, endDate);
        multiDateBox.setAlignment(Pos.CENTER);
        multiDateBox.setVisible(false);

        // Hide multiple-date pickers by default
        multiDateBox.setVisible(false);

        // Visibility based on selection
        singleGameRadio.setOnAction(e -> {
            singleGameDate.setVisible(true);
            multiDateBox.setVisible(false);
        });

        multipleGamesRadio.setOnAction(e -> {
            singleGameDate.setVisible(false);
            multiDateBox.setVisible(true);
        });

        // Buttons
        Button viewReportButton = new Button("View Report");
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> switchScenes(menuScene));
        
        // Error message if full name is not entered
        viewReportButton.setOnAction(e -> {
            String first = formattedWord(reportFNameField.getText());
            String last = formattedWord(reportLNameField.getText());
            String teamName = formattedWord(reportTeamField.getText());
            
            // Get teamID based on the user entered team name
            int teamID = BatterAppDB.getTeamIDFromTeamName(teamName);

            // Display an alert is any text field is empty
            if (first.isEmpty() || last.isEmpty()) {
                showAlert("Enter both first and last name.");
                return;
            } 
            if (teamName.isEmpty()) {
                showAlert("Enter a team name.");
                return;
            }

            // Initalize message used in report
            String message = "";

            // ==== FOR SINGLE GAME DATE ====
            if (singleGameRadio.isSelected()) {
                LocalDate date = singleGameDate.getValue();
                if (date == null) {
                    showAlert("Select a game date.");
                    return;
                }

                
                Batter searchedPlayer = new Batter(first, last, teamID);

                if (searchedPlayer == null) {
                    showAlert("No stats found for " + first + " " + last + " on " + date + ".");
                    return;
                }

                message = Batter.batterAsString(searchedPlayer, date.toString());

            // ==== FOR MULTIPLE GAME DATES ====
            } else {
                LocalDate start = startDate.getValue();
                LocalDate end = endDate.getValue();
                if (start == null || end == null) {
                    showAlert("Select a start and end date.");
                    return;
                }

                Batter searchedPlayer = new Batter(first, last, teamID);

                if (searchedPlayer == null) {
                    showAlert("No stats found for " + first + " " + last + " between " + start.toString() + "and " + end.toString());
                    return;
                }

                message = Batter.batterAsString(searchedPlayer, start.toString(), end.toString());
            }

            // Show the result
            Alert popup = new Alert(Alert.AlertType.INFORMATION);
            popup.setTitle("Player Report");
            popup.setHeaderText(null);
            popup.setContentText(message);
            popup.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            popup.showAndWait();
        });




        backButton.setOnAction(e -> stage.setScene(menuScene));

        // Page layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
            titleLabel,
            nameBox,
            radioBox,
            singleGameDate,
            multiDateBox,
            viewReportButton,
            backButton
        );

        // Screen size
        return new Scene(layout, 400, 400);
    }

    private static void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    // enter batter into the database
    private void enterInfo() {
        
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Validate data
        Validation v = new Validation();
        String errorMsg = "";
        errorMsg += v.isPresent(firstNameField.getText(), "First Name");
        errorMsg += v.isPresent(lastNameField.getText(), "Last Name");
        errorMsg += v.isPresent(teamField.getText(), "Batter's Team");
        String state = stateCombo.getSelectionModel().getSelectedItem();
        if (state == null) {
            errorMsg += "State is required.\n";
        }
        errorMsg += v.isPresent(oppTeamField.getText(), "Opponent Team");
        LocalDate dateOfGame = gameDatePicker.getValue();
        if (dateOfGame == null) {
            errorMsg += "Date is required. Check formatting.\n";
        } 
        errorMsg += v.isPresent(cityField.getText(), "Venue City");
        errorMsg += v.isInteger(atBatsField.getText(), "At Bats");
        errorMsg += v.isInteger(runsField.getText(), "Runs");
        errorMsg += v.isInteger(hitsField.getText(), "Hits");
        errorMsg += v.isInteger(rbiField.getText(), "RBI");
        errorMsg += v.isInteger(doublesField.getText(), "Doubles");
        errorMsg += v.isInteger(triplesField.getText(), "Triples");
        errorMsg += v.isInteger(homeRunsField.getText(), "Home Runs");
        errorMsg += v.isInteger(totalBasesField.getText(), "Total Bases");
        errorMsg += v.isInteger(strikeOutsField.getText(), "Strike Outs");
        errorMsg += v.isInteger(baseOnBallsField.getText(), "Base on Balls");
        errorMsg += v.isInteger(sacrificeFliesField.getText(), "Sacrifice Flies");
        errorMsg += v.isInteger(sacrificeBuntsField.getText(), "Sacrifice Bunts");
        errorMsg += v.isInteger(hitByPitchField.getText(), "Hit By Pitch");
        errorMsg += v.isInteger(leftOnBasesField.getText(), "Left on Bases");
        errorMsg += v.isInteger(stolenBasesField.getText(), "Stolen Bases");
        errorMsg += v.isInteger(homePlatesField.getText(), "Home Plates");
        
        if (errorMsg.isEmpty()) {
            // Batter and game variables
            String firstName = formattedWord(firstNameField.getText());
            String lastName = formattedWord(lastNameField.getText());
            String batterTeam = formattedWord(teamField.getText());
            String opponentTeam = formattedWord(oppTeamField.getText());
            String formattedDateOfGame = dateOfGame.format(df);
            String gameCity = formattedWord(cityField.getText());
            String gameState = stateCombo.getSelectionModel().getSelectedItem(); 

            int atBats = Integer.parseInt(atBatsField.getText());
            int runs = Integer.parseInt(runsField.getText());
            int hits = Integer.parseInt(hitsField.getText());
            int rbi = Integer.parseInt(rbiField.getText());        
            int doubles = Integer.parseInt(doublesField.getText());
            int triples = Integer.parseInt(triplesField.getText());
            int homeRuns = Integer.parseInt(homeRunsField.getText());   
            int totalBases = Integer.parseInt(totalBasesField.getText());
            int strikeOuts = Integer.parseInt(strikeOutsField.getText());
            int baseOnBalls = Integer.parseInt(baseOnBallsField.getText());
            int sacrificeFlies = Integer.parseInt(sacrificeFliesField.getText());
            int sacrificeBunts = Integer.parseInt(sacrificeBuntsField.getText());
            int hitByPitch = Integer.parseInt(hitByPitchField.getText());
            int leftOnBase = Integer.parseInt(leftOnBasesField.getText());
            int stolenBases = Integer.parseInt(stolenBasesField.getText());
            int homePlates = Integer.parseInt(homePlatesField.getText());
            RadioButton selectedButton = (RadioButton) winnerGroup.getSelectedToggle();
            String winner = selectedButton.getText();
            
            // create batter object with variables above
            Batter batter = new Batter(firstName, lastName, batterTeam, "Batter", 
                    dateOfGame, atBats, hits, homeRuns, strikeOuts, rbi, runs, doubles,
                    triples, totalBases, baseOnBalls, sacrificeFlies, sacrificeBunts, 
                    hitByPitch, leftOnBase, stolenBases, homePlates);
            
            // check if the batters team or opponent is currently in the database or not
            // if not, add the team first
            int teamExistsBatterTeam = BatterAppDB.getTeamIDFromTeamName(batterTeam);
            int teamExistsOppTeam = BatterAppDB.getTeamIDFromTeamName(opponentTeam);
            int newBatterTeamID;
            int newOppTeamID;
            
            if (teamExistsBatterTeam == 999) {
                newBatterTeamID = BatterAppDB.addTeam(batterTeam, 0, 0);
                teamExistsBatterTeam = newBatterTeamID;
            }
            
            if (teamExistsOppTeam == 999) {
                newOppTeamID = BatterAppDB.addTeam(opponentTeam, 0, 0);
                teamExistsOppTeam = newOppTeamID;
            }
            
            // add the batter to the DB
            BatterAppDB.addBatter(batter, batterTeam);
            
            // add the game to the DB based on who won
            int gameID;
            if (winner.equals("Batter's Team")) {
                gameID = BatterAppDB.addGame(batterTeam, opponentTeam, dateOfGame, batterTeam, gameCity, gameState);
                BatterAppDB.addTeamPerGame(gameID, teamExistsBatterTeam, 1);
                BatterAppDB.addTeamPerGame(gameID, teamExistsOppTeam, 0);
            } else {
                gameID = BatterAppDB.addGame(batterTeam, opponentTeam, dateOfGame, opponentTeam, gameCity, gameState);
                BatterAppDB.addTeamPerGame(gameID, teamExistsBatterTeam, 0);
                BatterAppDB.addTeamPerGame(gameID, teamExistsOppTeam, 1);
            }
            
            Console.println("Found GameID: " + gameID);
            Console.println("Found teamExistsBatterTeam: " + teamExistsBatterTeam);
            Console.println("Found teamExistsOppTeam: " + teamExistsOppTeam);
            
            // add the batter's stats to the DB
            BatterAppDB.addStatsPerGame(batter, gameID);
                    
            // TODO: Clear previously entered numbers and alert users that information was updated to database.
            // TODO: Figure out why stats per game aren' being added
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid/Missing Data");
            alert.setContentText(errorMsg);
            alert.showAndWait();
        }
    }

    // Capitalize and format every word in the string
    public String formattedWord(String str) {
        // Trim input
        String[] words = str.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        
        for (String word: words) {
            if (word.length() > 0) {
                // Capitalize first letter and append remainder
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        
        return result.toString().trim();
    }
}

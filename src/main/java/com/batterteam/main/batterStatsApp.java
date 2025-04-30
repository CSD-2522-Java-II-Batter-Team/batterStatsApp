/**
 * Author Name: Batter Team
 * Date: 4/25/25
 * File Name: batterStatsApp.java
 * Last Update: Lillian H. - 4/29/25
 * Program Description: Main file AND GUI for batterStatsApp.
 */

/*
============= CHANGE LOG =============
Seth I. - 4/25/25 - created main file structure for team to add onto. The below code is an example javaFX code provided by NetBeans
Lillian H. - 4/27/25 - added basic GUI structure created with Tiffany W
Lillian H. - 4/29/25 - added finished GUI layout, minus the scene for viewing reports and logic for submitting stats
======================================
 */

package com.batterteam.main;

import java.util.Arrays;
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


/**
 * JavaFX batterStatsApp
 */
public class batterStatsApp extends Application {

    private Stage stage;
    private Scene menuScene;
    private Scene entryScene;

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
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Batter Stats Manager");

        menuScene = createMenuScene();
        entryScene = createEntryScene();

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
        Button viewCumulativeReportBtn = new Button("View Cumulative Report");
        Button helpBtn = new Button("Help");
        Button exitBtn = new Button("Exit");

        // Design
        enterStatsBtn.setMinWidth(200);
        viewGameReportBtn.setMinWidth(200);
        viewCumulativeReportBtn.setMinWidth(200);
        helpBtn.setMinWidth(200);
        exitBtn.setMinWidth(200);

        // Page layout
        VBox menuBox = new VBox(15, title, enterStatsBtn, viewGameReportBtn, viewCumulativeReportBtn, helpBtn, exitBtn);
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
        grid.add(buttonBox, 0, 15, 4, 1);

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

        // Exit the app
        exitBtn.setOnAction(e -> stage.close());

        // Reset the fields
        resetBtn.setOnAction(e -> clearFields());

        // Return to menu
        returnBtn.setOnAction(e -> switchScenes(menuScene));

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
        gameDatePicker.setMaxSize(100, 100);
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
}

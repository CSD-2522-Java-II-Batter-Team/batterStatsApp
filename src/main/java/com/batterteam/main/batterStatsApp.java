/**
 * Author Name: Batter Team
 * Date: 4/25/25
 * File Name: batterStatsApp.java
 * Last Update: Lillian H. - 4/27/25
 * Program Description: Main file AND GUI for batterStatsApp.
 */

/*
============= CHANGE LOG =============
Seth I. - 4/25/25 - created main file structure for team to add onto. The below code is an example javaFX code provided by NetBeans
Lillian H. - 4/27/25 - added basic GUI structure created with Tiffany W

======================================
 */

package com.batterteam.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
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

    private TextField nameField = new TextField();
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
        Button resetBtn = new Button("Reset");
        Button exitBtn = new Button("Exit");

        // Design
        enterStatsBtn.setMinWidth(200);
        viewGameReportBtn.setMinWidth(200);
        viewCumulativeReportBtn.setMinWidth(200);
        helpBtn.setMinWidth(200);
        resetBtn.setMinWidth(200);
        exitBtn.setMinWidth(200);

        // Page layout
        VBox menuBox = new VBox(15, title, enterStatsBtn, viewGameReportBtn, viewCumulativeReportBtn, helpBtn, resetBtn, exitBtn);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(40));

        // Exit the app
        exitBtn.setOnAction(e -> stage.close());

        // Switch to data entry scene
        enterStatsBtn.setOnAction(e -> {
            switchScenes(entryScene);
        });

        // Scene Setup
        return new Scene(menuBox, 400, 400);
    }
    
    // Method to create the data entry scene 
    private Scene createEntryScene() {
        // Title
        Text title = new Text("Enter Batter Stats");
        title.setFont(Font.font("Arial", 24));

        // Buttons
        Button exitBtn = new Button("Exit");
        Button resetBtn = new Button("Reset");
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
        grid.add(buttonBox, 0, 8, 3, 1);

        // Labels
        grid.add(new Label("Batter's Name:"), 0, 1);
        grid.add(new Label("Batter's Team:"), 0, 2);
        grid.add(new Label("Date Played:"), 2, 1);
        grid.add(new Label("At Bats:"), 0, 4);
        grid.add(new Label("Hits:"), 0, 5);
        grid.add(new Label("Doubles:"), 0, 6);
        grid.add(new Label("Triples:"), 0, 7);
        grid.add(new Label("Home Runs:"), 2, 4);
        grid.add(new Label("Strike Outs:"), 2, 5);
        grid.add(new Label("Walks:"), 2, 6);
        grid.add(new Label("Runs Batted In:"), 2, 7);

        // Text Fields        
        grid.add(nameField, 1, 1);
        grid.add(teamField, 1, 2);
        grid.add(atBatsField, 1, 4);
        grid.add(hitsField, 1, 5);
        grid.add(doublesField, 1, 6);
        grid.add(triplesField, 1, 7);
        grid.add(homeRunsField, 3, 4);
        grid.add(strikeOutsField, 3, 5);
        grid.add(walksField, 3, 6);
        grid.add(rbiField, 3, 7);

        // Date Picker
        grid.add(gameDatePicker, 3, 1);

        // Exit the app
        exitBtn.setOnAction(e -> stage.close());

        // Reset the fields
        resetBtn.setOnAction(e -> clearFields());

        // Return to menu
        returnBtn.setOnAction(e -> switchScenes(menuScene));

        return new Scene(grid, 550, 275);
    }
    
    // Method to switch scenes
    public void switchScenes(Scene scene) {
        stage.setScene(scene);
    }

    // Method to clear all fields 
    public void clearFields() {
        nameField.clear();
        teamField.clear();
        atBatsField.clear();
        hitsField.clear();
        doublesField.clear();
        triplesField.clear();
        homeRunsField.clear();
        strikeOutsField.clear();
        walksField.clear();
        rbiField.clear();
    }
    
}
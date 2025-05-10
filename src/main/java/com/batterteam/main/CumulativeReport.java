/**
 * Author: Batter Team
 * Date: 4/30/2025
 * File Name: CumulativeReport.java
 * Last Updated By: 5/9/25 - Seth I.
 * Program Description: 
 */

/*
============= CHANGE LOG =============
Tiffany - 5/8/25 - Consolidated Scene from batterStatsApp into CumulativeReport
Seth I - 5/9/25 - Added (then commented out) code around lines 151-161 for testing of a new report message.
Seth I - 5/9/25 - Removed commented out code and then modified alert box for cumulative report to allow the user to scroll through longer reports.

======================================
 */

package com.batterteam.main;

import com.batterteam.classes.Batter;
import com.batterteam.classes.BatterAppDB;
import com.batterteam.classes.Validation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.time.LocalDate;
import javafx.scene.text.Font;
import javafx.print.PrinterJob;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.util.ArrayList;

public class CumulativeReport {
    
    private static String printReport = "";

    public static Scene getScene(Stage primaryStage, Scene mainMenuScene) {
        Label titleLabel = new Label("📊 Cumulative Player Stats");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setStyle("-fx-font-weight: bold;");

        // First and Last name text fields
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        firstNameField.setPrefWidth(120);
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        lastNameField.setPrefWidth(120);
        TextField teamNameField = new TextField();
        teamNameField.setPromptText("Team Name");
        teamNameField.setPrefWidth(150);
        
        HBox nameBox = new HBox(10, firstNameField, lastNameField, teamNameField);
        nameBox.setAlignment(Pos.CENTER);
        
        Label nameLabel = new Label("Player Info:");
        nameLabel.setStyle("-fx-font-weight: bold");
        nameLabel.setAlignment(Pos.CENTER);
        
        VBox nameSection = new VBox(5, nameLabel, nameBox);
        nameSection.setAlignment(Pos.CENTER);

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
        
        Label dateLabel = new Label("Game Date(s):");
        dateLabel.setStyle("-fx-font-weight: bold");
        dateLabel.setAlignment(Pos.CENTER);

        HBox singleDateBox = new HBox(singleGameDate);
        singleDateBox.setAlignment(Pos.CENTER);

        VBox dateSection = new VBox(10, dateLabel, radioBox, singleDateBox, multiDateBox);
        dateSection.setAlignment(Pos.CENTER_LEFT);

        // Buttons
        Button viewReportButton = new Button("View Report");
        Button printButton = new Button("Print Report");
        Button backButton = new Button("Back");
        Button resetButton = new Button("Reset");
        
        HBox buttonBox = new HBox(15, viewReportButton, printButton, resetButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));
        
        // Error message if full name is not entered
        viewReportButton.setOnAction(e -> {
            String first = firstNameField.getText().trim();
            String last = lastNameField.getText().trim();
            String teamName = teamNameField.getText().trim();
            
            // Force user entered strings to have a capital first letter and lowercase for remaining word (rule repeats with subsequent words)
            Validation v = new Validation();
            first = v.capitalizeWords(first);
            last = v.capitalizeWords(last);
            teamName = v.capitalizeWords(teamName);
            
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
            if (!BatterAppDB.checkIfTeamExists(teamName)) {
                showAlert("Team Not Found - Try a Different Team.");
                return;
            } 
            if (!BatterAppDB.checkIfPlayerExists(first, last)){
                showAlert("Player Not Found - Try a Different Player.");
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
                printReport = message;


            // ==== FOR MULTIPLE GAME DATES ====
            } else {
                LocalDate start = startDate.getValue();
                LocalDate end = endDate.getValue();
                if (start == null || end == null) {
                    showAlert("Select a start and end date.");
                    return;
                }
                if (start.isAfter(end)) {
                    showAlert("The start date must be before the end date.");
                    return;
                }

                Batter searchedPlayer = new Batter(first, last, teamID);

                if (searchedPlayer == null) {
                    showAlert("No stats found for " + first + " " + last + " between " + start.toString() + "and " + end.toString());
                    return;
                }

                message = Batter.batterAsString(searchedPlayer, start.toString(), end.toString());               
                printReport = message;

            }
            
            // Show the message in a popup window
            Alert popup = new Alert(Alert.AlertType.NONE);
            popup.setTitle("Player Report");         
            TextArea txtArea =  new TextArea(message);
            txtArea.setEditable(false);  
            txtArea.setPrefWidth(400);
            txtArea.setPrefHeight(350);
            popup.getDialogPane().setContent(txtArea);
            popup.getButtonTypes().addAll(ButtonType.OK);
            popup.setHeaderText(null);
            popup.showAndWait();
        });
        
        printButton.setOnAction(e -> {
            if (printReport.isEmpty()) {
                showAlert("Please generate a report before printing.");
                return;
            }

            //Print Area
            Text text = new Text(printReport);
            text.setWrappingWidth(500);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setPadding(new Insets(20));


            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(primaryStage)) {
                boolean success = job.printPage(textFlow);
                if (success) {
                    job.endJob();
                } else {
                    showAlert("Failed to print the report.");
                }
            }
        });


        resetButton.setOnAction(e -> {
            firstNameField.clear();
            lastNameField.clear();
            teamNameField.clear();
            singleGameDate.setValue(null);
            startDate.setValue(null);
            endDate.setValue(null);
        });

        backButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));

        // Page layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
            titleLabel,
            nameSection,
            dateSection,
            buttonBox
        );

        // Screen size
        return new Scene(layout, 400, 400);
    }

    private static void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

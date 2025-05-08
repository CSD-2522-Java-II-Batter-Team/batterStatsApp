/**
 * Author: Batter Team
 * Date: 4/30/2025
 * File Name: CumulativeReport.java
 * Last Updated By: Tiffany W.
 * Program Description: 
 */

/*
============= CHANGE LOG =============
TODO: Add reset feature to clear whatever user entered after they've generated a report

======================================
 */

package com.batterteam.main;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.time.LocalDate;

public class CumulativeReport {

    public static Scene getScene(Stage primaryStage, Scene mainMenuScene) {
        Label titleLabel = new Label("Cumulative Player Stats");

        // First and Last name text fields
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        
        HBox nameBox = new HBox(10, firstNameField, lastNameField);
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
        
        // Error message if full name is not entered
        viewReportButton.setOnAction(e -> {
            String first = firstNameField.getText().trim();
            String last = lastNameField.getText().trim();

            if (first.isEmpty() || last.isEmpty()) {
                showAlert("Enter both first and last name.");
                return;
            }

            String message;

            // Error message if date is not selected
            if (singleGameRadio.isSelected()) {
                LocalDate date = singleGameDate.getValue();
                if (date == null) {
                    showAlert("Select a game date.");
                    return;
                }

                //Format for report
                if (first.equalsIgnoreCase("Test") && last.equalsIgnoreCase("Player")) {
                    message = "Showing stats for " + first + " " + last +
                              "\nGame Date: " + date +
                              "\nHits: 2, AVG: .400";
                } else {
                    message = "No stats found for " + first + " " + last + " on " + date + ".";
                }
            } else {
                LocalDate start = startDate.getValue();
                LocalDate end = endDate.getValue();
                if (start == null || end == null) {
                    showAlert("Select a start and end date.");
                    return;
                }

                // Testing report pop-up
                if (first.equalsIgnoreCase("Test") && last.equalsIgnoreCase("Player")) {
                    message = "Cumulative stats for " + first + " " + last +
                              "\nFrom " + start + " to " + end +
                              "\nHits: 12, AVG: .375";
                } else {
                    message = "No stats found for " + first + " " + last +
                              " between " + start + " and " + end + ".";
                }
            }

            // Show the message in a popup window
            Alert popup = new Alert(Alert.AlertType.INFORMATION);
            popup.setTitle("Player Report");
            popup.setHeaderText(null);
            popup.setContentText(message);
            popup.showAndWait();
        });


        backButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));

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
}

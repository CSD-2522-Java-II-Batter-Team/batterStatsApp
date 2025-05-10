/**
 * Author: Batter Team
 * Date: 5/9/2025
 * File Name: GameReport.java
 * Last Updated By: 5/9/25 - Tiffany
 * Program Description: Displays a game report of batter stats
 */

/*
============= CHANGE LOG =============



======================================
 */

package com.batterteam.main;

import com.batterteam.classes.Batter;
import com.batterteam.classes.BatterAppDB;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class GameReport {

    public static Scene getScene(Stage primaryStage, Scene mainMenuScene) {
        Label titleLabel = new Label("📄 Game Report for Batters");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        DatePicker gameDate = new DatePicker();
        gameDate.setPromptText("Select Date");

        HBox inputBox = new HBox(10, gameDate);
        inputBox.setAlignment(Pos.CENTER);

        TextArea reportArea = new TextArea();
        reportArea.setEditable(false);
        reportArea.setWrapText(true);
        reportArea.setPrefRowCount(15);
        reportArea.setStyle("-fx-font-family: 'Courier New';");


        Button viewBtn = new Button("Generate Report");
        Button printBtn = new Button("Print Report");
        Button saveBtn = new Button("Save to File");
        Button backBtn = new Button("Back");

        HBox buttonBox = new HBox(10, viewBtn, printBtn, saveBtn, backBtn);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, titleLabel, inputBox, reportArea, buttonBox);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        final StringBuilder lastReport = new StringBuilder();

        viewBtn.setOnAction(e -> {

            LocalDate date = gameDate.getValue();
            ArrayList<Batter> batters = BatterAppDB.buildBatterTeamObjectsFromDBSingleGame(date.toString());

            if (batters == null || batters.isEmpty()) {
                reportArea.setText("No stats found for any batters on " + date);
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Game Report for a Baseball Game on ").append(date).append("\n\n");
            sb.append(String.format("%-14s%-14s%4s%4s%5s%8s%8s%8s%6s%5s\n",
                "First Name", "Last Name", "AB", "H", "TB", "AVG", "SLG", "OBP", "RBI", "R"));
            sb.append("=".repeat(78)).append("\n");

            for (Batter b : batters) {
                    sb.append(String.format("%-14s%-14s%4d%4d%5d%8.3f%8.3f%8.3f%6d%5d\n",
                        b.getPlayerFirstName(), b.getPlayerLastName(),
                        b.getAtBats(), b.getHits(), b.getTotalBases(),
                        b.getBattingAvg(), b.getSluggingAmount(), b.getOnBasePerc(),
                        b.getRBI(), b.getRuns()));
            }

            lastReport.setLength(0);
            lastReport.append(sb);
            reportArea.setText(sb.toString());

        });

        printBtn.setOnAction(e -> {
            if (lastReport.length() == 0) {
                showAlert("Please generate a report first.");
                return;
            }

            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(primaryStage)) {
                boolean success = job.printPage(reportArea);
                if (success) {
                    job.endJob();
                } else {
                    showAlert("Printing failed.");
                }
            }
        });

        saveBtn.setOnAction(e -> {
            if (lastReport.length() == 0) {
                showAlert("Please generate a report first.");
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Report");
            fileChooser.setInitialFileName("TeamGameReport.txt");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

            java.io.File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(lastReport.toString());
                    showAlert("Report saved to " + file.getAbsolutePath());
                } catch (IOException ex) {
                    showAlert("Error saving file: " + ex.getMessage());
                }
            }
        });

        backBtn.setOnAction(e -> primaryStage.setScene(mainMenuScene));

        return new Scene(layout, 700, 500);
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

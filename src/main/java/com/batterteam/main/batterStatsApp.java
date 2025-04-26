/**
 * Author Name: Batter Team
 * Date: 4/25/25
 * File Name: batterStatsApp.java
 * Last Update: Seth I. - 4/25/25
 * Program Description: Main file AND GUI for batterStatsApp.
 */

/*
============= CHANGE LOG =============
Seth I. - 4/25/25 - created main file structure for team to add onto. The below code is an example javaFX code provided by NetBeans


======================================
 */

package com.batterteam.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX batterStatsApp
 */
public class batterStatsApp extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
package com.github.methmal66;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.*;
import javafx.scene.web.*;
import javafx.scene.Group;

public class SliderExample extends Application {

    // launch the application
    public void start(Stage stage) {
        try {

            // set title for the stage
            stage.setTitle("System");

            // create    a webview object
            WebView w = new WebView();

            // get the web engine
            WebEngine e = w.getEngine();

            // load a website
            e.load("http://192.168.1.108:8080");

            // create a scene
            Scene scene = new Scene(w, w.getPrefWidth(),
                    w.getPrefHeight());

            // set the scene
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    // Main Method
    public static void main(String args[]) {

        // launch the application
        launch(args);
    }
}
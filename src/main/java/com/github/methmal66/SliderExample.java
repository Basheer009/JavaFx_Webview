package com.github.methmal66;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
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
            stage.setTitle("النظام المعلوماتي");
            stage.getIcons().add(new Image("http://192.168.1.108:8080/images/icon_cid.png"));
            StackPane root = new StackPane();
            final ProgressBar progress = new ProgressBar();
            // create    a webview object
            WebView w = new WebView();

            root.getChildren().addAll(w, progress);
            // get the web engine
            WebEngine e = w.getEngine();
            w.setContextMenuEnabled(false);
            createContextMenu(w);

            // load a website
            e.load("http://192.168.1.108:8080");
            e.getLoadWorker().stateProperty().addListener(
                    new ChangeListener<Worker.State>() {
                        @Override
                        public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                            if (newState == Worker.State.SUCCEEDED) {
                                // hide progress bar then page is ready
                                progress.setVisible(false);

                            }
                        }
                    });
            // create a scene
            Scene scene = new Scene(root, w.getPrefWidth(),
                    w.getPrefHeight());

            // set the scene
            stage.setScene(scene);
            stage.setMaximized(true);

            stage.show();
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    private void createContextMenu(WebView webView) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem reload = new MenuItem("تحديث");
        MenuItem back = new MenuItem("الرجوع");
        reload.setOnAction(e -> {
            webView.getEngine().reload();
            webView.getEngine().load(webView.getEngine().getLocation());
            System.out.println(webView.getEngine().getLocation());
        });
        back.setOnAction(e -> webView.getEngine().executeScript("history.back()"));
//        reload.setOnAction(e -> webView.getEngine().executeScript("location.reload()"));
        contextMenu.getItems().addAll(reload,back);

        webView.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(webView, e.getScreenX(), e.getScreenY());
            } else {
                contextMenu.hide();
            }
        });
    }

    // Main Method
    public static void main(String args[]) {

        // launch the application
        launch(args);
    }
}
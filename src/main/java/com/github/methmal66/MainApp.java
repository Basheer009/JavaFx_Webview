package com.github.methmal66;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Set initial URL
        webEngine.load("https://www.google.com");
        webView.setContextMenuEnabled(false);

        showCustomContextMenu(webEngine,webView);


        // Create border pane to hold webview and buttons
        BorderPane root = new BorderPane();
        addButtons(root,webView,webEngine);

        // Set up scene and stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("النظام المعلوماتي");
        primaryStage.show();

    }


    private void showCustomContextMenu(WebEngine webEngine, WebView webView){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem refreshButton = new MenuItem("Refresh");
        MenuItem goBackButton = new MenuItem("Go Back");
        refreshButton.setOnAction(event -> webEngine.reload());
        goBackButton.setOnAction(event -> webEngine.getHistory().go(-1));
        contextMenu.getItems().addAll(refreshButton, goBackButton);
//        webView.setContextMenu(contextMenu);
        webView.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(webView, e.getScreenX(), e.getScreenY());
            } else {
                contextMenu.hide();
            }
        });
    }

    private void addButtons(BorderPane root, WebView webView, WebEngine webEngine){
        HBox buttonBox = new HBox();
        Button homeButton = new Button("الرئيسية");
        Button logout = new Button("خروج");
        buttonBox.getChildren().addAll(logout, homeButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        root.setTop(buttonBox);
        root.setCenter(webView);

        // Add action listeners to buttons
        homeButton.setOnAction(event -> webEngine.load("https://www.google.com"));
        logout.setOnAction(event -> webEngine.load("https://www.beautycenterusa.com"));
    }
}



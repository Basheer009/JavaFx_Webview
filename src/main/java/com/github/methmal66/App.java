package com.github.methmal66;

import java.nio.file.Files;
import java.nio.file.Path;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Beaver");

        WebView webView = new WebView();

        WebEngine webEngine = webView.getEngine();
        Path index = Path.of("src", "main", "resources", "pages", "index.html");
        String content = Files.readString(index);
        webEngine.loadContent(content);

        VBox vBox = new VBox(webView);
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
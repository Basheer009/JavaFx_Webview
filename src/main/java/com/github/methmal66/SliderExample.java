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

import java.util.Objects;
import java.util.Optional;
import java.net.NetworkInterface;
import java.net.SocketException;

public class SliderExample extends Application {

    // launch the application
    public void start(Stage stage) {
        try {

            // set title for the stage
            stage.setTitle("النظام المعلوماتي");
            stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("icon_cid.png"))));
            StackPane root = new StackPane();
            final ProgressBar progress = new ProgressBar();
            // create    a webview object
            WebView webView = new WebView();

            root.getChildren().addAll(webView, progress);
            // get the web engine
            WebEngine webEngine = webView.getEngine();
            webView.setContextMenuEnabled(false);
            createContextMenu(webView);

            // load a website
            String baseUrl = "http://192.168.1.108:8080";
            e.load(baseUrl);
            e.getLoadWorker().stateProperty().addListener(
                    new ChangeListener<Worker.State>() {
                        @Override
                        public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                            if (newState == Worker.State.SUCCEEDED) {
                                // hide progress bar then page is ready
                                progress.setVisible(false);
                                getMacAddress();

                        } else if (newState == Worker.State.FAILED) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("حدث خطاء");
                            alert.setHeaderText("حدث خطاء");
                            alert.setContentText("لا يوجد اتصال بالسيرفر! يرجى التاكد من صحة الشبكة");
                            ButtonType buttonTypeReload = new ButtonType("تحديث");
                            ButtonType buttonTypeCancel = new ButtonType("إلغاء", ButtonBar.ButtonData.CANCEL_CLOSE);
                            alert.getButtonTypes().setAll(buttonTypeReload, buttonTypeCancel);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == buttonTypeReload) {
                                e.load(baseUrl);
                                System.out.println(baseUrl);
                            } else {
                                stage.close();
                            }
                        }
                    });

            // create a scene
            Scene scene = new Scene(root, webView.getPrefWidth(),
                    webView.getPrefHeight());

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
//            webView.getEngine().reload();
//            webView.getEngine().load(webView.getEngine().getLocation());
            System.out.println(webView.getEngine().getLocation());
            getMacAddress();
            webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    // The initial page has finished loading, so you can now call reload()
                    webView.getEngine().reload();
                }
            });
        });
        back.setOnAction(e -> webView.getEngine().executeScript("history.back()"));
//        reload.setOnAction(e -> webView.getEngine().executeScript("location.reload()"));
        contextMenu.getItems().addAll(reload, back);

        webView.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(webView, e.getScreenX(), e.getScreenY());
            } else {
                contextMenu.hide();
            }
        });
    }

    private void getMacAddress(){
        try {
            NetworkInterface networkInterface = NetworkInterface.getByName("eth0");
            byte[] macAddress = networkInterface.getHardwareAddress();
            // Print the MAC address in hexadecimal format
            if (macAddress != null) {
                for (int i = 0; i < macAddress.length; i++) {
                    System.out.format("%02X%s", macAddress[i], (i < macAddress.length - 1) ? "-" : "");
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    // Main Method
    public static void main(String args[]) {

        // launch the application
        launch(args);
    }
}
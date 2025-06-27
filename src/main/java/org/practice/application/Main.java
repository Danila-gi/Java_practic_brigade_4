package org.practice.application;

import javafx.application.Application;
import javafx.stage.Stage;
import org.practice.application.view.MainWindow;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow = new MainWindow(primaryStage);
        mainWindow.show();
    }

    public static void main(String[] args) {
        System.out.println("System encoding: " + System.getProperty("file.encoding"));
        launch(args);
    }
}
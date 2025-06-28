package org.practice.application;

import javafx.application.Application;
import javafx.stage.Stage;
import org.practice.application.view.MainWindow;

public class Main extends  Application{
    @Override
    public void start(Stage primaryStage) {
        new MainWindow(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
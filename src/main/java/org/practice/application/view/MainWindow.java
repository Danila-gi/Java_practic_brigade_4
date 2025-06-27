package org.practice.application.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow {
    private final Stage stage;
    private final ToolbarView toolbarView;
    public MainWindow(Stage primaryStage) {
        this.toolbarView = new ToolbarView();
        this.stage = primaryStage;
        VBox root = new VBox(10, toolbarView.getToolbar(), toolbarView.getEdgeToolbar());
        root.setPadding(new javafx.geometry.Insets(10));
        root.setStyle("-fx-background-color: #f0f0f0;");
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Graph Editor with Vertex Tools");
        stage.setScene(scene);
        stage.show();
    }
}

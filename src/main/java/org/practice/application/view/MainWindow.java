package org.practice.application.view;

import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import org.practice.application.controller.GraphEditorController;
import org.practice.application.model.Graph;

public class MainWindow {
    private final Stage stage;
    private final Graph graph;
    private final ToolbarView toolbarView;
    private final GraphView graphView;
    private final GraphEditorController controller;

    public MainWindow(Stage primaryStage) {
        this.graph = new Graph();
        this.graphView = new GraphView();
        this.toolbarView = new ToolbarView();
        this.controller = new GraphEditorController(graph, graphView, toolbarView);
        this.stage = primaryStage;

        VBox root = new VBox(10, toolbarView.getToolbar(), graphView.getContainer(),toolbarView.getEdgeToolbar());
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #f0f0f0;");
        VBox.setVgrow(graphView.getContainer(), Priority.ALWAYS);
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Graph Editor with Vertex Tools");
        stage.setScene(scene);
        stage.show();
    }
}

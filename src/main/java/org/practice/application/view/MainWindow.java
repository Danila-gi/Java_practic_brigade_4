package org.practice.application.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import org.practice.application.controller.GraphEditorController;
import org.practice.application.model.Algorithm;
import org.practice.application.model.Graph;

public class MainWindow {
    private final Stage stage;
    private final Graph graph;
    private final ToolbarView toolbarView;
    private final GraphView graphView;
    private final GraphEditorController controller;
    private final Algorithm alg;

    public MainWindow(Stage primaryStage) {
        this.graph = new Graph();
        this.graphView = new GraphView();
        this.toolbarView = new ToolbarView();
        alg = new Algorithm(graphView);
        graph.setAlg(alg);
        this.controller = new GraphEditorController(graph, graphView, toolbarView);
        this.stage = primaryStage;
        Image icon = new Image(getClass().getResourceAsStream("/images/Bridge_1.png"));

        // Установка иконки
        primaryStage.getIcons().add(icon);

        VBox root = new VBox(10, toolbarView.getToolbar(), graphView.getContainer(),toolbarView.getEdgeToolbar());
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #f0f0f0;");
        VBox.setVgrow(graphView.getContainer(), Priority.ALWAYS);
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(
                getClass().getResource("/styles/graph-style.css").toExternalForm()
        );
        stage.setTitle("Graph Editor with Vertex Tools");
        stage.setScene(scene);
        stage.show();
    }
}

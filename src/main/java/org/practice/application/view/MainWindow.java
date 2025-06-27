package org.practice.application.view;


import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainWindow {
    private final Stage stage;
    private final GraphView graphView;
    private final ToolbarView toolbarView;

    public MainWindow(Stage primaryStage) {
        this.stage = primaryStage;
        this.graphView = new GraphView();
        this.toolbarView = new ToolbarView(graphView);
        initialize();
    }

    private void initialize() {
        VBox root = new VBox(10, toolbarView.getToolbar(), graphView.getGraphContainer(), toolbarView.getEdgeControls());
        root.setPadding(new javafx.geometry.Insets(10));
        root.setStyle("-fx-background-color: #f0f0f0;");

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Graph Editor with Vertex Tools");
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }
}
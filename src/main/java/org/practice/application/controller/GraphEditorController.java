package org.practice.application.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.practice.application.view.GraphView;
import org.practice.application.view.ToolbarView;

import java.io.File;

public class GraphEditorController {
    private final GraphView graphView;
    private final ToolbarView toolbarView;

    //TODO: 1. model map<vertex, VertexView>,
    //               map<Edge, EdgeView> ???
    //      2. add model methods graph;
    //      3. button add/remove edge

    private boolean isEditMode = true;
    private Circle firstSelected = null;

    public GraphEditorController(ToolbarView toolbarView, GraphView graphView) {
        this.toolbarView = toolbarView;
        this.graphView = graphView;

        setUpToolbarActions();
        setUpMouseHandlers();
    }

    private void setUpToolbarActions() {
        toolbarView.getButton("run").setOnAction(event -> toggleEditMode());
        toolbarView.getButton("clean").setOnAction(event -> handleCleanSurface());
        toolbarView.getButton("help").setOnAction(event -> handleShowHelp());
        toolbarView.getButton("load").setOnAction(event -> handleLoadGraph());
    }

    private void toggleEditMode() {
        isEditMode = !isEditMode;
        toolbarView.getButton("run").setText(isEditMode ? "Run ON:" : "Run OFF");
        toolbarView.getButton("addVertex").setDisable(!isEditMode);
        toolbarView.getButton("deleteVertex").setDisable(!isEditMode);

        if (!isEditMode && firstSelected == null) {
            firstSelected.setFill(Color.LIGHTBLUE);
            firstSelected = null;
        }
    }

    private void handleCleanSurface() {
        while(!graphView.getVertices().isEmpty()) {
            graphView.deleteVertex(graphView.getVertices().getFirst());
        }
    }

    private void handleShowHelp() {
        graphView.getEdges().stream().forEach(System.out::println);
        graphView.getVertices().stream().forEach(System.out::println);
        graphView.getVertexLabels().stream().forEach(System.out::println);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instruction of program");
        alert.setHeaderText("Choose mode: add vertex or remove them." +
                "\nAdd vertex by clicking your mouse on surface." +
                "\nFor removing choose right mode and click on yor vertex." +
                "\nIn down side select two vertices between which the edge needs to be removed." +
                "\nClick on RUN to see work of algorithm.");
        alert.showAndWait();
    }

    private void handleLoadGraph() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
    }

    private void setUpMouseHandlers() {
        graphView.getGraphPane().setOnMouseClicked(event -> {
            if (!isEditMode) {
                return;
            }
            if (event.getTarget() == graphView.getGraphPane()) {
                ToggleButton addVertexButton = (ToggleButton) toolbarView.getButton("addVertex");
                if (addVertexButton.isSelected()) {
                    graphView.addVertex(event.getX(), event.getY());
                }
            } else if (event.getTarget() instanceof Circle) {
                ToggleButton deleteVertexButton = (ToggleButton) toolbarView.getButton("deleteVertex");
                Circle clickedVertex = (Circle) event.getTarget();
                if (deleteVertexButton.isSelected()) {
                    graphView.deleteVertex(clickedVertex);
                } else {
                    handleVertexClick(clickedVertex);
                }
            }
        });
    }

    private  void handleVertexClick(Circle clickedVertex) {
        ToggleButton deleteVertexButton = (ToggleButton) toolbarView.getButton("deleteVertex");
        if (!isEditMode || deleteVertexButton.isSelected()) {
            return;
        }

        if (firstSelected == null) {
            firstSelected = clickedVertex;
            firstSelected.setFill(Color.ORANGE);
        } else if (firstSelected == clickedVertex) {
            firstSelected.setFill(Color.LIGHTBLUE);
            firstSelected = null;
        } else {
            graphView.connectVertices(firstSelected, clickedVertex);
            firstSelected.setFill(Color.LIGHTBLUE);
            firstSelected = null;
        }
    }
}

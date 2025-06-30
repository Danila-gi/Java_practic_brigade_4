package org.practice.application.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.practice.application.model.Graph;
import org.practice.application.view.GraphView;
import org.practice.application.view.ToolbarView;

import java.io.File;

public class GraphEditorController {
    private final Graph graph;
    private final GraphView graphView;
    private final ToolbarView toolbarView;

    private boolean isEditMode;
    private Integer firstSelectedVertexId = null;

    private final Color colorVertex;
    private final double radius;

    private final Color colorEdge;
    private final double strokeWidth;

    public GraphEditorController(Graph graph, GraphView graphView, ToolbarView toolbarView) {
        this.graph = graph;
        this.graphView = graphView;
        this.toolbarView = toolbarView;
        this.isEditMode = true;
        this.firstSelectedVertexId = null;
        this.colorVertex = Color.LIGHTBLUE;
        this.radius = 15;
        this.colorEdge = Color.BLACK;
        this.strokeWidth = 2;
        setUpToolbarActions();
        setUpMouseHandlers();
    }

    private void setUpToolbarActions() {
        toolbarView.getButton("run").setOnAction(event -> toggleEditMode());
        toolbarView.getButton("clean").setOnAction(event -> handleCleanSurface());
        toolbarView.getButton("help").setOnAction(event -> handleShowHelp());
        toolbarView.getButton("load").setOnAction(event -> handleLoadGraph());
        toolbarView.getButton("deleteVertex").setOnAction(event -> clearSelection());
        toolbarView.getButton("addEdge").setOnAction(event -> handleAddEdge());
        toolbarView.getButton("deleteEdge").setOnAction(event -> handleDeleteEdge());
    }

    private void toggleEditMode() {
        isEditMode = !isEditMode;
        toolbarView.getButton("run").setText(isEditMode ? "Run ON:" : "Run OFF");
        toolbarView.getButton("addVertex").setDisable(!isEditMode);
        toolbarView.getButton("deleteVertex").setDisable(!isEditMode);
        toolbarView.getButton("addEdge").setDisable(!isEditMode);
        toolbarView.getButton("deleteEdge").setDisable(!isEditMode);
        toolbarView.getFirstVertexField().setDisable(!isEditMode);
        toolbarView.getSecondVertexField().setDisable(!isEditMode);

        if (!isEditMode && firstSelectedVertexId != null) {
            graphView.highlight(firstSelectedVertexId, Color.LIGHTBLUE);
            firstSelectedVertexId = null;
        }
    }

    private void handleCleanSurface() {
        graph.clear();
        graphView.cleanSurface();
    }

    private void handleShowHelp() {
        graphView.showInfo();
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

    private void handleAddEdge() {
        String firstVertexText = toolbarView.getFirstVertexField().getText();
        String secondVertexText= toolbarView.getSecondVertexField().getText();

        try {
            int firstVertexId = Integer.parseInt(firstVertexText);
            int secondVertexId = Integer.parseInt(secondVertexText);
            graph.addEdge(firstVertexId, secondVertexId);
            graphView.addEdge(firstVertexId, secondVertexId, strokeWidth, colorEdge);
        } catch (NumberFormatException exception) {
            System.out.println("Enter correct digital Id vertexes");
        }
    }

    private void handleDeleteEdge() {
        String firstVertexText = toolbarView.getFirstVertexField().getText();
        String secondVertexText= toolbarView.getSecondVertexField().getText();

        try {
            int firstVertexId = Integer.parseInt(firstVertexText);
            int secondVertexId = Integer.parseInt(secondVertexText);
            graph.deleteEdge(firstVertexId, secondVertexId);
            graphView.deleteEdge(firstVertexId, secondVertexId);
        } catch (NumberFormatException exception) {
            System.out.println("Enter correct digital Id vertexes");
        }
    }

    private void setUpMouseHandlers() {
        graphView.getGraphPane().setOnMouseClicked(event -> {
            if (!isEditMode) {
                return;
            }
            if (event.getTarget() == graphView.getGraphPane()) {
                handleClickOnGraphPane(event);
            } else if (event.getTarget() instanceof Circle) {
                handleClickOnVertex(event);
            }
        });
    }

    private boolean isInsideSurface(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        double heightSurface = graphView.getGraphPane().getHeight();
        double widthSurface = graphView.getGraphPane().getWidth();

        if (x - radius < 1 || x + radius > widthSurface + 1
            || y - radius < 1 || y + radius > heightSurface + 1) {
            return false;
        }
        return true;
    }

    private void handleClickOnGraphPane(MouseEvent event) {
        ToggleButton addVertexButton = (ToggleButton) toolbarView.getButton("addVertex");
        if (addVertexButton.isSelected()) {
            if (!isInsideSurface(event)) {
                return;
            }
            int key = graph.getNextAvailableVertexId();
            graph.addVertex(key);
            graphView.addVertex(key,event.getX(), event.getY(), radius, colorVertex);
        }
    }

    private void clearSelection() {
        if (firstSelectedVertexId != null) {
            graphView.highlight(firstSelectedVertexId, Color.LIGHTBLUE);
            firstSelectedVertexId = null;
        }
    }
    private void handleClickOnVertex(MouseEvent event) {
        ToggleButton deleteVertexButton = (ToggleButton) toolbarView.getButton("deleteVertex");
        Circle clickedVertex = (Circle) event.getTarget();
        int vertexId = (Integer) clickedVertex.getUserData();
        if (deleteVertexButton.isSelected()) {
            graph.deleteVertex(vertexId);
            graphView.deleteVertex(vertexId);
            return;
        }

        if (firstSelectedVertexId == null) {
            firstSelectedVertexId = vertexId;
            graphView.highlight(firstSelectedVertexId, Color.ORANGE);
            return;
        }

        if (firstSelectedVertexId == vertexId) {
            clearSelection();
            return;
        }
        graphView.addEdge(firstSelectedVertexId, vertexId, strokeWidth, colorEdge);
        graph.addEdge(firstSelectedVertexId, vertexId);
        clearSelection();
    }

}

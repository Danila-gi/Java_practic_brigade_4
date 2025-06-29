package org.practice.application.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.practice.application.model.Edge;
import org.practice.application.model.Vertex;
import org.practice.application.view.GraphView;
import org.practice.application.view.ToolbarView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphEditorController {
    private final GraphView graphView;
    private final ToolbarView toolbarView;
    private final Map<Integer, Vertex> vertices;
    private final List<Edge> edges;;
    private boolean isEditMode;
    private Integer firstSelectedVertexId = null;

    private final Color colorVertex;
    private final double radius;

    private final Color colorEdge;
    private final double strokeWidth;

    public GraphEditorController(ToolbarView toolbarView, GraphView graphView) {
        this.toolbarView = toolbarView;
        this.graphView = graphView;
        this.vertices = new HashMap<>();
        this.edges = new ArrayList<>();
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
    }

    private void toggleEditMode() {
        isEditMode = !isEditMode;
        toolbarView.getButton("run").setText(isEditMode ? "Run ON:" : "Run OFF");
        toolbarView.getButton("addVertex").setDisable(!isEditMode);
        toolbarView.getButton("deleteVertex").setDisable(!isEditMode);

        if (!isEditMode && firstSelectedVertexId != null) {
            graphView.highlight(firstSelectedVertexId, Color.LIGHTBLUE);
            firstSelectedVertexId = null;
        }
    }

    private void handleCleanSurface() {
        vertices.clear();
        edges.clear();
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

    private void deleteAllEdgesByVertex(int vertexId) {
        List<Edge> edgesToRemove = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getFromId() == vertexId || edge.getToId() == vertexId) {
                edgesToRemove.add(edge);
            }
        }
        edges.removeAll(edgesToRemove);
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
            int key = vertices.isEmpty() ? 1 : vertices.size() + 1;
            vertices.put(key, new Vertex(key));
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
        int idVertex = (Integer) clickedVertex.getUserData();
        if (deleteVertexButton.isSelected()) {
            vertices.remove(idVertex);
            deleteAllEdgesByVertex(idVertex);
            graphView.deleteVertex(idVertex);
            return;
        }

        if (firstSelectedVertexId == null) {
            firstSelectedVertexId = idVertex;
            graphView.highlight(firstSelectedVertexId, Color.ORANGE);
            return;
        }

        if (firstSelectedVertexId == idVertex) {
            clearSelection();
            return;
        }
        graphView.addEdge(firstSelectedVertexId, idVertex, strokeWidth, colorEdge);
        edges.add(new Edge(vertices.get(firstSelectedVertexId), vertices.get(idVertex)));
        clearSelection();
    }
}

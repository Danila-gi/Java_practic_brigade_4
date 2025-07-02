package org.practice.application.controller;

import javafx.scene.paint.Color;
import org.practice.application.model.EditorStateData;
import org.practice.application.model.Graph;
import org.practice.application.view.GraphView;
import org.practice.application.view.Pair;

public class VertexController {
    private final Graph graph;
    private final GraphView graphView;
    private final double radiusVertex;

    public VertexController(EditorStateData stateData) {
        this.graph = stateData.getGraph();
        this.graphView = stateData.getGraphView();
        this.radiusVertex = stateData.getVertexRadius();
    }

    private boolean isInsideSurface(double x, double y) {
        double heightSurface = graphView.getGraphPane().getHeight();
        double widthSurface = graphView.getGraphPane().getWidth();

        return !(x - radiusVertex < 0) && !(x + radiusVertex > widthSurface)
                && !(y - radiusVertex < 0) && !(y + radiusVertex > heightSurface);
    }

    public void addVertexAt(double x, double y) {
        if (!isInsideSurface(x, y)) {
            return;
        }
        int key = graph.getNextAvailableVertexId();
        graph.addVertex(key);
        graphView.addVertex(key,x, y, radiusVertex);
    }

    public void deleteVertexAt(int vertexId) {
        graph.deleteVertex(vertexId);
        graphView.deleteVertex(vertexId);
    }

    public void highlightVertex(int vertexId, Color color) {
        graphView.highlight(vertexId, color);
    }

    public void moveVertexTo(int dragVertexId, double x, double y) {
        if (!isInsideSurface(x, y)) {
            return;
        }
        Pair<Double, Double> currentPos = graphView.getVertexPosition(dragVertexId);
        if (currentPos == null) {
            return;
        }
        graphView.moveToVertexView(dragVertexId, x, y);
    }

    public Pair<Double, Double> getVertexPosition(int vertexId) {
        return graphView.getVertexPosition(vertexId);
    }

}

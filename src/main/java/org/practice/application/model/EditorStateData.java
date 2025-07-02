package org.practice.application.model;

import javafx.scene.paint.Color;
import org.practice.application.view.GraphView;

public class EditorStateData {
    private final Graph graph;
    private final GraphView graphView;
    private final double vertexRadius;
    private final Color selectedVertexColor;
    public EditorStateData(Graph graph, GraphView graphView, double vertexRadius, Color selectedVertexColor) {
        this.graph = graph;
        this.graphView = graphView;
        this.vertexRadius = vertexRadius;
        this.selectedVertexColor = selectedVertexColor;
    }

    public Graph getGraph() {
        return graph;
    }

    public GraphView getGraphView() {
        return graphView;
    }

    public double getVertexRadius() {
        return vertexRadius;
    }

    public Color getSelectedVertexColor() {
        return selectedVertexColor;
    }
}

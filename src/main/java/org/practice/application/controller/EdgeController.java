package org.practice.application.controller;

import org.practice.application.model.EditorStateData;
import org.practice.application.model.Graph;
import org.practice.application.view.GraphView;

public class EdgeController {
    private final Graph graph;
    private final GraphView graphView;

    public EdgeController(EditorStateData stateData) {
        this.graph = stateData.getGraph();
        this.graphView = stateData.getGraphView();
    }

    public void addEdge(int firstVertexId, int secondVertexId) {
        graph.addEdge(firstVertexId, secondVertexId);
        graphView.addEdge(firstVertexId, secondVertexId);
    }

    public void addEdge(String firstVertexText, String secondVertexText) {
        try {
            int firstVertexId = Integer.parseInt(firstVertexText);
            int secondVertexId = Integer.parseInt(secondVertexText);
            graph.addEdge(firstVertexId, secondVertexId);
            graphView.addEdge(firstVertexId, secondVertexId);
        } catch (NumberFormatException exception) {
            System.out.println("Enter correct digital Id vertexes");
        }
    }

    public void deleteEdge(String firstVertexText, String secondVertexText) {
        try {
            int firstVertexId = Integer.parseInt(firstVertexText);
            int secondVertexId = Integer.parseInt(secondVertexText);
            graph.deleteEdge(firstVertexId, secondVertexId);
            graphView.deleteEdge(firstVertexId, secondVertexId);
        } catch (NumberFormatException exception) {
            System.out.println("Enter correct digital Id vertexes");
        }
    }
}

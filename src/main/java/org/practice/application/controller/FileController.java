package org.practice.application.controller;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import org.practice.application.model.*;
import org.practice.application.view.GraphView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FileController {
    private final Graph graph;
    private final GraphView graphView;
    private final double vertexRadius;

    public FileController(EditorStateData stateData) {
        this.graph = stateData.getGraph();
        this.graphView = stateData.getGraphView();
        this.vertexRadius = stateData.getVertexRadius();
    }

    public void loadGraph(File file) throws FileException {
        GraphFileReader reader = new GraphFileReader(file);
        HashMap<Vertex, ArrayList<Vertex>> mapOfGraph = reader.getGraph();
        ArrayList<Vertex> vertices = reader.getVertex();

        graph.clear();
        graphView.cleanSurface();

        Random rand = new Random();
        for (Vertex ver : vertices) {
            int x = rand.nextInt(600);
            int y = rand.nextInt(400);

            int key = graph.getNextAvailableVertexId();
            graph.addVertex(key);
            graphView.addVertex(key, x, y, vertexRadius);
        }

        for (Vertex ver1 : vertices) {
            ArrayList<Vertex> adjacency = mapOfGraph.get(ver1);
            if (adjacency != null) {
                for (Vertex ver2 : adjacency) {
                    if (ver1.getId() > ver2.getId()) {
                        graph.addEdge(ver1.getId(), ver2.getId());
                        graphView.addEdge(ver1.getId(), ver2.getId());
                    }
                }
            }
        }
    }

    public void saveGraph(String filename) throws FileException {
        graph.saveGraph(filename);
    }

    public void saveResult(String filename) throws FileException {
        graph.saveResult(filename);
        graph.clearAlg();
        for (Vertex ver : graph.getArrayOfVertex()) {
            ver.clearVertex();
        }

        for (Node node : graphView.getGraphPane().getChildren()) {
            if ("Line".equals(node.getTypeSelector())) {
                Line line = (Line) node;
                line.getStyleClass().clear();
                line.getStyleClass().add("edge");
            }
        }
    }
}

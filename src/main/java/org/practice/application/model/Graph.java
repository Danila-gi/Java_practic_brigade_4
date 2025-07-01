package org.practice.application.model;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Graph {
    private final Map<Integer, Vertex> vertices;
    private final List<Edge> edges;
    private int nextAvailableId;
    private Algorithm alg;
    public Graph() {
        this.vertices = new HashMap<>();
        this.edges = new ArrayList<>();
        this.nextAvailableId = 1;
    }

    public void setAlg(Algorithm alg) {
        this.alg = alg;
    }

    public void addVertex(int vertexId) {
        nextAvailableId = Math.max(vertexId + 1, nextAvailableId);
        vertices.put(vertexId, new Vertex(vertexId));
    }

    public void deleteVertex(int vertexId) {
        vertices.remove(vertexId);
        deleteAllEdgesByVertex(vertexId);
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

    public void addEdge(int firstVertexId, int secondVertexId) {
        if (!hasVertex(firstVertexId) || !hasVertex(secondVertexId)) {
            System.out.println("first or second vertex doesn't exist");
            return;
        }
        edges.add(new Edge(vertices.get(firstVertexId), vertices.get(secondVertexId)));
    }

    public void deleteEdge(int firstVertexId, int secondVertexId) {
        if (!hasEdge(firstVertexId, secondVertexId)) {
            System.out.println("Edge doesn't exist");
            return;
        }
        edges.removeIf(edge -> edge.getToId() == firstVertexId && edge.getFromId() == secondVertexId);
    }

    public int getNextAvailableVertexId() {
        return nextAvailableId++;
    }

    public boolean hasVertex(int vertexId) {
        return vertices.containsKey(vertexId);
    }

    public boolean hasEdge(int firstVertexId, int secondVertexId) {
        if (!hasVertex(firstVertexId) || !hasVertex(secondVertexId)) {
            return false;
        }
        Edge checkEdge = null;
        for (Edge edge : edges) {
            if (edge.getFromId() == firstVertexId &&  edge.getToId() == secondVertexId) {
                checkEdge = edge;
            }
        }
        return checkEdge != null;
    }

    public void clear() {
        vertices.clear();
        edges.clear();
        nextAvailableId = 1;
    }

    public void execute(){
        HashMap<Vertex, ArrayList<Vertex>> mapOfVertex = new HashMap<>();
        ArrayList<Vertex> arrayOfVertex = new ArrayList<>();
        for (Vertex vertex: this.vertices.values()) {
            arrayOfVertex.add(vertex);
            mapOfVertex.put(vertex, new ArrayList<>());
        }
        for (Edge edge: this.edges){
            mapOfVertex.get(edge.getFirstVertex()).add(edge.getSecondVertex());
            mapOfVertex.get(edge.getSecondVertex()).add(edge.getFirstVertex());
        }

        System.out.println(arrayOfVertex.toString());
        System.out.println(mapOfVertex.toString());

        alg.setGraph(mapOfVertex);
        alg.setVertex(arrayOfVertex);
        ArrayList<Vertex[]> result = alg.findBridges();
        System.out.println("Result:");
        for (Vertex[] pair: result){
            System.out.println(pair[0] + "--" + pair[1]);
        }

        for (Vertex vertex: arrayOfVertex){
            vertex.clearVertex();
        }
    }
}

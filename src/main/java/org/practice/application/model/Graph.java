package org.practice.application.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

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
        if (hasEdge(firstVertexId, secondVertexId)) {
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
        edges.removeIf(edge -> edge.getToId() == secondVertexId && edge.getFromId() == firstVertexId);
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
            if ((edge.getFromId() == firstVertexId &&  edge.getToId() == secondVertexId)
                || (edge.getFromId() == secondVertexId && edge.getToId() == firstVertexId)) {
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
    }

    public void saveGraph(String filename){
        try(FileWriter writer = new FileWriter(filename, false))
        {
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
            System.out.println(arrayOfVertex);
            for (int i = 0; i < arrayOfVertex.size(); i++) {
                String rowStr = "";
                int[] rowInt = new int[arrayOfVertex.size()];
                for (Vertex ver : mapOfVertex.get(arrayOfVertex.get(i))) {
                    rowInt[arrayOfVertex.indexOf(ver)] = 1;
                }
                for (int j = 0; j < rowInt.length; j++){
                    if (j == rowInt.length - 1){
                        rowStr += rowInt[j];
                        break;
                    }
                    rowStr += rowInt[j];
                    rowStr += " ";
                }
                System.out.println(rowStr);
                writer.write(rowStr);
                writer.write('\n');
            }
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public void saveResult(String filename) {
        saveGraph(filename);
        try (FileWriter writer = new FileWriter(filename, true)) {
            getRusult();
            ArrayList<Vertex[]> bridges = alg.getListBridges();
            writer.write("Result:\n");
            for (Vertex[] bridge : bridges) {
                writer.write(bridge[0] + " -- " + bridge[1] + "\n");
            }
            if (bridges.isEmpty()) {
                writer.write("No bridges");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clearAlg(){
        alg.clear();
    }

    public ArrayList<Vertex> getArrayOfVertex(){
        ArrayList<Vertex> array = new ArrayList<>();
        array.addAll(this.vertices.values());
        return array;
    }

    public void stopAlg(){
        alg.stop();
    }

    public void getRusult() {
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

        alg.setGraph(mapOfVertex);
        alg.setVertex(arrayOfVertex);
        alg.getResultFast();
    }
}

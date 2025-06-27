package org.practice.application;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        GraphFileReader reader = new GraphFileReader("src/graph.txt");

        HashMap<Vertex, ArrayList<Vertex>> graph = new HashMap<>();
        graph = reader.getGraph();

        ArrayList<Vertex> vertex = new ArrayList<Vertex>();
        vertex = reader.getVertex();

        System.out.println(vertex.toString());
        System.out.println(graph.toString());

        Algorithm alg = new Algorithm(graph, vertex);
        ArrayList<Vertex[]> result = alg.findBridges();
        System.out.println("Result:");
        for (Vertex[] pair: result){
            System.out.println(pair[0] + "--" + pair[1]);
        }
    }
}
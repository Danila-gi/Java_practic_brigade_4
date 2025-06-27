package org.danila.test;
import java.math.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HashMap<Vertex, ArrayList<Vertex>> graph = new HashMap<>();

        ArrayList<Vertex> vertex = new ArrayList<Vertex>();
        for (int i = 0; i < 7; i++){
            vertex.add(new Vertex(i + 1));
            graph.put(vertex.get(i), new ArrayList<Vertex>());
        }

        graph.get(vertex.get(0)).add(vertex.get(1));
        graph.get(vertex.get(0)).add(vertex.get(2));

        graph.get(vertex.get(1)).add(vertex.get(0));
        graph.get(vertex.get(1)).add(vertex.get(3));

        graph.get(vertex.get(2)).add(vertex.get(0));
        graph.get(vertex.get(2)).add(vertex.get(3));

        graph.get(vertex.get(3)).add(vertex.get(1));
        graph.get(vertex.get(3)).add(vertex.get(2));
        graph.get(vertex.get(3)).add(vertex.get(4));

        graph.get(vertex.get(4)).add(vertex.get(3));
        graph.get(vertex.get(4)).add(vertex.get(5));
        graph.get(vertex.get(4)).add(vertex.get(6));

        graph.get(vertex.get(5)).add(vertex.get(4));

        graph.get(vertex.get(6)).add(vertex.get(4));

        System.out.println(graph.toString());

        Algorithm alg = new Algorithm(graph, vertex);
        ArrayList<Vertex[]> result = alg.findBridges();
        System.out.println("Result:");
        for (Vertex[] pair: result){
            System.out.println(pair[0] + "--" + pair[1]);
        }
    }
}
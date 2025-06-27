package org.practice.application.model;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Graph {
    private final Map<Integer, Set<Integer>> adjacencyList = new HashMap<>();
    public Graph(File file) {

    }
    void addAdjacencyRow(Integer v, Set<Integer> adjacent) {
        adjacencyList.put(v, adjacent);
    }

}

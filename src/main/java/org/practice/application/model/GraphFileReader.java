package org.practice.application.model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphFileReader {
    private HashMap<Vertex, ArrayList<Vertex>> graph;
    private ArrayList<Vertex> vertex;
    private File file;

    public GraphFileReader(File file){
        int count_of_vertex = -1;
        this.file = file;
        this.vertex = new ArrayList<Vertex>();
        this.graph = new HashMap<Vertex, ArrayList<Vertex>>();

        if (!this.file.exists() || !this.file.isFile())
            throw new FileException("Incorrect filename or format!");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int i = 0;
            boolean readingMatrix = true;
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("Result:")) {
                    readingMatrix = false;
                    continue;
                }

                if (readingMatrix) {
                    String[] neighbours = line.split(" ");
                    if (count_of_vertex == -1) {
                        count_of_vertex = neighbours.length;
                        for (int j = 0; j < count_of_vertex; j++)
                            vertex.add(new Vertex(j + 1));
                    }
                    else{
                        if (count_of_vertex != neighbours.length)
                            throw new FileException("Incorrect matrix!");
                    }
                    graph.put(vertex.get(i), new ArrayList<Vertex>());
                    for (int j = 0; j < neighbours.length; j++){
                        if (neighbours[j].equals("1"))
                            graph.get(vertex.get(i)).add(vertex.get(j));
                    }
                    i++;
                }
            }

            if (count_of_vertex != i)
                throw new FileException("Incorrect matrix!");
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    public ArrayList<Vertex> getVertex(){
        return this.vertex;
    }

    public HashMap<Vertex, ArrayList<Vertex>> getGraph(){
        return this.graph;
    }
}

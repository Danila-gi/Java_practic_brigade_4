package org.practice.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Algorithm {
    private HashMap<Vertex, ArrayList<Vertex>> graph;
    private ArrayList<Vertex> vertex;
    private ArrayList<Vertex[]> result;

    public Algorithm(HashMap<Vertex, ArrayList<Vertex>> graph, ArrayList<Vertex> vertex){
        this.graph = graph;
        this.result = new ArrayList<Vertex[]>();
        this.vertex = vertex;
    }

    public ArrayList<Vertex[]> findBridges(){
        this.firstDFS();

        System.out.println("After first DFS:");
        System.out.println(graph.toString());

        this.secondDFS();
        System.out.println("After second DFS:");
        for (int i = 0; i < vertex.size(); i++){
            System.out.println(vertex.get(i).toString() + " - " + vertex.get(i).getColor());
        }

        for (Vertex ver1: vertex){
            for (Vertex ver2: graph.get(ver1)){
                if (ver1.getColor() != ver2.getColor()){
                    Vertex[] res = new Vertex[]{ver1, ver2};
                    result.add(res);
                }
            }
        }
        return this.result;
    }

    private void firstDFS(){
        Stack<Vertex> stack = new Stack<Vertex>();
        Vertex nextVertex = this.findUnviewedVertexFisrtDFS();
        OUTER_LOOP:
        while (nextVertex != null){
            stack.push(nextVertex);
            nextVertex.firstDFS();
            while (!stack.empty()) {
                for (int i = 0; i < graph.get(nextVertex).size(); i++) {
                    if (!graph.get(nextVertex).get(i).getStateFisrtDFS()) {
                        Vertex neighbour = graph.get(nextVertex).get(i);
                        graph.get(nextVertex).remove(i);
                        nextVertex = neighbour;
                        continue OUTER_LOOP;
                    }
                }
                stack.pop();
                if (stack.empty())
                    break;
                nextVertex = stack.peek();
            }
            nextVertex = findUnviewedVertexFisrtDFS();
        }
    }

    private void secondDFS(){
        int color = 1;
        Stack<Vertex> stack = new Stack<Vertex>();
        Vertex nextVertex = this.findUnviewedVertexSecondDFS();
        OUTER_LOOP:
        while (nextVertex != null){
            stack.push(nextVertex);
            nextVertex.secondDFS();
            nextVertex.setColor(color);
            while (!stack.empty()) {
                for (int i = 0; i < graph.get(nextVertex).size(); i++) {
                    if (!graph.get(nextVertex).get(i).getStateSecondDFS()) {
                        Vertex neighbour = graph.get(nextVertex).get(i);
                        graph.get(nextVertex).remove(i);
                        nextVertex = neighbour;
                        continue OUTER_LOOP;
                    }
                }
                nextVertex = stack.pop();
            }
            color++;
            nextVertex = findUnviewedVertexSecondDFS();
        }
    }

    private Vertex findUnviewedVertexFisrtDFS(){
        for (int i = 0; i < vertex.size(); i++){
            if (!vertex.get(i).getStateFisrtDFS())
                return vertex.get(i);
        }
        return null;
    }

    private Vertex findUnviewedVertexSecondDFS(){
        for (int i = 0; i < vertex.size(); i++){
            if (!vertex.get(i).getStateSecondDFS())
                return vertex.get(i);
        }
        return null;
    }
}

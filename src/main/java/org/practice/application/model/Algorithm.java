package org.practice.application.model;

import javafx.concurrent.Task;
import org.practice.application.controller.GraphEditorController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import javafx.application.Platform;
public class Algorithm {
    private HashMap<Vertex, ArrayList<Vertex>> graph;
    private ArrayList<Vertex> vertex;
    private ArrayList<Vertex[]> result;
    private final GraphEditorController controller;


    public Algorithm(GraphEditorController controller){
        this.result = new ArrayList<Vertex[]>();
        this.controller = controller;
    }

    public void setGraph(HashMap<Vertex, ArrayList<Vertex>> graph) {
        this.graph = graph;
    }

    public void setVertex(ArrayList<Vertex> vertex) {
        this.vertex = vertex;
    }

    public ArrayList<Vertex[]> findBridges(){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                firstDFS();
                return null;
            }
        };

        System.out.println("After first DFS:");
        System.out.println(graph.toString());
        task.setOnSucceeded(e -> {
            System.out.println("After first DFS:");
            System.out.println(graph.toString());
            secondDFS();
            System.out.println("After second DFS:");
            for (int i = 0; i < vertex.size(); i++) {
                System.out.println(vertex.get(i).toString() + " - " + vertex.get(i).getColor());
            }

            for (Vertex ver1 : vertex) {
                for (Vertex ver2 : graph.get(ver1)) {
                    if (ver1.getColor() != ver2.getColor()) {
                        Vertex[] res = new Vertex[]{ver1, ver2};
                        result.add(res);
                    }
                }
            }
            System.out.println("Result:");
            for (Vertex[] pair: result){
                System.out.println(pair[0] + "--" + pair[1]);
            }
        });
        new Thread(task).start();
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
                        // Обновление UI
                        Vertex finalNextVertex = nextVertex;
                        Platform.runLater(() -> {
                            controller.addDirection(finalNextVertex.getId(), neighbour.getId());
                        });

                        // Пауза между шагами
                        try {
                            Thread.sleep(1000); // Уменьшите для более быстрой анимации
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }



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

package org.practice.application.model;

import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import javafx.application.Platform;
import org.practice.application.view.GraphView;

public class Algorithm {
    private HashMap<Vertex, ArrayList<Vertex>> graph;
    private ArrayList<Vertex> vertex;
    private ArrayList<Vertex[]> result;
    private ArrayList<Vertex> arrayAfterFirstDFS;
    private final GraphView view;
    private Thread taskOfAlg;


    public Algorithm(GraphView view){
        this.result = new ArrayList<Vertex[]>();
        this.arrayAfterFirstDFS = new ArrayList<Vertex>();
        this.view = view;
    }

    public void setGraph(HashMap<Vertex, ArrayList<Vertex>> graph) {
        this.graph = graph;
    }

    public void setVertex(ArrayList<Vertex> vertex) {
        this.vertex = vertex;
    }

    public ArrayList<Vertex[]> findBridges() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                firstDFS(true);
                System.out.println(graph.toString());

                secondDFS(true);

                for (Vertex ver1 : vertex) {
                    for (Vertex ver2 : graph.get(ver1)) {
                        if (ver1.getColor() != ver2.getColor()) {

                            result.add(new Vertex[]{ver1, ver2});

                            Platform.runLater(() -> {
                                view.drawBridges(ver1.getId(), ver2.getId());
                            });

                        }
                    }
                }
                System.out.println("Result:");
                for (Vertex[] pair: result){
                    System.out.println(pair[0] + "--" + pair[1]);
                }
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            view.clearAfterAlgorithm();
            for (Vertex ver: vertex)
                ver.clearVertex();
        });
        taskOfAlg = new Thread(task);
        taskOfAlg.start();
        return this.result;
    }

    private void firstDFS(boolean isStep){
        Stack<Vertex> stack = new Stack<Vertex>();
        Vertex nextVertex = this.findUnviewedVertexFisrtDFS();
        OUTER_LOOP:
        while (nextVertex != null){
            stack.push(nextVertex);
            arrayAfterFirstDFS.add(nextVertex);
            nextVertex.firstDFS();
            while (!stack.empty()) {
                for (int i = 0; i < graph.get(nextVertex).size(); i++) {
                    if (!graph.get(nextVertex).get(i).getStateFisrtDFS()) {
                        Vertex neighbour = graph.get(nextVertex).get(i);
                        graph.get(nextVertex).remove(i);

                        Vertex finalNextVertex = nextVertex;

                        if (isStep) {
                            Platform.runLater(() -> {
                                view.drawDirection(finalNextVertex.getId(), neighbour.getId());
                            });


                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                return;
                            }

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

    private void secondDFS(boolean isStep){
        int color = 1;
        Stack<Vertex> stack = new Stack<Vertex>();
        Vertex nextVertex = this.findUnviewedVertexSecondDFS();
        OUTER_LOOP:
        while (nextVertex != null){
            stack.push(nextVertex);
            nextVertex.secondDFS();
            nextVertex.setColor(color);


            Vertex finalNextVertex = nextVertex;
            int finalColor = color;
            if (isStep) {
                Platform.runLater(() -> {
                    view.drawVertex(finalNextVertex.getId(), finalColor);
                });

                // Пауза для визуализации
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }


            while (!stack.empty()) {
                for (int i = 0; i < graph.get(nextVertex).size(); i++) {
                    if (!graph.get(nextVertex).get(i).getStateSecondDFS()) {
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
        for (int i = 0; i < arrayAfterFirstDFS.size(); i++){
            if (!arrayAfterFirstDFS.get(i).getStateSecondDFS())
                return arrayAfterFirstDFS.get(i);
        }
        return null;
    }

    public void clear(){
        graph.clear();
        vertex.clear();
        arrayAfterFirstDFS.clear();
        result.clear();
    }

    public void stop(){
        taskOfAlg.interrupt();
    }

    public void getResultFast() {


        firstDFS(false);
        secondDFS(false);

        for (Vertex ver1 : vertex) {
            for (Vertex ver2 : graph.get(ver1)) {
                if (ver1.getColor() != ver2.getColor()) {
                    result.add(new Vertex[]{ver1, ver2});
                    view.drawBridges(ver2.getId(), ver1.getId());

                }
            }
        }
        System.out.println("Result:");
        for (Vertex[] pair: result){
            System.out.println(pair[0] + "--" + pair[1]);
        }

        for (Vertex ver: vertex)
            ver.clearVertex();
    }

    public ArrayList<Vertex[]> getListBridges() {
        return result;
    }
}

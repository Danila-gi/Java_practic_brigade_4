package org.practice.application.controller.states;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import org.practice.application.model.EditorStateData;
import org.practice.application.model.Graph;
import org.practice.application.model.GraphEditorContext;
import org.practice.application.model.Vertex;
import org.practice.application.view.GraphView;

import java.util.ArrayList;

public class RunState implements EditorState {
    private final Graph graph;
    private final GraphView graphView;

    public RunState(EditorStateData stateData) {
        this.graph = stateData.getGraph();
        this.graphView = stateData.getGraphView();
    }

    private void startAlgorithm() {
        graph.execute();
    }

    private void stopAndCleanAlgorithm() {
        graph.stopAlg();
        graph.clearAlg();
        graphView.clearAfterAlgorithm();

        for (Vertex ver : graph.getArrayOfVertex()) {
            ver.clearVertex();
        }

        ArrayList<Node> arrayForDelete = new ArrayList<>();
        for (Node node : graphView.getGraphPane().getChildren()) {
            if ("Polygon".equals(node.getTypeSelector())) {
                arrayForDelete.add(node);
            }
            if ("Line".equals(node.getTypeSelector())) {
                Line line = (Line) node;
                line.getStyleClass().clear();
                line.getStyleClass().add("edge");
            }
        }
        graphView.getGraphPane().getChildren().removeAll(arrayForDelete);
    }

    @Override
    public void openState() {
        startAlgorithm();
    }

    @Override
    public void exitState() {
        stopAndCleanAlgorithm();
    }
}

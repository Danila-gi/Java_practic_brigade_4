package org.practice.application.controller.states;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.shape.Line;
import org.practice.application.model.EditorStateData;
import org.practice.application.model.Graph;
import org.practice.application.model.GraphEditorContext;
import org.practice.application.model.Vertex;
import org.practice.application.view.GraphView;

import java.util.ArrayList;

public class GetResultState implements EditorState {
    private final GraphView graphView;
    private final Graph graph;

    public GetResultState(EditorStateData stateData) {
        this.graph = stateData.getGraph();
        this.graphView = stateData.getGraphView();

    }

    private void getResultAlgorithm() {
        graph.getRusult();
    }

    private void stopAndCleanAlgorithm() {

        graph.clearAlg();

        for (Vertex ver : graph.getArrayOfVertex()) {
            ver.clearVertex();
        }


        for (Node node : graphView.getGraphPane().getChildren()) {
            if ("Line".equals(node.getTypeSelector())) {
                Line line = (Line) node;
                line.getStyleClass().clear();
                line.getStyleClass().add("edge");
            }
        }

    }


    @Override
    public void openState() {
        getResultAlgorithm();
    }

    @Override
    public void exitState() {
        stopAndCleanAlgorithm();
    }
}
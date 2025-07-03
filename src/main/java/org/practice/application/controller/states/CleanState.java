package org.practice.application.controller.states;

import org.practice.application.model.EditorStateData;
import org.practice.application.model.Graph;
import org.practice.application.model.GraphEditorContext;
import org.practice.application.view.GraphView;

public class CleanState implements EditorState {
    private final Graph graph;
    private final GraphView graphView;

    public CleanState(EditorStateData stateData) {
        this.graph = stateData.getGraph();
        this.graphView = stateData.getGraphView();
    }

    @Override
    public void openState() {
        graph.clear();
        graphView.cleanSurface();
    }
}

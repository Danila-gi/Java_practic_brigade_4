package org.practice.application.controller.states;

import org.practice.application.model.EditorStateData;
import org.practice.application.model.Graph;
import org.practice.application.model.GraphEditorContext;
import org.practice.application.view.GraphView;

public class CleanState implements EditorState {
    private GraphEditorContext context;
    private EditorState newState;
    private final Graph graph;
    private final GraphView graphView;

    public CleanState(GraphEditorContext context, EditorStateData stateData, EditorState newState) {
        this.context = context;
        this.newState = newState;
        this.graph = stateData.getGraph();
        this.graphView = stateData.getGraphView();
    }

    @Override
    public void openState() {
        graph.clear();
        graphView.cleanSurface();
        context.transitToState(newState);
    }
}

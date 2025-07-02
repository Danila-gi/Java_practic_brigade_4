package org.practice.application.model;

import org.practice.application.controller.states.EditorState;

public class GraphEditorContext {
    private EditorState currentState;
    private final EditorStateData stateData;

    public GraphEditorContext(EditorStateData stateData) {
        this.stateData = stateData;
    }

    public void transitToState(EditorState newState) {
        if (currentState != null) {
            currentState.exitState();
        }
        currentState = newState;
        currentState.openState();
    }

    public EditorState getState() {
        return currentState;
    }
}
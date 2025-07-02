package org.practice.application.controller.states;

import org.practice.application.controller.VertexController;

public class DeleteState implements EditorState {
    private final VertexController vertexController;
    public DeleteState(VertexController vertexController) {
        this.vertexController = vertexController;
    }

    @Override
    public void handleClickOnVertex(int vertexId) {
        vertexController.deleteVertexAt(vertexId);
    }
}

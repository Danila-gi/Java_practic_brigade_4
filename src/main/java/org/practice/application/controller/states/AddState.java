package org.practice.application.controller.states;

import javafx.scene.paint.Color;
import org.practice.application.controller.EdgeController;
import org.practice.application.controller.VertexController;
import org.practice.application.model.EditorStateData;

public class AddState implements EditorState {
    private final VertexController vertexController;
    private final EdgeController edgeController;
    private Integer selectedVertexId;
    private Color clickedVertex;

    public AddState(VertexController vertexController, EdgeController edgeController, EditorStateData stateData) {
        this.vertexController = vertexController;
        this.edgeController = edgeController;
        this.clickedVertex = stateData.getSelectedVertexColor();
        this.selectedVertexId = null;
    }

    @Override
    public void openState() {
        selectedVertexId = null;
    }

    @Override
    public void exitState() {
        if (selectedVertexId != null) {
            vertexController.highlightVertex(selectedVertexId, Color.LIGHTBLUE);
        }
        selectedVertexId = null;
    }

    private void clearSelecetion() {
        if (selectedVertexId != null) {
            vertexController.highlightVertex(selectedVertexId, Color.LIGHTBLUE);
            selectedVertexId = null;
        }
    }

    @Override
    public void handleClickOnVertex(int vertexId) {
        if (selectedVertexId == null) {
            selectedVertexId = vertexId;
            vertexController.highlightVertex(vertexId, Color.ORANGE);
        } else if (selectedVertexId == vertexId) {
            clearSelecetion();
        } else {
            edgeController.addEdge(selectedVertexId, vertexId);
            clearSelecetion();
        }
    }

    @Override
    public void handleClickOnPane(double x, double y) {
        vertexController.addVertexAt(x, y);
        clearSelecetion();
    }
}
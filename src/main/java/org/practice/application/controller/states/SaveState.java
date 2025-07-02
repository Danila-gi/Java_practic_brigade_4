package org.practice.application.controller.states;

import org.practice.application.model.GraphEditorContext;
import org.practice.application.controller.FileController;

public class SaveState implements EditorState {
    private final FileController fileController;
    private final GraphEditorContext context;
    private final EditorState newState;

    public SaveState(GraphEditorContext context, FileController fileController, EditorState newState) {
        this.context = context;
        this.newState = newState;
        this.fileController = fileController;
    }

    @Override
    public void openState() {
        fileController.saveGraph("newGraph.txt");
        context.transitToState(newState);
    }
}

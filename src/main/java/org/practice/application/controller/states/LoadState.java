package org.practice.application.controller.states;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import org.practice.application.model.GraphEditorContext;
import org.practice.application.controller.FileController;
import org.practice.application.model.FileException;

import java.io.File;

public class LoadState implements EditorState {
    private final GraphEditorContext context;
    private final FileController fileController;
    private final EditorState newState;

    public LoadState(GraphEditorContext context, FileController fileController, EditorState newState) {
        this.context = context;
        this.fileController = fileController;
        this.newState = newState;
    }

    @Override
    public void openState() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                fileController.loadGraph(selectedFile);
                context.transitToState(newState);
            } catch (FileException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File Error");
                alert.setHeaderText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
}

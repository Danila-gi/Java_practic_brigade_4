package org.practice.application.controller.states;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import org.practice.application.model.GraphEditorContext;
import org.practice.application.controller.FileController;
import org.practice.application.model.FileException;

import java.io.File;

public class LoadState implements EditorState {
    private final FileController fileController;

    public LoadState(FileController fileController) {
        this.fileController = fileController;
    }

    @Override
    public void openState() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                fileController.loadGraph(selectedFile);
            } catch (FileException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File Error");
                alert.setHeaderText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
}

package org.practice.application.controller.states;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import org.practice.application.model.GraphEditorContext;
import org.practice.application.controller.FileController;

import java.io.File;

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt")
        );
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            fileController.saveResult(file.getAbsolutePath());
            context.transitToState(newState);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save cancel");
            alert.setHeaderText(null);
            alert.setContentText("Save to file canceled by user.");
            alert.showAndWait();
        }
    }
}

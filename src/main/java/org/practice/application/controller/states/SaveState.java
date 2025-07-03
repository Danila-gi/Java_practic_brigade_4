package org.practice.application.controller.states;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.FileChooser;
import org.practice.application.model.GraphEditorContext;
import org.practice.application.controller.FileController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaveState implements EditorState {
    private final FileController fileController;

    public SaveState(FileController fileController) {
        this.fileController = fileController;
    }

    private void save(boolean onlyGraph) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt")
        );
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            if (onlyGraph) {
                fileController.saveGraph(file.getAbsolutePath());
            } else {
                fileController.saveResult(file.getAbsolutePath());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save cancel");
            alert.setHeaderText(null);
            alert.setContentText("Save to file canceled by user.");
            alert.showAndWait();
        }
    }
    @Override
    public void openState() {
        List<String> choices = new ArrayList<>();
        choices.add("Graph");
        choices.add("Graph + result");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Graph", choices);
        dialog.setTitle("Select save mode");
        dialog.setHeaderText("Choose what to save: ");
        dialog.setContentText("Options:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String choice = result.get();
            if (choice.equals("Graph")) {
                save(true);
            } else {
                save(false);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save cancel");
            alert.setHeaderText(null);
            alert.setContentText("Save was canceled by user.");
            alert.showAndWait();
        }
    }
}

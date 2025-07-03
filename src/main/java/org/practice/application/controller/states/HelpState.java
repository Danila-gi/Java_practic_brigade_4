package org.practice.application.controller.states;

import javafx.scene.control.Alert;
import org.practice.application.model.EditorStateData;
import org.practice.application.model.GraphEditorContext;
import org.practice.application.view.GraphView;

public class HelpState implements EditorState {
    private final GraphView graphView;

    public HelpState(EditorStateData stateData) {
        this.graphView = stateData.getGraphView();
    }

    @Override
    public void openState() {
        graphView.showInfo();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instruction of program");
        alert.setHeaderText("Choose mode: add vertex or remove them." +
                "\nAdd vertex by clicking your mouse on surface." +
                "\nFor removing choose right mode and click on yor vertex." +
                "\nIn down side select two vertices between which the edge needs to be removed." +
                "\nClick on RUN to see work of algorithm.");
        alert.showAndWait();
    }
}

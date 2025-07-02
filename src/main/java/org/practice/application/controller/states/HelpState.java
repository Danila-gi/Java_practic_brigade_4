package org.practice.application.controller.states;

import javafx.scene.control.Alert;
import org.practice.application.model.EditorStateData;
import org.practice.application.model.GraphEditorContext;
import org.practice.application.view.GraphView;

public class HelpState implements EditorState {
    private final GraphView graphView;
    private final GraphEditorContext context;
    private final EditorState newState;

    public HelpState(GraphEditorContext context, EditorStateData stateData, EditorState newState) {
        this.context = context;
        this.graphView = stateData.getGraphView();
        this.newState = newState;
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
        context.transitToState(newState);
    }
}

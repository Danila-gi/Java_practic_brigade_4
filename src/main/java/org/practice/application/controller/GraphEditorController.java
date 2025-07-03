package org.practice.application.controller;

import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.practice.application.controller.states.*;
import org.practice.application.model.*;
import org.practice.application.view.GraphView;
import org.practice.application.view.ToolbarView;

import java.util.Map;

public class GraphEditorController {
    private final GraphEditorContext context;

    private final VertexController vertexController;
    private final EdgeController edgeController;
    private final FileController fileController;

    private AddState addState;
    private DeleteState deleteState;
    private DragState dragState;
    private LoadState loadState;
    private SaveState saveState;
    private CleanState cleanState;
    private HelpState helpState;
    private RunState runState;
    private GetResultState getResultState;

    private static final Color[] PREDEFINED_COLORS = {
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
            Color.ORANGE, Color.PURPLE, Color.CYAN, Color.MAGENTA
    };

    private final ToolbarView toolbarView;
    private final Graph graph;
    private final GraphView graphView;

    public GraphEditorController(Graph graph, GraphView graphView, ToolbarView toolbarView) {
        this.toolbarView = toolbarView;
        this.graph = graph;
        this.graphView = graphView;

        EditorStateData stateData = new EditorStateData(graph, graphView, 15, Color.ORANGE);
        this.vertexController = new VertexController(stateData);
        this.edgeController = new EdgeController(stateData);
        this.fileController = new FileController(stateData);

        this.context = new GraphEditorContext(stateData);

        this.addState = new AddState(vertexController, edgeController, stateData);
        this.deleteState = new DeleteState(vertexController);
        this.dragState = new DragState(vertexController);
        this.saveState = new SaveState(fileController);
        this.loadState = new LoadState(fileController);
        this.cleanState = new CleanState(stateData);
        this.helpState = new HelpState(stateData);
        this.runState = new RunState(stateData);
        this.getResultState = new GetResultState(stateData);

        context.transitToState(dragState);
        setUpToolbarActions();
        setUpMouseHandlers();
    }

    private void setUpToolbarActions() {
        toolbarView.getButton("run").setOnAction(event -> toggleEditMode());
        toolbarView.getButton("clean").setOnAction(event -> context.transitToState(cleanState));
        toolbarView.getButton("help").setOnAction(event -> context.transitToState(helpState));
        toolbarView.getButton("load").setOnAction(event -> context.transitToState(loadState));
        toolbarView.getButton("save").setOnAction(event -> context.transitToState(saveState));
        toolbarView.getButton("addVertex").setOnAction(event -> context.transitToState(addState));
        toolbarView.getButton("deleteVertex").setOnAction(event -> context.transitToState(deleteState));
        toolbarView.getButton("cursor").setOnAction(event -> context.transitToState(dragState));
        toolbarView.getButton("addEdge").setOnAction(event -> handleAddEdge(toolbarView.getFirstVertexField().getText(), toolbarView.getSecondVertexField().getText()));
        toolbarView.getButton("deleteEdge").setOnAction(event -> handleDeleteEdge(toolbarView.getFirstVertexField().getText(), toolbarView.getSecondVertexField().getText()));
        toolbarView.getButton("result").setOnAction(event -> resultEditMode());
    }

    private void resetControlsEnabled() {
        for (ButtonBase button: toolbarView.getButtons().values()) {
            button.setDisable(false);
            if (button instanceof ToggleButton) {
                ((ToggleButton) button).setSelected(false);
            }
        }
        toolbarView.getFirstVertexField().setDisable(false);
        toolbarView.getSecondVertexField().setDisable(false);
    }

    private void toggleEditMode() {
        boolean isInRunMode = context.getState() instanceof RunState;
        if (isInRunMode) {
            context.transitToState(dragState);
            resetControlsEnabled();
        } else {
            context.transitToState(runState);
            setEditModeControlsEnabled("run",false);
        }
    }

    private void resultEditMode() {
        boolean isGetResult = context.getState() instanceof GetResultState;
        if (isGetResult) {
            context.transitToState(dragState);
            resetControlsEnabled();
        } else {
            context.transitToState(getResultState);
            setEditModeControlsEnabled("result",false);
        }
    }

    private void setEditModeControlsEnabled(String activeButtonKey, boolean enabled) {
        for (Map.Entry<String, ButtonBase> entry : toolbarView.getButtons().entrySet()) {
            String key = entry.getKey();
            ButtonBase button = entry.getValue();
            button.setDisable(!key.equals(activeButtonKey));
        }
        toolbarView.getFirstVertexField().setDisable(!enabled);
        toolbarView.getSecondVertexField().setDisable(!enabled);
    }

    private void handleAddEdge(String firstVertex, String secondVertex) {
        edgeController.addEdge(firstVertex, secondVertex);
    }

    private void handleDeleteEdge(String firstVertex, String secondVertex) {
        edgeController.deleteEdge(firstVertex, secondVertex);
    }

    private void setUpMouseHandlers() {
        graphView.getGraphPane().setOnMouseClicked(event -> {
            if (event.getTarget() == graphView.getGraphPane()) {
                context.getState().handleClickOnPane(event.getX(), event.getY());
            } else if (event.getTarget() instanceof Circle) {
                Circle circle = (Circle) event.getTarget();
                int vertexId = (Integer) circle.getUserData();
                context.getState().handleClickOnVertex(vertexId);
            }
        });

        graphView.getGraphPane().setOnMousePressed(event -> {
            context.getState().handleMousePressed(event.getX(), event.getY(), (Node) event.getTarget());
        });

        graphView.getGraphPane().setOnMouseDragged(event -> {
            if (context.getState() instanceof DragState) {
                context.getState().handleMouseDragged(event.getX(), event.getY(), (Node) event.getTarget());
            }
        });

        graphView.getGraphPane().setOnMouseReleased(event -> {
            context.getState().handleMouseReleased(event.getX(), event.getY(), (Node) event.getTarget());
        });
    }
}

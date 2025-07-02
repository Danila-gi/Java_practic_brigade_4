package org.practice.application.controller.states;

import javafx.scene.Node;
import javafx.scene.shape.Circle;
import org.practice.application.controller.VertexController;

public class DragState implements EditorState {
    private final VertexController vertexController;
    private Integer draggingVertexId = null;
    private double mouseOffsetX;
    private double mouseOffsetY;

    public DragState(VertexController vertexController) {
        this.vertexController = vertexController;
    }

    @Override
    public void handleMousePressed(double x, double y, Node target) {
        if (target instanceof Circle) {
            Circle circle = (Circle) target;
            draggingVertexId = (Integer) circle.getUserData();
            this.mouseOffsetX = x - circle.getCenterX();
            this.mouseOffsetY = y - circle.getCenterY();
        } else {
            draggingVertexId = null;
        }
    }
    @Override
    public void handleMouseDragged(double x, double y, Node target) {
        if (draggingVertexId != null) {
            vertexController.moveVertexTo(draggingVertexId, x - mouseOffsetX, y - mouseOffsetY);
        }
    }

    @Override
    public void handleMouseReleased(double x, double y, Node target) {
        draggingVertexId = null;
        mouseOffsetX = 0;
        mouseOffsetY = 0;
    }

    @Override
    public void handleClickOnPane(double x, double y) {
        draggingVertexId = null;
    }
}
package org.practice.application.controller.states;

import javafx.scene.Node;

public interface EditorState {
    default void openState() {}
    default void exitState() {}
    default void handleClickOnVertex(int vertexId) {}
    default void handleClickOnPane(double x, double y) {}
    default void handleMouseDragged(double x, double y, Node target) {}
    default void handleMouseReleased(double x, double y, Node target) {}
    default void handleMousePressed(double x, double y, Node target) {}
}

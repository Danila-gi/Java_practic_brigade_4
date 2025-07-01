package org.practice.application.view;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VertexView {
    private final int id;
    private final Circle circle;
    private final Label label;

    public VertexView(int id, double x, double y, double radius, Color color) {
        this.id = id;
        this.circle = new Circle(x, y, radius, color);
        circle.getStyleClass().add("vertex");
        this.circle.setStroke(Color.BLACK);
        this.circle.setStrokeWidth(2);
        this.circle.setUserData(id);
        this.label = new Label(String.valueOf(id));
        label.getStyleClass().add("vertex-label");
        this.label.layoutXProperty().bind(this.circle.centerXProperty().subtract(this.label.widthProperty().divide(2)));
        this.label.layoutYProperty().bind(this.circle.centerYProperty().subtract(this.label.heightProperty().divide(2)));
        this.label.setMouseTransparent(true);
        this.label.setPickOnBounds(false);
    }

    public void highlightCircle(Color color) {
        this.circle.setFill(color);
    }

    public int getId() {
        return id;
    }

    public Circle getCircle() {
        return circle;
    }

    public Label getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "VertexView{"
                + "x = " + circle.getCenterX()
                + ", y = " + circle.getCenterY()
                + "}";
    }
}

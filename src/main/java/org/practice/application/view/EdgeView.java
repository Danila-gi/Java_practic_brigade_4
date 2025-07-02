package org.practice.application.view;


import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class EdgeView {
    private final VertexView from;
    private final VertexView to;
    private final Line line;

    public EdgeView(VertexView from, VertexView to) {
        this.from = from;
        this.to = to;
        this.line = new Line();
        line.getStyleClass().add("edge");
        this.line.startXProperty().bind(from.getCircle().centerXProperty());
        this.line.startYProperty().bind(from.getCircle().centerYProperty());
        this.line.endXProperty().bind(to.getCircle().centerXProperty());
        this.line.endYProperty().bind(to.getCircle().centerYProperty());
    }

    public VertexView getFrom() {
        return from;
    }

    public VertexView getTo() {
        return to;
    }

    public Line getLine() {
        return line;
    }

    @Override
    public String toString() {
        return "EdgeView { " +
                "from (id = " + from.getId() +
                ", x = " + from.getCircle().getCenterX() +
                ", y = " + from.getCircle().getCenterY() + ")" +
                " -> to (id = " + to.getId() +
                ", x = " + to.getCircle().getCenterX() +
                ", y = " + to.getCircle().getCenterY() + ")" +
                " }";
    }

}

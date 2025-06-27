package org.practice.application.view;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;


public class GraphView {
    private final Pane graphPane = new Pane();
    private final StackPane graphContainer;
    private final List<Circle> vertices = new ArrayList<>();
    private final List<Text> vertexLabels = new ArrayList<>();
    private final List<Line> edges = new ArrayList<>();


    public GraphView() {
        graphContainer = new StackPane(graphPane);
        graphContainer.setPadding(new javafx.geometry.Insets(10));
        graphContainer.setStyle("-fx-border-color: #999; -fx-border-width: 2; -fx-background-color: white;");
        graphPane.setPrefSize(600, 400);
    }

    public StackPane getGraphContainer() {
        return graphContainer;
    }

    public Pane getGraphPane() {
        return graphPane;
    }

    public List<Circle> getVertices() {
        return vertices;
    }

    public List<Text> getVertexLabels() {
        return vertexLabels;
    }

    public List<Line> getEdges() {
        return edges;
    }


    public void incrementVertexCounter() {
        vertexCounter++;
    }

    public void addVertex(double x, double y) {
        Circle vertex = new Circle(x, y, 15, javafx.scene.paint.Color.LIGHTBLUE);
        vertex.setStroke(javafx.scene.paint.Color.BLACK);
        vertex.setStrokeWidth(2);
        vertices.add(vertex);
        graphPane.getChildren().add(vertex);

        Text label = new Text(String.valueOf(vertices.size()));
        label.setX(x - 5);
        label.setY(y + 5);
        vertexLabels.add(label);
        graphPane.getChildren().add(label);


    }

    public void deleteVertex(Circle vertex) {
        List<Line> edgesToRemove = new ArrayList<>();
        for (Line edge : edges) {
            if (edge.getStartX() == vertex.getCenterX() && edge.getStartY() == vertex.getCenterY() ||
                    edge.getEndX() == vertex.getCenterX() && edge.getEndY() == vertex.getCenterY()) {
                edgesToRemove.add(edge);
            }
        }
        edges.removeAll(edgesToRemove);
        graphPane.getChildren().removeAll(edgesToRemove);

        int index = vertices.indexOf(vertex);
        if (index >= 0 && index < vertexLabels.size()) {
            graphPane.getChildren().remove(vertexLabels.get(index));
            vertexLabels.remove(index);
        }

        graphPane.getChildren().remove(vertex);
        vertices.remove(vertex);
    }

    public void updateVertexLabel(Circle vertex) {
        int index = vertices.indexOf(vertex);
        if (index >= 0 && index < vertexLabels.size()) {
            Text label = vertexLabels.get(index);
            label.setX(vertex.getCenterX() - 5);
            label.setY(vertex.getCenterY() + 5);
        }
    }

    public void connectVertices(Circle v1, Circle v2) {
        Line edge = new Line();
        edge.setStroke(javafx.scene.paint.Color.BLACK);
        edge.setStrokeWidth(2);

        edge.startXProperty().bind(v1.centerXProperty());
        edge.startYProperty().bind(v1.centerYProperty());
        edge.endXProperty().bind(v2.centerXProperty());
        edge.endYProperty().bind(v2.centerYProperty());

        edges.add(edge);
        graphPane.getChildren().add(edge);
    }
}
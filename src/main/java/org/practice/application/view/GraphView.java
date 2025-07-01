package org.practice.application.view;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;


public class GraphView {
    private final Pane graphPane;
    private final StackPane container;
    private final Map<Integer, VertexView> vertexViewMap;
    private final List<EdgeView> edges;

    public GraphView() {
        graphPane = new Pane();
        graphPane.setPrefSize(600, 400);
        graphPane.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white;");

        container = new StackPane(graphPane);
        container.setPrefSize(600, 400);
        vertexViewMap = new HashMap<>();
        edges = new ArrayList<>();
    }

    public void addVertex(int id, double x, double y, double radius, Color color) {
        VertexView vertexView = new VertexView(id, x, y, radius, color);
        vertexViewMap.put(id, vertexView);
        graphPane.getChildren().addAll(vertexView.getCircle(), vertexView.getLabel());
    }

    public void deleteVertex(int id) {
        VertexView removeVertexView = vertexViewMap.get(id);
        if (removeVertexView != null) {
            deleteAllEdgesByVertex(id);
            graphPane.getChildren().removeAll(removeVertexView.getCircle(), removeVertexView.getLabel());
            vertexViewMap.remove(id);
        }
    }

    public void addEdge(int firstId, int secondId, double strokeWidth, Color color) {
        VertexView from = vertexViewMap.get(firstId);
        VertexView to = vertexViewMap.get(secondId);
        if (from == null || to == null) {
            return;
        }

        EdgeView edgeView = new EdgeView(from, to, strokeWidth, color);

        edges.add(edgeView);
        graphPane.getChildren().add(0, edgeView.getLine());

        // drawDirection(firstId, secondId); // Добавлял для теста
    }

    public void deleteEdge(int firstId, int secondId) {
        VertexView from = vertexViewMap.get(firstId);
        VertexView to = vertexViewMap.get(secondId);
        if (from == null || to == null) {
            return;
        }

        EdgeView edgeToRemove = null;
        for (EdgeView edge : edges) {
            if (edge.getFrom() == from && edge.getTo() == to) {
                edgeToRemove = edge;
                break;
            }
        }
        if (edgeToRemove != null) {
            if (edgeToRemove.getLine().getUserData() != null) {
                Node arrow = (Node) edgeToRemove.getLine().getUserData();
                graphPane.getChildren().remove(arrow); // Удаляем стрелку с панели
            }
            graphPane.getChildren().remove(edgeToRemove.getLine());
            edges.remove(edgeToRemove);
        }
    }

    public void deleteAllEdgesByVertex(int id) {
        VertexView removeVertexView = vertexViewMap.get(id);
        List<EdgeView> edgesToRemove = new ArrayList<>();
        for (EdgeView edge : edges) {
            if (edge.getFrom() == removeVertexView || edge.getTo() == removeVertexView) {
                edgesToRemove.add(edge);
            }
        }
        for (EdgeView edge : edgesToRemove) {
            if (edge.getLine().getUserData() != null) {
                Node arrow = (Node) edge.getLine().getUserData();
                graphPane.getChildren().remove(arrow); // Удаляем стрелку с панели
            }
            graphPane.getChildren().remove(edge.getLine());
            edges.remove(edge);
        }
    }

    public void highlight(int id, Color color) {
        VertexView vertex = vertexViewMap.get(id);
        vertex.highlightCircle(color);
    }

    public void cleanSurface() {
        graphPane.getChildren().clear();
        vertexViewMap.clear();
        edges.clear();
    }

    public void showInfo() {
        vertexViewMap.forEach((id, view) ->
            System.out.println("id: " + id + ", vertexView: " + view)
        );
        edges.forEach(System.out::println);
    }

    public StackPane getContainer() {
        return container;
    }

    public Pane getGraphPane() {
        return graphPane;
    }


    public void drawDirection(int firstId, int secondId) {
        VertexView from = vertexViewMap.get(firstId);
        VertexView to = vertexViewMap.get(secondId);
        if (from == null || to == null) {
            return;
        }

        EdgeView edgeToDirection = null;
        for (EdgeView edge : edges) {
            if (edge.getFrom() == from && edge.getTo() == to) {
                edgeToDirection = edge;
                break;
            }
        }

        if (edgeToDirection != null) {
            addArrowToEdge(edgeToDirection, from);

        }


    }


    private void addArrowToEdge(EdgeView edge, VertexView target) {

        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(
                0.0, 0.0,
                10.0, -5.0,
                10.0, 5.0
        );
        arrow.setFill(Color.BLACK);


        double dx = edge.getLine().getEndX() - edge.getLine().getStartX();
        double dy = edge.getLine().getEndY() - edge.getLine().getStartY();
        double length = Math.sqrt(dx*dx + dy*dy);


        dx /= length;
        dy /= length;


        double arrowX = target.getCircle().getCenterX() + dx * target.getCircle().getRadius();
        double arrowY = target.getCircle().getCenterY() + dy * target.getCircle().getRadius();

        arrow.setLayoutX(arrowX);
        arrow.setLayoutY(arrowY);


        double angle = Math.toDegrees(Math.atan2(dy, dx));


        Rotate rotation = new Rotate(angle, 0, 0);
        arrow.getTransforms().add(rotation);


        graphPane.getChildren().add(arrow);


        edge.getLine().setUserData(arrow);
    }
}
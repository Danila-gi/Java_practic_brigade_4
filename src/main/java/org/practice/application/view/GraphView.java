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

    private static final Color[] PREDEFINED_COLORS = {
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
            Color.ORANGE, Color.PURPLE, Color.CYAN, Color.MAGENTA
    };

    public GraphView() {
        graphPane = new Pane();
        graphPane.setPrefSize(600, 400);
        graphPane.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white;");

        container = new StackPane(graphPane);
        container.setPrefSize(600, 400);
        vertexViewMap = new HashMap<>();
        edges = new ArrayList<>();
    }

    public void addVertex(int id, double x, double y, double radius) {
        VertexView vertexView = new VertexView(id, x, y, radius);
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

    public void addEdge(int firstId, int secondId) {
        VertexView from = vertexViewMap.get(firstId);
        VertexView to = vertexViewMap.get(secondId);
        if (from == null || to == null) {
            return;
        }
        for (EdgeView edgeView : edges) {
            if ((edgeView.getFrom() == from && edgeView.getTo() == to) ||
                    (edgeView.getFrom() == to && edgeView.getTo() == from)) {
                return;
            }

        }
        EdgeView edgeView = new EdgeView(from, to);
        edges.add(edgeView);
        graphPane.getChildren().add(0, edgeView.getLine());
    }

    public void deleteEdge(int firstId, int secondId) {
        VertexView from = vertexViewMap.get(firstId);
        VertexView to = vertexViewMap.get(secondId);
        if (from == null || to == null) {
            return;
        }

        EdgeView edgeToRemove = null;
        for (EdgeView edge : edges) {
            if ((edge.getFrom() == from && edge.getTo() == to) ||
                    (edge.getFrom() == to && edge.getTo() == from)) {
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
    public void moveToVertexView(int vertexViewId, double x, double y) {
        VertexView vertexView = vertexViewMap.get(vertexViewId);
        if (vertexView != null) {
            vertexView.moveTo(x, y);
        }
    }

    public Pair<Double, Double> getVertexPosition(int vertexViewId) {
        VertexView vertexView = vertexViewMap.get(vertexViewId);
        return new Pair<>(vertexView.getVertexPositionX(), vertexView.getVertexPositionY());
    }

    public StackPane getContainer() {
        return container;
    }

    public Pane getGraphPane() {
        return graphPane;
    }

    private Pair<Integer,EdgeView> findEdge(int firstId, int secondId) {
        VertexView from = vertexViewMap.get(firstId);
        VertexView to = vertexViewMap.get(secondId);
        if (from == null || to == null) {
            return null;
        }

        EdgeView edgeToFind = null;
        for (EdgeView edge : edges) {
            if ((edge.getFrom() == from && edge.getTo() == to) || (edge.getFrom() == to && edge.getTo() == from)) {
                edgeToFind = edge;
                break;
            }
        }
        if (edgeToFind.getFrom() == from && edgeToFind.getTo() == to) {
            return new Pair<>(1, edgeToFind);
        }
        if (edgeToFind.getFrom() == to && edgeToFind.getTo() == from) {
            return new Pair<>(-1, edgeToFind);
        }
        return null;
    }

    public void drawDirection(int firstId, int secondId) {
        VertexView from = vertexViewMap.get(firstId);

        Pair<Integer,EdgeView> edgeViewPair = findEdge(firstId, secondId);
        EdgeView edgeToDirection = edgeViewPair.getRight();
        int numberChooseDirection = edgeViewPair.getLeft();
        if (edgeToDirection != null) {
            addArrowToEdge(edgeToDirection, from, numberChooseDirection);
        }
    }


    private void addArrowToEdge(EdgeView edge, VertexView target, int number) {

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


        double arrowX = target.getCircle().getCenterX() + number * dx * target.getCircle().getRadius();
        double arrowY = target.getCircle().getCenterY() + number * dy * target.getCircle().getRadius();

        arrow.setLayoutX(arrowX);
        arrow.setLayoutY(arrowY);



        double angle = Math.toDegrees(Math.atan2(dy, dx)) + (180 * ((number == -1) ? 1 : 0));


        Rotate rotation = new Rotate(angle, 0, 0);
        arrow.getTransforms().add(rotation);


        graphPane.getChildren().add(arrow);


        edge.getLine().setUserData(arrow);
    }


    public void drawBridges(int firstId, int secondId) {

        EdgeView bridge = findEdge(firstId, secondId).getRight();
        if (bridge != null) {
            if (bridge.getLine().getUserData() != null) {
                Polygon arrow = (Polygon) bridge.getLine().getUserData();
                arrow.setFill(Color.RED);
            }
            bridge.getLine().getStyleClass().add("bridge");
        }
    }


    public void drawVertex(int id, int color) {
        VertexView vertex = vertexViewMap.get(id);
        vertex.getCircle().setFill(getColorByNumber(color));
    }

    public void clearAfterAlgorithm(){
        for (VertexView ver: vertexViewMap.values())
            ver.clear();
    }

    public static Color getColorByNumber(int number) {
        return PREDEFINED_COLORS[number % PREDEFINED_COLORS.length];
    }
}
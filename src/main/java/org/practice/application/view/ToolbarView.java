package org.practice.application.view;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;


public class ToolbarView {
    private final HBox toolbar;
    private final HBox edgeControls;
    private ToggleButton addVertexButton;
    private ToggleButton deleteVertexButton;
    private Button toggleButton;
    private boolean editMode = true;
    private Circle firstSelected = null;
    private final GraphView graphView;

    public ToolbarView(GraphView graphView) {
        this.graphView = graphView;
        this.toolbar = createToolbar();
        this.edgeControls = createEdgeControls();
        this.addVertexButton = (ToggleButton) ((HBox) ((BorderPane) toolbar.getChildren().get(0)).getCenter()).getChildren().get(0);
        this.deleteVertexButton = (ToggleButton) ((HBox) ((BorderPane) toolbar.getChildren().get(0)).getCenter()).getChildren().get(1);
        this.toggleButton = (Button) ((HBox) ((BorderPane) toolbar.getChildren().get(0)).getLeft()).getChildren().get(0);

        setupMouseHandlers();
    }

    public HBox getToolbar() {
        return toolbar;
    }

    public HBox getEdgeControls() {
        return edgeControls;
    }

    private HBox createToolbar() {
        toggleButton = new Button("Run: ON");
        toggleButton.setOnAction(e -> toggleEditMode());

        Button cleanButton = new Button("Clean");
        cleanButton.setOnAction(e -> cleanSurface());

        Button loadGraphButton = new Button("Load graph");
        loadGraphButton.setOnAction(e -> chooseFile());

        Button helpButton = new Button();
        helpButton.setGraphic(loadHelpIcon());
        helpButton.setOnAction(e -> showHelp());

        ToggleGroup vertexToolsGroup = new ToggleGroup();

        addVertexButton = new ToggleButton("Add vertex");
        addVertexButton.setToggleGroup(vertexToolsGroup);
        addVertexButton.setSelected(true);
        addVertexButton.setStyle("-fx-base: #a0e0a0;");

        deleteVertexButton = new ToggleButton("Remove vertex");
        deleteVertexButton.setToggleGroup(vertexToolsGroup);
        deleteVertexButton.setStyle("-fx-base: #e0a0a0;");

        StackPane helpContainer = new StackPane(helpButton);
        helpContainer.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        StackPane.setMargin(helpButton, new javafx.geometry.Insets(5, 5, 0, 0));

        HBox leftToolbar = new HBox(10, toggleButton, cleanButton, loadGraphButton);
        leftToolbar.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        HBox centerToolbar = new HBox(10, addVertexButton, deleteVertexButton);
        centerToolbar.setAlignment(javafx.geometry.Pos.CENTER);

        BorderPane toolContainer = new BorderPane();
        toolContainer.setLeft(leftToolbar);
        toolContainer.setCenter(centerToolbar);
        toolContainer.setRight(helpContainer);
        toolContainer.setPadding(new javafx.geometry.Insets(5));

        HBox toolbar = new HBox(toolContainer);
        toolbar.setAlignment(javafx.geometry.Pos.CENTER);
        HBox.setHgrow(toolContainer, Priority.ALWAYS);

        return toolbar;
    }

    private ImageView loadHelpIcon() {
        try {
            Image image = new Image(getClass().getResourceAsStream("/images/help_icon.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            return imageView;
        } catch (Exception e) {
            System.err.println("Не удалось загрузить иконку: " + e.getMessage());
            ImageView iv = new ImageView();
            iv.setFitWidth(20);
            iv.setFitHeight(20);
            Circle fallbackIcon = new Circle(10, Color.GRAY);
            iv.setImage(fallbackIcon.snapshot(null, null));
            return iv;
        }
    }

    private HBox createEdgeControls() {
        TextField vertex1Field = new TextField();
        vertex1Field.setPromptText("Vertex 1");
        vertex1Field.setPrefWidth(80);

        TextField vertex2Field = new TextField();
        vertex2Field.setPromptText("Vertex 2");
        vertex2Field.setPrefWidth(80);

        Button addEdgeButton = new Button("+");
        addEdgeButton.setStyle("-fx-font-weight: bold; -fx-base: #a0e0a0;");
        addEdgeButton.setOnAction(e -> addEdgeByNumbers());

        Button removeEdgeButton = new Button("-");
        removeEdgeButton.setStyle("-fx-font-weight: bold; -fx-base: #e0a0a0;");
        removeEdgeButton.setOnAction(e -> removeEdgeByNumbers());

        HBox controls = new HBox(10,
                new Label("Edge Management:"),
                vertex1Field,
                new Label("--"),
                vertex2Field,
                addEdgeButton,
                removeEdgeButton);
        controls.setAlignment(javafx.geometry.Pos.CENTER);
        controls.setPadding(new javafx.geometry.Insets(10));
        controls.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #ccc; -fx-border-width: 1;");

        return controls;
    }

    private void removeEdgeByNumbers() {
    }

    private void addEdgeByNumbers() {
    }

    private void showHelp() {
        graphView.getEdges().stream().forEach(System.out::println);

        graphView.getVertices().stream().forEach(System.out::println);

        graphView.getVertexLabels().stream().forEach(System.out::println);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instruction of program");
        alert.setHeaderText("Choose mode: add vertex or remove them." +
                "\nAdd vertex by clicking your mouse on surface." +
                "\nFor removing choose right mode and click on yor vertex." +
                "\nIn down side select two vertices between which the edge needs to be removed." +
                "\nClick on RUN to see work of algorithm.");
        alert.showAndWait();
    }

    private void chooseFile(){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
    }

    private void cleanSurface(){
        List<Circle> allVertex = graphView.getVertices();
        while (!allVertex.isEmpty()){
            graphView.deleteVertex(allVertex.get(0));
        }
    }

    private void setupMouseHandlers() {
        graphView.getGraphPane().setOnMouseClicked(event -> {
            if (!editMode) return;

            if (event.getTarget() == graphView.getGraphPane()) {
                if (addVertexButton.isSelected()) {
                    graphView.addVertex(event.getX(), event.getY());
                }
            } else if (event.getTarget() instanceof Circle) {
                Circle clickedVertex = (Circle) event.getTarget();
                if (deleteVertexButton.isSelected()) {
                    graphView.deleteVertex(clickedVertex);
                } else {
                    handleVertexClick(clickedVertex);
                }
            }
        });

    }

    private void toggleEditMode() {
        editMode = !editMode;
        toggleButton.setText(editMode ? "Run: ON" : "Run: OFF");

        addVertexButton.setDisable(!editMode);
        deleteVertexButton.setDisable(!editMode);

        if (!editMode && firstSelected != null) {
            firstSelected.setFill(javafx.scene.paint.Color.LIGHTBLUE);
            firstSelected = null;
        }

    }

    private void handleVertexClick(Circle clickedVertex) {
        if (!editMode || deleteVertexButton.isSelected()) return;

        if (firstSelected == null) {
            firstSelected = clickedVertex;
            firstSelected.setFill(javafx.scene.paint.Color.ORANGE);
        } else if (firstSelected == clickedVertex) {
            firstSelected.setFill(javafx.scene.paint.Color.LIGHTBLUE);
            firstSelected = null;
        } else {
            graphView.connectVertices(firstSelected, clickedVertex);
            firstSelected.setFill(javafx.scene.paint.Color.LIGHTBLUE);
            firstSelected = null;
        }
    }
}
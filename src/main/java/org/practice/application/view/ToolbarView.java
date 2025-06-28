package org.practice.application.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ToolbarView {
    private final Map<String, ButtonBase> buttons;
    private TextField firstVertexField;
    private TextField secondVertexField;
    private final HBox mainToolbar;
    private final HBox edgeToolbar;

    public ToolbarView() {
        this.buttons = new HashMap<>();
        this.mainToolbar = createToolbar();
        this.edgeToolbar = createEdgeToolbar();
    }

    private HBox createToolbar() {
        buttons.put("run", createButton("Run: ON", "runButton"));
        buttons.put("clean", createButton("Clean", "cleanButton"));
        buttons.put("load", createButton("Load graph", "loadButton"));
        buttons.put("help", createButton("", "helpButton"));
        try {
            ImageView helpIcon = loadIcon("/images/help_icon.png",20);
            buttons.get("help").setGraphic(helpIcon);
        } catch (IOException e) {
            System.err.println("Icon load failed: " + e.getMessage());
            buttons.get("help").setText("?");
        }

        ToggleGroup vertexToolsGroup = new ToggleGroup();

        ToggleButton addVertexButton = new ToggleButton("Add vertex");
        addVertexButton.setToggleGroup(vertexToolsGroup);
        addVertexButton.setSelected(true);
        addVertexButton.setStyle("-fx-base: #a0e0a0;");

        ToggleButton deleteVertexButton = new ToggleButton("Remove Vertex");
        deleteVertexButton.setToggleGroup(vertexToolsGroup);
        deleteVertexButton.setStyle("-fx-base: #e0a0a0;");

        buttons.put("addVertex", addVertexButton);
        buttons.put("deleteVertex", deleteVertexButton);

        StackPane helpContainer = new StackPane(buttons.get("help"));
        helpContainer.setAlignment(Pos.TOP_RIGHT);
        StackPane.setMargin(buttons.get("help"), new Insets(5, 5, 0, 0));

        HBox leftSection = new HBox(10,
                buttons.get("run"),
                buttons.get("clean"),
                buttons.get("load"));
        leftSection.setAlignment(Pos.CENTER_LEFT);

        HBox centerSection = new HBox(10,
                buttons.get("addVertex"),
                buttons.get("deleteVertex"));
        centerSection.setAlignment(Pos.CENTER);

        BorderPane container = new BorderPane();
        container.setLeft(leftSection);
        container.setCenter(centerSection);
        container.setRight(helpContainer);
        container.setPadding(new Insets(5));

        HBox toolbar = new HBox(container);
        toolbar.setAlignment(Pos.CENTER);
        HBox.setHgrow(container, Priority.ALWAYS);

        return toolbar;
    }

    private HBox createEdgeToolbar() {
        firstVertexField = new TextField("Vertex 1");
        secondVertexField = new TextField("Vertex 2");

        firstVertexField.setPrefWidth(80);
        secondVertexField.setPrefWidth(80);

        buttons.put("addEdge", createButton("+", "addEdgeButton"));
        buttons.get("addEdge").setStyle("-fx-font-weight: bold; -fx-base: #a0e0a0;");

        buttons.put("removeEdge", createButton("-", "removeEdgeButton"));
        buttons.get("removeEdge").setStyle("-fx-font-weight: bold; -fx-base: #e0a0a0;");

        HBox controls = new HBox(10,
                new Label("Edge Management"),
                firstVertexField,
                new Label("--"),
                secondVertexField,
                buttons.get("addEdge"),
                buttons.get("removeEdge")
        );
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new javafx.geometry.Insets(10));
        controls.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #ccc; -fx-border-width: 1;");
        return controls;
    }

    private ImageView loadIcon(String path, int size) throws  IOException{
        InputStream stream = getClass().getResourceAsStream(path);
        if (stream == null) {
            throw new IOException("Icon not found: " + path);
        }

        Image img = new Image(stream);
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        return imageView;
    }

    private Button createButton(String text, String id) {
        Button button = text.isEmpty() ? new Button() : new Button(text);
        button.setId(id);
        return button;
    }

    public ButtonBase getButton(String key) {
        return buttons.get(key);
    }

    public HBox getToolbar() {
        return mainToolbar;
    }

    public HBox getEdgeToolbar() {
        return edgeToolbar;
    }

    public TextField getFirstVertexField() {
        return firstVertexField;
    }

    public TextField getSecondVertexField() {
        return secondVertexField;
    }
}

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
    private final int iconSize;

    public ToolbarView() {
        this.buttons = new HashMap<>();
        this.mainToolbar = createToolbar();
        this.edgeToolbar = createEdgeToolbar();
        this.iconSize = 20;
    }

    private HBox createToolbar() {
        buttons.put("run", createToggleButton("Run", "run-button"));
        buttons.put("clean", createToggleButtonIcon("Clean", "clean-button"));
        buttons.put("load", createToggleButtonIcon("Load", "load-button"));
        buttons.put("save", createToggleButtonIcon ("Save","save-button"));
        buttons.put("help", createToggleButtonIcon("Help", "help-button"));
        buttons.put("addVertex", createToggleButton("Add vertex", "add-vertex-button"));
        buttons.put("deleteVertex", createToggleButton("Remove vertex", "delete-vertex-button"));
        buttons.put("cursor", createToggleButtonIcon("Cursor", "cursor-button"));
        buttons.put("result", createToggleButton("Get result", "result-button"));

        ToggleGroup vertexToolsGroup = new ToggleGroup();
        String[] keys = {"addVertex", "deleteVertex", "cursor"};
        for (String key : keys) {
            ButtonBase button = buttons.get(key);
            if (button instanceof ToggleButton) {
                ((ToggleButton) button).setToggleGroup(vertexToolsGroup);
            }
        }

        StackPane helpContainer = new StackPane(buttons.get("help"));
        helpContainer.setAlignment(Pos.TOP_RIGHT);
        StackPane.setMargin(buttons.get("help"), new Insets(5, 5, 0, 0));

        HBox leftSection = new HBox(10,
                buttons.get("run"),
                buttons.get("clean"),
                buttons.get("load"),
                buttons.get("save"),
                buttons.get("result"));
        leftSection.setAlignment(Pos.CENTER_LEFT);

        HBox centerSection = new HBox(10,
                buttons.get("cursor"),
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
        firstVertexField = createTextField("Vertex 1", 80);
        secondVertexField = createTextField("Vertex 2", 80);

        buttons.put("addEdge", createToggleButton("+", "add-edge-button"));
        buttons.put("deleteEdge", createToggleButton("-", "delete-edge-button"));

        Label edgeManagement = new Label("Edge Management");
        edgeManagement.setId("edge-management-label");
        edgeManagement.getStyleClass().add("edge-management-label");

        HBox controls = new HBox(10,
                edgeManagement,
                firstVertexField,
                new Label("--"),
                secondVertexField,
                buttons.get("addEdge"),
                buttons.get("deleteEdge")
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

    private ToggleButton createToggleButton(String text, String id) {
        ToggleButton button = new ToggleButton(text);
        button.setId(id);
        button.getStyleClass().add(id);
        return button;
    }

    private ToggleButton createToggleButtonIcon(String text, String id) {
        ToggleButton button = new ToggleButton();
        button.setId(id);
        String name = text.toLowerCase() + "_icon.png";
        String path = "/images/" + name;
        try {
            ImageView iconButton = loadIcon(path, iconSize);
            button.setGraphic(iconButton);
            button.setTooltip(new Tooltip(text));
        } catch (IOException exception) {
            System.err.println("Icon load failed: " + exception.getMessage());
            button.setText(text);
        }
        return button;
    }

    private TextField createTextField(String text, int prefWidth) {
        TextField textField = new TextField();
        textField.getStyleClass().add("text-field");
        textField.setPromptText(text);
        textField.setPrefWidth(prefWidth);
        return textField;
    }

    public Map<String, ButtonBase> getButtons() {
        return buttons;
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

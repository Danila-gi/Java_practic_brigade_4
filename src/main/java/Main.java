import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import java.util.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;  // Для диалоговых окон

public class Main extends Application {
    private final Pane graphPane = new Pane();
    private final List<Circle> vertices = new ArrayList<>();
    private final List<Text> vertexLabels = new ArrayList<>();
    private Circle firstSelected = null;
    private final List<Line> edges = new ArrayList<>();
    private boolean editMode = true;
    private Button toggleButton;
    private Button cleanButton;
    private Button helpButton;
    private ToggleButton addVertexButton;
    private ToggleButton deleteVertexButton;
    private int vertexCounter = 1;

    private TextField vertex1Field;
    private TextField vertex2Field;
    private Button addEdgeButton;
    private Button removeEdgeButton;

    @Override
    public void start(Stage primaryStage) {
        // Настройка панели для графа с рамкой
        StackPane graphContainer = new StackPane(graphPane);
        graphContainer.setPadding(new Insets(10));
        graphContainer.setStyle("-fx-border-color: #999; -fx-border-width: 2; -fx-background-color: white;");
        graphPane.setPrefSize(600, 400);

        // Создаем панель инструментов
        HBox toolbar = createToolbar();

        HBox edgeControls = createEdgeControls();



        // Главный контейнер
        VBox root = new VBox(10, toolbar, graphContainer, edgeControls);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #f0f0f0;");


        // Обработчики событий
        setupMouseHandlers();

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Graph Editor with Vertex Tools");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createToolbar() {
        // Кнопки режима редактирования
        toggleButton = new Button("Run: ВКЛ");
        toggleButton.setOnAction(e -> toggleEditMode());

        cleanButton = new Button("Clean");

        // Кнопка Help с иконкой
        helpButton = new Button();
        helpButton.setGraphic(loadHelpIcon());
        helpButton.setOnAction(e -> showHelp());

        // Группа кнопок для добавления/удаления вершин
        ToggleGroup vertexToolsGroup = new ToggleGroup();

        addVertexButton = new ToggleButton("Добавить вершину");
        addVertexButton.setToggleGroup(vertexToolsGroup);
        addVertexButton.setSelected(true);
        addVertexButton.setStyle("-fx-base: #a0e0a0;");

        deleteVertexButton = new ToggleButton("Удалить вершину");
        deleteVertexButton.setToggleGroup(vertexToolsGroup);
        deleteVertexButton.setStyle("-fx-base: #e0a0a0;");

        // Создаем отдельный контейнер для кнопки Help
        StackPane helpContainer = new StackPane(helpButton);
        helpContainer.setAlignment(Pos.TOP_RIGHT);
        StackPane.setMargin(helpButton, new Insets(5, 5, 0, 0));

        // Основная панель инструментов слева
        HBox leftToolbar = new HBox(10, toggleButton, cleanButton);
        leftToolbar.setAlignment(Pos.CENTER_LEFT);

        // Центральная панель с инструментами вершин
        HBox centerToolbar = new HBox(10, addVertexButton, deleteVertexButton);
        centerToolbar.setAlignment(Pos.CENTER);

        // Главный контейнер панели инструментов
        BorderPane toolContainer = new BorderPane();
        toolContainer.setLeft(leftToolbar);
        toolContainer.setCenter(centerToolbar);
        toolContainer.setRight(helpContainer);
        toolContainer.setPadding(new Insets(5));

        // Возвращаем HBox с растягиванием
        HBox toolbar = new HBox(toolContainer);
        toolbar.setAlignment(Pos.CENTER);
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
        // Поля для ввода номеров вершин
        vertex1Field = new TextField();
        vertex1Field.setPromptText("Вершина 1");
        vertex1Field.setPrefWidth(80);

        vertex2Field = new TextField();
        vertex2Field.setPromptText("Вершина 2");
        vertex2Field.setPrefWidth(80);

        // Кнопки для управления рёбрами
        addEdgeButton = new Button("+");
        addEdgeButton.setStyle("-fx-font-weight: bold; -fx-base: #a0e0a0;");
        addEdgeButton.setOnAction(e -> addEdgeByNumbers());

        removeEdgeButton = new Button("-");
        removeEdgeButton.setStyle("-fx-font-weight: bold; -fx-base: #e0a0a0;");
        removeEdgeButton.setOnAction(e -> removeEdgeByNumbers());

        // Панель для элементов управления
        HBox controls = new HBox(10,
                new Label("Управление рёбрами:"),
                vertex1Field,
                new Label("—"),
                vertex2Field,
                addEdgeButton,
                removeEdgeButton);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));
        controls.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #ccc; -fx-border-width: 1;");

        return controls;
    }

    private void removeEdgeByNumbers() {
    }

    private void addEdgeByNumbers() {
    }


    private void showHelp() {
        System.out.println("Help button clicked");
    }

    private void setupMouseHandlers() {
        graphPane.setOnMouseClicked(event -> {
            if (!editMode) return;

            if (event.getTarget() == graphPane) {
                // Клик на пустое место - добавляем вершину если выбран режим добавления
                if (addVertexButton.isSelected()) {
                    addVertex(event.getX(), event.getY());
                }
            } else if (event.getTarget() instanceof Circle) {
                // Клик на вершину
                Circle clickedVertex = (Circle) event.getTarget();
                if (deleteVertexButton.isSelected()) {
                    deleteVertex(clickedVertex);
                } else {
                    handleVertexClick(clickedVertex);
                }
            }
        });
    }

    private void toggleEditMode() {
        editMode = !editMode;
        toggleButton.setText(editMode ? "Run: ВКЛ" : "Run: ВЫКЛ");

        // Блокируем кнопки работы с вершинами в режиме просмотра
        addVertexButton.setDisable(!editMode);
        deleteVertexButton.setDisable(!editMode);

        if (!editMode && firstSelected != null) {
            firstSelected.setFill(Color.LIGHTBLUE);
            firstSelected = null;
        }

        vertices.forEach(vertex -> vertex.setOnMouseDragged(editMode ? e -> {
            vertex.setCenterX(e.getX());
            vertex.setCenterY(e.getY());
            updateVertexLabel(vertex);
        } : null));
    }

    private void addVertex(double x, double y) {
        if (!editMode || !addVertexButton.isSelected()) return;

        Circle vertex = new Circle(x, y, 15, Color.LIGHTBLUE);
        vertex.setStroke(Color.BLACK);
        vertex.setStrokeWidth(2);

        vertex.setOnMouseDragged(e -> {
            if (!editMode) return;
            vertex.setCenterX(e.getX());
            vertex.setCenterY(e.getY());
            updateVertexLabel(vertex);
        });

        vertices.add(vertex);
        graphPane.getChildren().add(vertex);

        // Добавляем текст с номером вершины
        Text label = new Text(String.valueOf(vertexCounter));
        label.setX(x - 5);
        label.setY(y + 5);
        vertexLabels.add(label);
        graphPane.getChildren().add(label);

        vertexCounter++;
    }

    private void deleteVertex(Circle vertex) {
        if (!editMode || !deleteVertexButton.isSelected()) return;

        // Удаляем все связанные ребра
        List<Line> edgesToRemove = new ArrayList<>();
        for (Line edge : edges) {
            if (edge.getStartX() == vertex.getCenterX() && edge.getStartY() == vertex.getCenterY() ||
                    edge.getEndX() == vertex.getCenterX() && edge.getEndY() == vertex.getCenterY()) {
                edgesToRemove.add(edge);
            }
        }
        edges.removeAll(edgesToRemove);
        graphPane.getChildren().removeAll(edgesToRemove);

        // Удаляем метку вершины
        int index = vertices.indexOf(vertex);
        if (index >= 0 && index < vertexLabels.size()) {
            graphPane.getChildren().remove(vertexLabels.get(index));
            vertexLabels.remove(index);
        }

        // Удаляем саму вершину
        graphPane.getChildren().remove(vertex);
        vertices.remove(vertex);

        // Сбрасываем выделение если удалили выделенную вершину
        if (firstSelected == vertex) {
            firstSelected = null;
        }
    }

    private void updateVertexLabel(Circle vertex) {
        int index = vertices.indexOf(vertex);
        if (index >= 0 && index < vertexLabels.size()) {
            Text label = vertexLabels.get(index);
            label.setX(vertex.getCenterX() - 5);
            label.setY(vertex.getCenterY() + 5);
        }
    }

    private void handleVertexClick(Circle clickedVertex) {
        if (!editMode || deleteVertexButton.isSelected()) return;

        if (firstSelected == null) {
            firstSelected = clickedVertex;
            firstSelected.setFill(Color.ORANGE);
        } else if (firstSelected == clickedVertex) {
            firstSelected.setFill(Color.LIGHTBLUE);
            firstSelected = null;
        } else {
            connectVertices(firstSelected, clickedVertex);
            firstSelected.setFill(Color.LIGHTBLUE);
            firstSelected = null;
        }
    }

    private void connectVertices(Circle v1, Circle v2) {
        Line edge = new Line();
        edge.setStroke(Color.BLACK);
        edge.setStrokeWidth(2);

        edge.startXProperty().bind(v1.centerXProperty());
        edge.startYProperty().bind(v1.centerYProperty());
        edge.endXProperty().bind(v2.centerXProperty());
        edge.endYProperty().bind(v2.centerYProperty());

        edges.add(edge);
        graphPane.getChildren().add(edge);
    }




    public static void main(String[] args) {
        launch(args);
    }
}
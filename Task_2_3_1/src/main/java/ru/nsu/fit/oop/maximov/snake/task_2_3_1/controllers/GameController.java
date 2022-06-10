package ru.nsu.fit.oop.maximov.snake.task_2_3_1.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.SnakeApplication;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.Game;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.snake.Direction;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.view.Painter;

import java.io.IOException;

public class GameController {
    @FXML
    private Canvas canvas;

    private final int WIDTH = 30;
    private final int HEIGHT = 30;
    private final int POINT_SIZE = 20;

    private final Game game;
    private final Painter painter;

    private Timeline timeline;

    private Stage stage;
    private Scene scene;

    public GameController() {
        game = new Game(WIDTH, HEIGHT);
        painter = new Painter();
    }

    public void initialize(Stage stage, Scene scene) {
        this.stage = stage;
        this.scene = scene;

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP -> game.directSnake(Direction.UP);
                case LEFT -> game.directSnake(Direction.LEFT);
                case DOWN -> game.directSnake(Direction.DOWN);
                case RIGHT -> game.directSnake(Direction.RIGHT);
            }
        });

        timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> gameLoop(canvas.getGraphicsContext2D())));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    void gameLoop(GraphicsContext gc) {
        if (!game.isFinish()) {
            game.moveSnake();
            painter.paintMap(game.map(), gc);
            return;
        }

        timeline.stop();

        FXMLLoader fxmlLoader = new FXMLLoader(SnakeApplication.class.getResource("end-view.fxml"));
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 720);
        } catch (IOException ignored) {
        }
        ((MenuController) fxmlLoader.getController()).setScore(game.getScore());
        stage.setScene(scene);
        stage.show();
    }
}

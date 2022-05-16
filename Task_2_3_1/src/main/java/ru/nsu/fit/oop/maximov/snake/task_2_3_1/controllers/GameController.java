package ru.nsu.fit.oop.maximov.snake.task_2_3_1.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.Game;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.Move;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.view.Painter;

import java.io.IOException;

public class GameController {
    @FXML
    Canvas canvas;

    private final int WIDTH = 30;
    private final int HEIGHT = 30;
    private final int POINT_SIZE = 20;

    Game game;
    Painter painter = new Painter();

    public GameController() {
        game = new Game(WIDTH, HEIGHT);
    }

    public void initialize(Stage stage, Scene scene) {
        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();
            if (code == KeyCode.RIGHT || code == KeyCode.D) {
                game.moveSnake(Move.RIGHT);
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                game.moveSnake(Move.LEFT);
            } else if (code == KeyCode.UP || code == KeyCode.W) {
                game.moveSnake(Move.UP);
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                game.moveSnake(Move.DOWN);
            }
            else if (code == KeyCode.SPACE) {
                game.putFood();
            }
        });

        var timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> gameLoop(canvas.getGraphicsContext2D())));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    void gameLoop(GraphicsContext gc) {
        game.moveSnake();
        painter.paintMap(game, gc);
    }
}

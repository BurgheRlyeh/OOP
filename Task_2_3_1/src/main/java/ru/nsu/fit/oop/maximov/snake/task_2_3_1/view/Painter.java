package ru.nsu.fit.oop.maximov.snake.task_2_3_1.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.point.Point;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.map.GameMap;

public class Painter {
    private static final int POINT_SIZE = 20;

    private static final Color WALL_COLOR = Color.GRAY;
    private static final Color SNAKE_COLOR = Color.DARKGREEN;
    private static final Color FOOD_COLOR = Color.RED;
    private static final Color DEFAULT_COLOR = Color.PALETURQUOISE;

    public void paintMap(GameMap map, GraphicsContext graphicsContext) {
        for (var point : map) {
            paintPoint(point, graphicsContext);
        }
    }

    private void paintPoint(Point point, GraphicsContext gc) {
        gc.setFill(
                switch (point.type()) {
                    case FOOD -> FOOD_COLOR;
                    case SNAKE -> SNAKE_COLOR;
                    case WALL -> WALL_COLOR;
                    default -> DEFAULT_COLOR;
                }
        );
        gc.fillRect(point.x() * POINT_SIZE, point.y() * POINT_SIZE, POINT_SIZE, POINT_SIZE);
    }
}

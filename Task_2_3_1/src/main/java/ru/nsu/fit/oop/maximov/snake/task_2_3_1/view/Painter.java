package ru.nsu.fit.oop.maximov.snake.task_2_3_1.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.Game;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.Point;

public class Painter {
    private static final int CELL_SIZE = 20;
    private static final Color BG_COLOR = Color.BLACK;
    private static final Color WALL_COLOR = Color.BEIGE;
    private static final Color SNAKE_COLOR = Color.DARKGREEN;
    private static final Color FOOD_COLOR = Color.RED;
    private static final Color DEFAULT_COLOR = Color.AQUAMARINE;

    public void paintMap(Game game, GraphicsContext graphicsContext) {
        var map = game.map;
        graphicsContext.setFill(BG_COLOR);
        graphicsContext.fillRect(0, 0, map.width() * CELL_SIZE, map.height() * CELL_SIZE);

        for (int y = 0; y < map.height(); ++y) {
            for (int x = 0; x < map.width(); ++x) {
                paintPoint(map.getPoint(x, y), graphicsContext);
            }
        }
    }

    private void paintPoint(Point point, GraphicsContext graphicsContext) {
        graphicsContext.setFill(
                switch (point.getType()) {
                    case FOOD -> FOOD_COLOR;
                    case SNAKE -> SNAKE_COLOR;
                    case WALL -> WALL_COLOR;
                    default -> DEFAULT_COLOR;
                }
        );
        graphicsContext.fillRect(point.x() * CELL_SIZE, point.y() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }
}

package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model;

import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.map.GameMap;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.point.Point;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.snake.Direction;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.snake.Snake;

/**
 * Main game class
 */
public class Game {
    private final GameMap map;

    /**
     * @return game map
     */
    public GameMap map() {
        return map;
    }

    private final Snake snake;

    /**
     * @param width  width of the game map in Points
     * @param height height of the game map in Points
     */
    public Game(int width, int height) {
        map = new GameMap(width, height);
        snake = new Snake(map, new Point(width / 2 - 1, height / 2 - 1));

        generateFood();
    }

    /**
     * @param move new direction of the snake to set
     */
    public void directSnake(Direction move) {
        snake.setDirection(move);
    }

    /**
     * implements one step game snake
     */
    public void moveSnake() {
        snake.move();
    }

    /**
     * generate food on the map
     */
    public void generateFood() {
        map.generateFood();
    }

    /**
     * @return is game finish
     */
    public boolean isFinish() {
        return !snake.isAlive();
    }

    /**
     * @return game score
     */
    public int getScore() {
        return snake.getSize();
    }
}

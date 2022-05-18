package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.snake;

import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.point.Point;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.map.GameMap;

import java.util.ArrayDeque;
import java.util.Deque;

import static ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.point.PointType.*;

/**
 * A class that represents a game snake
 */
public class Snake {
    private final GameMap map;
    private Direction direction;
    private boolean isAlive = true;

    public boolean isAlive() {
        return isAlive;
    }

    private final Deque<Point> snake = new ArrayDeque<>();

    /**
     * @param map           game map for the snake
     * @param startPosition start point, where snake will be spawned
     */
    public Snake(GameMap map, Point startPosition) {
        this.map = map;

        startPosition.setType(SNAKE);
        snake.add(startPosition);
        setDirection(Direction.RIGHT);
    }

    /**
     * @param direction new direction of the snake to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * the function implements one step game snake
     */
    public void move() {
        var next = direction.move(snake.getFirst(), map);
        switch (next.type()) {
            case FREE -> {
                snake.addFirst(next);
                map.getPoint(next).setType(SNAKE);
                map.getPoint(snake.removeLast()).setType(FREE);
            }
            case FOOD -> {
                snake.addFirst(next);
                map.getPoint(next).setType(SNAKE);
                map.generateFood();
            }
            case WALL, SNAKE -> isAlive = false;
        }
    }

    /**
     * @return size of the snake
     */
    public int getSize() {
        return snake.size();
    }
}

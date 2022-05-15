package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model;

import java.util.ArrayDeque;

import static ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.PointType.*;

public class Snake {
    Map map;

    private ArrayDeque<Point> snake;

    public Snake(Map map, Point startPosition) {
        snake = new ArrayDeque<>();
        startPosition.type = PointType.SNAKE;
        snake.add(startPosition);
    }

    public void move(Move direction) {
        var next = map.next(snake.getLast(), direction);
        switch (next.type) {
            case FREE -> {
                map.getPoint(snake.removeFirst()).type = FREE;
                snake.addLast(next);
                map.getPoint(next).type = SNAKE;
            }
            case SNAKE -> {
                // TODO
            }
            case FOOD -> {
                snake.addLast(next);
                map.getPoint(next).type = SNAKE;
            }
            case WALL -> {
                for (var part : snake) {
                    map.getPoint(snake.removeFirst()).type = FOOD;
                }
                // TODO
            }
        }
    }
}

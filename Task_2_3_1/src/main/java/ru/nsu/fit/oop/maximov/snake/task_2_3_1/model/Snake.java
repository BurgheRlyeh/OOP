package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model;

import java.util.ArrayDeque;
import java.util.Deque;

import static ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.PointType.*;

public class Snake {
    Game game;
    Map map;
    Move direction;

    private final Deque<Point> snake;

    public Snake(Game game, Map map, Point startPosition) {
        this.game = game;
        this.map = map;

        snake = new ArrayDeque<>();
        startPosition.type = PointType.SNAKE;
        snake.add(startPosition);
        setDirection(Move.LEFT);
    }

    public void setDirection(Move direction) {
        this.direction = direction;
    }

    public void move() {
        var next = map.next(snake.getFirst(), direction);
        switch (next.type) {
            case FREE -> {
                snake.addFirst(next);
                map.getPoint(next).type = SNAKE;
                map.getPoint(snake.removeLast()).type = FREE;
            }
            case SNAKE -> {
                // TODO
                System.out.println("SNAKE");
            }
            case FOOD -> {
                snake.addFirst(next);
                map.getPoint(next).type = SNAKE;
                game.putFood();
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

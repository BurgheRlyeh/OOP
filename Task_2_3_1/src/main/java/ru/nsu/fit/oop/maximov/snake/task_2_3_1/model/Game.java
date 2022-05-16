package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model;

import java.util.Random;

public class Game {
    public Map map;
    public Snake snake;

    public Game(int width, int height) {
        map = new Map(this, width, height);
        snake = new Snake(this, map, map.getPoint(width / 2 - 1, height / 2 - 1));
        putFood();
    }

    public void moveSnake(Move move) {
        snake.setDirection(move);
    }

    public void moveSnake() {
        snake.move();
    }

    public void putFood() {
        int foodX, foodY;
        do {
            foodX = new Random().nextInt(map.width());
            foodY = new Random().nextInt(map.height());
        } while (map.getPoint(foodX, foodY).getType() != PointType.FREE);

        map.getPoint(foodX, foodY).type = PointType.FOOD;
    }
}

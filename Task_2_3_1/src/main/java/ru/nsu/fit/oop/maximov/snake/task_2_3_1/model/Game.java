package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model;

public class Game {
    public Map map;
    public Snake snake;

    public Game(int width, int height) {
        map = new Map(this, width, height);
        snake = new Snake(map, new Point(width / 2, height / 2));
    }
}

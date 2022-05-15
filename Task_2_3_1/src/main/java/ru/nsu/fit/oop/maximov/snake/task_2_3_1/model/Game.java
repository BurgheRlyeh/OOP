package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model;

public class Game {
    public Map map;
    public Snake snake;

    private int score = 0;
    private final int scoreLimit;

    public Game(int width, int height, int scoreLimit) {
        map = new Map(this, width, height);
        snake = new Snake(map, new Point(width / 2, height / 2));

        this.scoreLimit = scoreLimit;

    }

    public void increaseScore() {
        ++score;
    }

    public Integer getScore() {
        return score;
    }

    public int getScoreLimit() {
        return scoreLimit;
    }


}

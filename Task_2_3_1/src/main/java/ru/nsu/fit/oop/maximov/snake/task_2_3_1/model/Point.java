package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model;

public class Point {
    int x;
    int y;
    PointType type;

    public Point(int x, int y) {
        this(x, y, PointType.FREE);
    }

    public Point(int x, int y, PointType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public boolean isInTheArea(Point start, Point end) {
        return start.x <= x && x <= end.x
                && start.y <= y && y <= end.y;
    }

    public boolean isXInTheInterval(Point start, Point end) {
        return start.x <= x && x <= end.x;
    }

    public boolean isYInTheInterval(Point start, Point end) {
        return start.y <= y && y <= end.y;
    }

    public PointType getType() {
        return type;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
}
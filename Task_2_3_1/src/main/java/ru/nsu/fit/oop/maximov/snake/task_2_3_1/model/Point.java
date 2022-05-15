package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model;

class Point {
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
}
package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model;

public class Map {
    private final Game model;

    private Point[][] map;
    private final int width;
    private final int height;

    public Map(Game model, int width, int height) {
        this.model = model;

        this.width = width;
        this.height = height;

        map = new Point[height][width];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                map[y][x] = new Point(x, y);
            }
        }
    }

    public Point getPoint(Point point) {
        return map[point.y][point.x];
    }

    private boolean isPointInTheArea(Point point) {
        return point.isInTheArea(new Point(0, 0), new Point(width - 1, height - 1));
    }

    private void updatePoint(Point point) {
        point.x = point.x < 0 ? width - 1 : 0;
        point.y = point.y < 0 ? height - 1 : 0;
    }

    public Point next(Point point, Move move) {
        if (!isPointInTheArea(point)) {
            throw new IllegalArgumentException();
        }
        var next = move.move(point); //point.next(direction);
        if (!isPointInTheArea(next)) {
            updatePoint(next);
        }
        return next;
    }
}

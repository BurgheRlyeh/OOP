package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.point;

/**
 * The class implements the GameMap unit
 */
public class Point {
    private final int x;

    /**
     * @return x coordinate of the point
     */
    public int x() {
        return x;
    }

    private final int y;

    /**
     * @return y coordinate of the point
     */
    public int y() {
        return y;
    }

    private PointType type = PointType.FREE;

    /**
     * @return type of the point
     */
    public PointType type() {
        return type;
    }

    /**
     * @param type new type of the point to set
     */
    public void setType(PointType type) {
        this.type = type;
    }

    /**
     * @param x x coordinate of the point
     * @param y y coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
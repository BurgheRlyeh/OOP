package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.map;

import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.point.Point;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Playing game map class
 */
public class GameMap implements Iterable<Point> {
    private final Point[][] map;

    private final int width;

    /**
     * @return width of map in Points
     */
    public int width() {
        return width;
    }

    private final int height;

    /**
     * @return height of map in Points
     */
    public int height() {
        return height;
    }

    /**
     * @param width width of map in Points
     * @param height height of map in Points
     */
    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;

        map = new Point[height][width];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                map[y][x] = new Point(x, y);
            }
        }

        WallsGenerator.generateWalls(this, 100);
    }

    /**
     * @param point point to get
     * @return point on the map with equivalent coordinates
     */
    public Point getPoint(Point point) {
        return map[point.y()][point.x()];
    }

    /**
     * @param point point to validate
     * @return validated point on the map
     */
    public Point validatedPoint(Point point) {
        int x = point.x();
        if (width <= x || x < 0) {
            x = x < 0 ? width - 1 : 0;
        }

        int y = point.y();
        if (height <= y || y < 0) {
            y = y < 0 ? height - 1 : 0;
        }

        return getPoint(new Point(x, y));
    }

    /**
     * generate food on the map
     */
    public void generateFood() {
        FoodGenerator.generateFood(this);
    }

    @Override
    public Iterator<Point> iterator() {
        var mapArray = Stream.of(map)
                .flatMap(Stream::of)
                .toArray(Point[]::new);
        return Arrays.stream(mapArray).iterator();
    }
}

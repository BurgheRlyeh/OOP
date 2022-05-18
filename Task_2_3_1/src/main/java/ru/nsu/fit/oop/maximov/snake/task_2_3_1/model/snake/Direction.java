package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.snake;

import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.point.Point;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.map.GameMap;

/**
 * Enum of all directions for Snake movement
 */
public enum Direction {
    UP {
        public Point next(Point point) {
            return new Point(point.x(), point.y() - 1);
        }
    },
    LEFT {
        public Point next(Point point) {
            return new Point(point.x() - 1, point.y());
        }
    },
    DOWN {
        public Point next(Point point) {
            return new Point(point.x(), point.y() + 1);
        }
    },
    RIGHT {
        public Point next(Point point) {
            return new Point(point.x() + 1, point.y());
        }
    };

    abstract Point next(Point point);

    /**
     * @param point point to move
     * @param map the game map on which the movement takes place
     * @return the moved point on the game map
     */
    public Point move(Point point, GameMap map) {
        return map.validatedPoint(next(point));
    }
}

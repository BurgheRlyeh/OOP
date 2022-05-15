package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model;

public enum Move {
    UP {
        public Point move(Point point) {
            return new Point(point.x, point.y - 1);
        }
    },
    LEFT {
        public Point move(Point point) {
            return new Point(point.x - 1, point.y);
        }
    },
    DOWN {
        public Point move(Point point) {
            return new Point(point.x, point.y + 1);
        }
    },
    RIGHT {
        public Point move(Point point) {
            return new Point(point.x + 1, point.y);
        }
    };

    public abstract Point move(Point point);
}

package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model;

public enum Direction {
    UP {
        public Point move() {
            return new Point(0, -1);
        }
    },
    LEFT {
        public Point move() {
            return new Point(-1, 0);
        }
    },
    DOWN {
        public Point move() {
            return new Point(0, 1);
        }
    },
    RIGHT {
        public Point move() {
            return new Point(1, 0);
        }
    };

    public abstract Point move();
}

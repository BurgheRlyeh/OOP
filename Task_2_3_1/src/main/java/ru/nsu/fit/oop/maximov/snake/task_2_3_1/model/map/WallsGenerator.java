package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.map;

import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.snake.Direction;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.point.Point;

import java.util.Arrays;
import java.util.Random;

import static ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.point.PointType.*;

/**
 * A class that implements a static function for generating valid walls on the gameMap
 * "valid" means that obstacles are guaranteed such that the player can reach any point on the map and get out of it
 */
public class WallsGenerator {
    /**
     * @param map map on which to generate walls
     * @param numberOfWalls number of walls to generate
     */
    public static void generateWalls(GameMap map, int numberOfWalls) {
        generateWalls(map, (double) numberOfWalls / (map.width() * map.height()));
        validateWalls(map);
    }

    private static void generateWalls(GameMap map, double wallProbability) {
        var random = new Random();

        for (var point : map) {
            if (map.width() / 2 - 1 <= point.x() && point.x() <= map.width() / 2
                || map.height() / 2 - 1 <= point.y() && point.y() <= map.height() / 2) {
                continue;
            }
            if (random.nextDouble() <= wallProbability) {
                point.setType(WALL);
            }
        }
    }

    private static int pointFreeNeighbors(GameMap map, Point point) {
        return (int) Arrays.stream(Direction.values())
                .filter(direction -> direction.move(point, map).type() != WALL)
                .count();
    }

    private static void validateWalls(GameMap map) {
        var freeNext = false;

        for (var point : map) {
            if (point.type() == FREE) {
                switch (pointFreeNeighbors(map, point)) {
                    case 0, 1 -> point.setType(WALL);
                    case 2 -> freeNext = true;
                    default -> freeNext = false;
                }
            }
            if (freeNext) {
                point.setType(FREE);
            }
        }
    }
}

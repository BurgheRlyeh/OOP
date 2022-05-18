package ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.map;

import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.point.Point;

import java.util.Random;

import static ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.point.PointType.*;

/**
 * A class that implements a static function for generating food on the gameMap
 */
public class FoodGenerator {
    /**
     * Static function for generating food on the gameMap
     *
     * @param map gameMap on which you want to generate food
     */
    public static void generateFood(GameMap map) {
        var random = new Random();

        Point food;

        do {
            food = new Point(random.nextInt(map.width()), random.nextInt(map.height()));
        } while (map.getPoint(food).type() != FREE);

        map.getPoint(food).setType(FOOD);
    }
}

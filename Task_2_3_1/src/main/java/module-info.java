module ru.nsu.fit.oop.maximov.snake.task_2_3_1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nsu.fit.oop.maximov.snake.task_2_3_1 to javafx.fxml;
    exports ru.nsu.fit.oop.maximov.snake.task_2_3_1;
    exports ru.nsu.fit.oop.maximov.snake.task_2_3_1.controllers;
    opens ru.nsu.fit.oop.maximov.snake.task_2_3_1.controllers to javafx.fxml;
}
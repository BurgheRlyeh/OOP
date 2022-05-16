package ru.nsu.fit.oop.maximov.snake.task_2_3_1.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.SnakeApplication;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.model.Game;

import java.io.IOException;

public class MenuController {
    @FXML
    private Button buttonStart;

    @FXML
    public void onButtonStartClick(ActionEvent actionEvent) throws Exception {
        gameStarter(actionEvent);
    }

    private void gameStarter(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(SnakeApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        ((GameController) fxmlLoader.getController()).initialize(stage, scene);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();

    }
}
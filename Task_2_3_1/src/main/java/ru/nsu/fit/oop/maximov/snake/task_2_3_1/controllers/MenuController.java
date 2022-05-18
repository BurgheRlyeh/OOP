package ru.nsu.fit.oop.maximov.snake.task_2_3_1.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.nsu.fit.oop.maximov.snake.task_2_3_1.SnakeApplication;

import java.io.IOException;

public class MenuController {
    @FXML
    public Button buttonEnd;
    @FXML
    public Label labelResult;
    @FXML
    public Label labelScore;
    @FXML
    private Button buttonStart;

    @FXML
    public void onButtonStartClick(ActionEvent actionEvent) throws Exception {
        gameStarter(actionEvent);
    }

    private void gameStarter(ActionEvent actionEvent) throws IOException {
        var stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(SnakeApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        ((GameController) fxmlLoader.getController()).initialize(stage, scene);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onButtonEndClick(ActionEvent actionEvent) throws IOException {
        var stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(SnakeApplication.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    public void setScore(int score) {
        labelScore.setText("Score: " + score);
    }
}
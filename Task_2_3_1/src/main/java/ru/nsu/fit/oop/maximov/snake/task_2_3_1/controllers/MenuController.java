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
    public ToolBar toolBarSpeed;

    @FXML
    public RadioButton radioButtonSpeedSlow;

    @FXML
    public RadioButton radioButtonSpeedMedium;

    @FXML
    public RadioButton radioButtonSpeedFast;

    @FXML
    public ToolBar toolBarMapSize;

    @FXML
    public TextField textFieldMapWidth;

    @FXML
    public TextField textFieldMapHeight;

    @FXML
    private Button buttonStart;

//    @FXML
//    public void initialize() {
//        for (var node : toolBarMapSize.getItems()) {
//            var textField = (TextField) node;
//            textField.textProperty().addListener((observable, oldValue, newValue) -> {
//                if (newValue.matches("\\d*")) return;
//                textField.setText(newValue.replaceAll("[^\\d]", ""));
//            });
//        }
//    }

    private void radioButtonsSpeedReset() {
        for (var node : toolBarSpeed.getItems()) {
            var radioButton = (RadioButton) node;
            radioButton.setSelected(false);
        }
    }

    @FXML
    public void onRadioButtonSpeedClick(ActionEvent actionEvent) {
        radioButtonsSpeedReset();
        var radioButtonSpeed = (RadioButton) actionEvent.getSource();
        radioButtonSpeed.setSelected(true);
    }

    private boolean isMapSizeSet() {
        for (var node : toolBarMapSize.getItems()) {
            var textField = (TextField) node;
            if (textField.getText().isEmpty() || Integer.parseInt(textField.getText()) == 0) {
                return false;
            }
        }
        return true;
    }

    @FXML
    public void onButtonStartClick(ActionEvent actionEvent) throws Exception {
        if (!isMapSizeSet()) {
//            gameStarter(actionEvent);
            throw new Exception("BEBRA_EXCEPTION");
        }

        var game = new Game(15, 15, 15);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Canvas canvas = new Canvas(
                Integer.parseInt(textFieldMapWidth.getText()) * 10,
                Integer.parseInt(textFieldMapHeight.getText()) * 10);
        var labelScore = new Label(game.getScore().toString());
        labelScore.setTextFill(Color.rgb(19, 124, 57));
        labelScore.setFont(new Font(15));
        VBox root = new VBox(labelScore,canvas);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene gameScene = new Scene(root);
        stage.setScene(gameScene);
        stage.sizeToScene();
        stage.show();
    }

    private void gameStarter(ActionEvent actionEvent) throws IOException {
        var gameController = new GameController(
                Integer.parseInt(textFieldMapWidth.getText()),
                Integer.parseInt(textFieldMapHeight.getText())
        );

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(SnakeApplication.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();

    }
}
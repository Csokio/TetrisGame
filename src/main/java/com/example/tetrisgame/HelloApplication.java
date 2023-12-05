package com.example.tetrisgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private HelloController helloController;
    private TetrisGame tetrisGame;


    @Override
    public void start(Stage stage) throws IOException, RuntimeException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 560,480,Color.OLIVE);
        Image icon = new Image("cotedemer.jpg");
        stage.getIcons().add(icon);

        stage.setResizable(false);
        stage.setTitle("Tetris");
        stage.setScene(scene);
        stage.show();

        helloController = fxmlLoader.getController();

        tetrisGame = new TetrisGame(helloController);

        tetrisGame.run();

        root.requestFocus();

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    tetrisGame.rotateRight();
                    break;
                case DOWN:
                    tetrisGame.dropBoxDown();
                    break;
                case RIGHT:
                    tetrisGame.moveRight();
                    break;
                case LEFT:
                    tetrisGame.moveLeft();
                    break;
                case P:
                    helloController.onClickPauseOrResume();
                    break;
                case SPACE:
                    tetrisGame.rotateFallingBlock();
                    break;
            }
        });

        /*ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new TetrisGame(helloController));
        executor.shutdown();*/
    }


    public static void main(String[] args) {
        launch();
    }
}
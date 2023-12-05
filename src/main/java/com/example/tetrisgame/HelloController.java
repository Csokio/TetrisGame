package com.example.tetrisgame;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HelloController {


    TetrisGame tetrisGame;

    public HelloController()
    {

    }

    public void setTetrisGame(TetrisGame tetrisGame) {
        this.tetrisGame = tetrisGame;
    }

    @FXML
    private Label welcomeText;

    @FXML
    private TextArea centerTextArea;

    @FXML
    private ToggleButton pauseOrResume;

    @FXML
    private TextField scoreTextField;
    @FXML
    private TextArea nextBlockTextArea;

    public ToggleButton getPauseOrResume()
    {
        return pauseOrResume;
    }
    public TextArea getCenterTextArea() {
        return centerTextArea;
    }

    public void setCenterTextArea(String text) {
        this.centerTextArea.setText(text);
    }
    public TextField getScoreTextField(){
        return scoreTextField;
    }
    public void setScoreTextField(String text){
        this.scoreTextField.setText(text);
    }
    public TextArea getNextBlockTextArea(){
        return this.nextBlockTextArea;
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void onClickPauseOrResume()
    {
        if(pauseOrResume.getText().equals("Pause")){
            //pauseOrResume.setText("Resume");
            tetrisGame.pause();
        }   else {
            //pauseOrResume.setText("Pause");
            tetrisGame.resume();
        }
    }
}
package com.example.tetrisgame;

import com.example.tetrisgame.blocks.Block;
import com.example.tetrisgame.blocks.MiniBlock;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.List;

public class TetrisGame implements Runnable{

    private final HelloController helloController;
    private final Level level;
    private Block fallingBlock;
    private Block nextBlock;
    private boolean drop;
    private int score;
    private volatile boolean paused = false;

    private boolean stopRequested = false;
    private final Object pauseLock = new Object();

    private Task<Void> gameTask;


    public TetrisGame(HelloController helloController){
        this.helloController = helloController;
        this.level = new Level();
        //this.fallingBlock = new LBlock();
        this.fallingBlock = Block.randomBlockType();
        System.out.println(fallingBlock.getClass());
        this.nextBlock = Block.randomBlockType();
        //this.nextBlock = new Block(BlockType.getRandomBlockType());
        helloController.setTetrisGame(this);
    }

    @Override
    public void run() {
        helloController.getCenterTextArea().setEditable(false);
        helloController.getCenterTextArea().setDisable(true);
        //String pauseOrResume = helloController.getPauseOrResumeText();


        gameTask = new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException {

                while (!isGameOver()) {
                    boolean isBlockLanded;
                    updateNextBlock();
                    do {

                        drawWithFallingBlock();
                        if (isPauseRequested()) {
                            pauseGame();
                        }
                        if (isResumeRequested()) {
                            resumeGame();
                        }
                        if (stopRequested) {
                            break;
                        }
                        isBlockLanded = move();
                        sleep(drop ? 50L : 600L);

                    } while (!fallingBlock.isAtBottom() && !isBlockLanded && !stopRequested);
                    level.mergeWith(fallingBlock);
                    int numberOfCompletedRows = level.removeCompletedRows();
                    score += numberOfCompletedRows;
                    updateScore();
                    drop = false;

                    //fallingBlock.setRow(0);
                    fallingBlock = nextBlock;
                    nextBlock = Block.randomBlockType();

                    updateNextBlock();
                    drawWithFallingBlock();

                }
                    drawEndGame();
                    return null;

            }
        };

        Thread gameThread = new Thread(gameTask);
        gameThread.setDaemon(true);
        gameThread.start();
    }



    public void pause()
    {
        Platform.runLater(() -> {
            helloController.getPauseOrResume().setText("Resume");
        });
        paused = true;

    }
    public void resume()
    {
        Platform.runLater(() -> {
            helloController.getPauseOrResume().setText("Pause");
        });
        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }

        paused = false;
    }
    private void resumeGame() {
        // No action needed for resume
    }

    public void requestStop() {
        stopRequested = true;
    }

    private boolean isPauseRequested() {
        return paused;
    }

    private boolean isResumeRequested() {
        return !paused;
    }

    private void pauseGame() {
        synchronized (pauseLock) {
            try {
                pauseLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void drawWithFallingBlock()
    {
        helloController.getCenterTextArea().setText(level.toString(fallingBlock));
    }

    private void drawLevelOnly(){
        helloController.getCenterTextArea().setText(level.toString());
    }

    private void drawEndGame() throws InterruptedException {
        animateGameOver();
        Thread.sleep(1000);
        helloController.getCenterTextArea().setStyle("-fx-text-alignment: center;");
        helloController.getCenterTextArea().setText("GAME OVER");
    }
    public void animateGameOver()
    {
        for(int row = Level.HEIGHT-1; row >= 0; row--){
            for(int col = 0; col < Level.WIDTH; col++){
                level.setCellAt(row, col, "██");
            }
            drawLevelOnly();
            sleep(100L);
        }
        for(int row = 0; row < Level.HEIGHT; row++){
            for(int col = 0; col < Level.WIDTH; col++){
                level.setCellAt(row, col, "  ");
            }
            drawLevelOnly();
            sleep(100L);
        }
    }

    private void updateScore()
    {
        helloController.getScoreTextField().setText(String.valueOf(score));
    }
    private void updateNextBlock()
    {
        //Block block = new Block(BlockType.SQUARE);

        helloController.getNextBlockTextArea().setText(nextBlock.toString());
    }

    private boolean move()
    {
        if(canMoveDown()){
            fallingBlock.moveDown();
            return false;
        }   else {
            return true;
        }
    }

    private boolean canMoveDown() {
        List<MiniBlock> miniBlockList = fallingBlock.getMiniBlockList();
        for (MiniBlock miniBlock : miniBlockList) {
            int miniBlockRow = fallingBlock.getRow() + miniBlock.getRowOffset();
            int miniBlockCol = fallingBlock.getCol() + miniBlock.getColOffset();
            String cellBelow = level.getCellAt(miniBlockRow + 1, miniBlockCol);
            if (!cellBelow.equals("  ")) {
                return false;

            }
        }
        return true;
    }

    private void sleep(long amount)
    {
        try{
            Thread.sleep(amount);
        }   catch (InterruptedException e)  {
            System.out.println(e.getMessage());
        }
    }

    public void rotateRight() {
    }
    public void rotateLeft() {
    }
    public void moveRight()
    {
        if(!fallingBlock.isAtRight() && canMoveRight() && !drop){
            fallingBlock.moveBlockRight();
        }
    }
    public void moveLeft(){
        if(!fallingBlock.isAtLeft() && canMoveLeft() && !drop){
            fallingBlock.moveBlockLeft();
        }
    }

    private boolean canMoveLeft() {
        List<MiniBlock> miniBlockList = fallingBlock.getMiniBlockList();
        for (MiniBlock miniBlock : miniBlockList) {
            int miniBlockRow = fallingBlock.getRow() + miniBlock.getRowOffset();
            int miniBlockCol = fallingBlock.getCol() + miniBlock.getColOffset();
            String cellToTheLeft = level.getCellAt(miniBlockRow, miniBlockCol-1);
            if (!cellToTheLeft.equals("  ")) {
                return false;

            }
        }
        return true;
    }
    private boolean canMoveRight() {
        List<MiniBlock> miniBlockList = fallingBlock.getMiniBlockList();
        for (MiniBlock miniBlock : miniBlockList) {
            int miniBlockRow = fallingBlock.getRow() + miniBlock.getRowOffset();
            int miniBlockCol = fallingBlock.getCol() + miniBlock.getColOffset();
            String cellToTheRight = level.getCellAt(miniBlockRow, miniBlockCol+1);
            if (!cellToTheRight.equals("  ")) {
                return false;

            }
        }
        return true;
    }

    public void dropBoxDown() {
        drop = true;
    }

    public void rotateFallingBlock()
    {
        //helloController.getCenterTextArea().setText("Rotated :)");
        if(canRotate() && !paused){
            fallingBlock.rotateBlock();
        }
    }
    private boolean canRotate()
    {
        Block copyOfFallingBlock = fallingBlock.copy();
        copyOfFallingBlock.rotateBlock();

        List<MiniBlock> miniBlockList = copyOfFallingBlock.getMiniBlockList();
        for (MiniBlock miniBlock : miniBlockList) {
            int miniBlockRow = copyOfFallingBlock.getRow() + miniBlock.getRowOffset();
            int miniBlockCol = copyOfFallingBlock.getCol() + miniBlock.getColOffset();
            String cell = level.getCellAt(miniBlockRow, miniBlockCol);
            if ("██".equals(cell)) {
                return false;
            }
        }

        return true;
    }

    private boolean isGameOver()
    {
        List<MiniBlock> miniBlockList = fallingBlock.getMiniBlockList();
        for (MiniBlock miniBlock : miniBlockList) {
            int miniBlockRow = fallingBlock.getRow() + miniBlock.getRowOffset();
            int miniBlockCol = fallingBlock.getCol() + miniBlock.getColOffset();
            String cell = level.getCellAt(miniBlockRow, miniBlockCol);
            if ("██".equals(cell)) {
                return true;
            }
        }

        return false;
    }

    /*@Override
    public void run() {
        System.out.println();
        Platform.runLater(() -> {
            helloController.setCenterTextArea("Hello Finally");
        });

        Platform.runLater(() -> {
            helloController.getCenterTextArea().setEditable(false);
            helloController.getCenterTextArea().setDisable(true);

            for (int i = 0; i < 10; i++){
                draw();
                sleep();
                move();
            }

            });
    }*/
}

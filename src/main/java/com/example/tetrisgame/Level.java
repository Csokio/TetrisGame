package com.example.tetrisgame;

import com.example.tetrisgame.blocks.Block;
import com.example.tetrisgame.blocks.MiniBlock;

import java.util.List;

public class Level {

    public static final int HEIGHT = 15;
    public static final int WIDTH = 12;
    private final String[][] level = new String[HEIGHT][WIDTH];

    public Level()
    {
        for(int row = 0; row < HEIGHT; row++){
            for(int col = 0; col < WIDTH; col++){
                level[row][col] = "  ";
            }
        }
        level[14][0] = "██";
        level[14][1] = "██";
        level[14][2] = "██";
        level[14][3] = "██";
        level[13][2] = "██";
        level[13][3] = "██";
        level[12][3] = "██";


    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                stringBuilder.append(level[row][col]);
            }
            stringBuilder.append('\n');
        }
        return  stringBuilder.toString();
    }


    public String toString(Block block) {
        String[][] drawBuffer = copyArray(level);
        block.draw(drawBuffer);
        StringBuilder stringBuilder = new StringBuilder();
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                stringBuilder.append(drawBuffer[row][col]);
            }
            stringBuilder.append('\n');
        }
        return  stringBuilder.toString();
    }

    private String[][] copyArray(String[][] arrayToCopy)
    {
        String[][] copy = new String[arrayToCopy.length][arrayToCopy[0].length];
        for(int row = 0; row < HEIGHT; row++){
            for(int col = 0; col < WIDTH; col++){
                copy[row][col] = arrayToCopy[row][col];
            }
        }
        return copy;
    }

    public String getCellAt(int row, int col) {
        if(isCoordinatesWithinBounds(row, col)){
            return level[row][col];
        }   else   {
            return "██";
        }
    }

    public void setCellAt(int row, int col, String value){
        level[row][col] = value;
    }

    private boolean isCoordinatesWithinBounds(int row, int col){
        return row >= 0 && row < Level.HEIGHT && col >= 0 && col < Level.WIDTH;
    }

    public void mergeWith(Block block)  {
        List<MiniBlock> miniBlockList = block.getMiniBlockList();
        for(MiniBlock miniBlock: miniBlockList){
        int miniBlockRow = block.getRow() + miniBlock.getRowOffset();
        int miniBlockColumn = block.getCol() + miniBlock.getColOffset();
        level[miniBlockRow][miniBlockColumn] = "██";
        }
    }

    public int removeCompletedRows()
    {
        int rowCounter = 0;
        for(int row = 0; row < HEIGHT; row++){
            int columnCounter = 0;
            for(int col = 0; col < WIDTH; col++){
                if("██".equals(level[row][col])){
                       columnCounter++;
                }
            }
            if(columnCounter >= WIDTH){
                removeRow(row);
                rowCounter++;
            }
        }
        return rowCounter;
    }

    private void removeRow(int rowToRemove)
    {
        for(int row = rowToRemove; row >= 0; row--) {
            for (int col = 0; col < WIDTH; col++) {
                level[row][col] = row <= 0 ? "  " : level[row-1][col];
            }
        }
    }
}

package com.example.tetrisgame.blocks;

import com.example.tetrisgame.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class Block {


    protected int col = Level.WIDTH / 2;
    protected int row = 2;

    private static final Random random = new Random();


    protected List<MiniBlock> miniBlockList = new ArrayList<>();

    public List<MiniBlock> getMiniBlockList() {
        return miniBlockList;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }


    /*public Block(BlockType blockType)
    {
        switch(blockType){
            case LONG:
                miniBlockList.add(new MiniBlock(0,0));
                miniBlockList.add(new MiniBlock(0,-1));
                miniBlockList.add(new MiniBlock(0,1));
                miniBlockList.add(new MiniBlock(0,2));
                break;
            case SQUARE:
                miniBlockList.add(new MiniBlock(0,0));
                miniBlockList.add(new MiniBlock(1,0));
                miniBlockList.add(new MiniBlock(0,1));
                miniBlockList.add(new MiniBlock(1,1));
                break;
            case Z:
                miniBlockList.add(new MiniBlock(0,0));
                miniBlockList.add(new MiniBlock(0,-1));
                miniBlockList.add(new MiniBlock(1,0));
                miniBlockList.add(new MiniBlock(1,1));
                break;
            case T:
                miniBlockList.add(new MiniBlock(0,0));
                miniBlockList.add(new MiniBlock(-1,0));
                miniBlockList.add(new MiniBlock(0,-1));
                miniBlockList.add(new MiniBlock(0,1));
                break;

        }
    }*/

    public static Block randomBlockType()
    {
        Block randomBlock = BlockType.getRandomBlockType().createBlock();
        int numberOfRotations = random.nextInt(4);
        for(int i = 0; i < numberOfRotations; i++){
            randomBlock.rotateBlock();
        }
        return randomBlock;
    }

    public Block(){

    }
    public abstract Block copy();

    protected void copy(Block original, Block copied)
    {
        copied.row = original.row;
        copied.col = original.col;
        for(MiniBlock miniBlock: original.miniBlockList){
            copied.miniBlockList.add(new MiniBlock(miniBlock));
        }
    }


    public void draw(String[][] buffer)
    {
        for(MiniBlock miniBlock: miniBlockList){
            int actualRow = row + miniBlock.getRowOffset();
            int actualCol = col + miniBlock.getColOffset();
            if(isCoordinatesWithinBounds(actualRow,actualCol)){
                buffer[actualRow][actualCol] = "██";
            }
        }
    }

    private boolean isCoordinatesWithinBounds(int actualRow, int actualCol)
    {
        return actualCol >= 0 && actualCol < Level.WIDTH
                && actualRow >= 0 && actualRow < Level.HEIGHT;
    }

    public void moveDown()
    {
        row++;
    }

    public boolean isAtBottom()
    {
        List<MiniBlock> miniBlocksCopy = new ArrayList<>(miniBlockList);
        Collections.sort(miniBlocksCopy, new MiniBlockRowComparator());
        MiniBlock bottomMiniBlock = miniBlocksCopy.get(miniBlocksCopy.size()-1);
        int actualRow = row + bottomMiniBlock.getRowOffset();
        return actualRow == Level.HEIGHT-1;
    }
    public boolean isAtRight()
    {
        List<MiniBlock> miniBlocksCopy = new ArrayList<>(miniBlockList);
        Collections.sort(miniBlocksCopy, new MiniBlockColumnComparator());
        MiniBlock bottomMiniBlock = miniBlocksCopy.get(miniBlocksCopy.size()-1);
        int actualCol = col + bottomMiniBlock.getColOffset();
        return actualCol == Level.WIDTH-1;
    }
    public boolean isAtLeft()
    {
        List<MiniBlock> miniBlocksCopy = new ArrayList<>(miniBlockList);
        Collections.sort(miniBlocksCopy, new MiniBlockColumnComparator());
        MiniBlock bottomMiniBlock = miniBlocksCopy.get(0);
        int actualCol = col + bottomMiniBlock.getColOffset();
        return actualCol == 0;
    }

    public void moveBlockRight()
    {
        col++;
    }

    public void moveBlockLeft()
    {
        col--;
    }

    @Override
    public String toString()
    {
        String[][] drawBuffer = new String[5][5];
        for(int row = 0; row < 5; row++){
            for(int col = 0; col < 5; col++){
                drawBuffer[row][col] = "  ";
            }
        }

        int rowAnchor = 2;
        int colAnchor = 2;
        for(MiniBlock miniBlock: miniBlockList){
            int miniBlockRow = rowAnchor + miniBlock.getRowOffset();
            int miniBlockCol = colAnchor + miniBlock.getColOffset();
            drawBuffer[miniBlockRow][miniBlockCol] = "██";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(int row = 0; row < 5; row++){
            for(int col = 0; col < 5; col++){
                stringBuilder.append(drawBuffer[row][col]);
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    public void rotateBlock()
    {
        for(MiniBlock miniBlock: miniBlockList){
            miniBlock.rotateClockwise();
        }

    }

    protected void rotateBlockCounterClockwise()
    {
        for(MiniBlock miniBlock: miniBlockList){
            miniBlock.rotateCounterClockwise();
        }

    }
}

package com.example.tetrisgame.blocks;

public class MiniBlock {


    private int rowOffset;
    private int colOffset;


    public MiniBlock(int rowOffset, int colOffset) {
        this.rowOffset = rowOffset;
        this.colOffset = colOffset;
    }

    public MiniBlock(MiniBlock other){
        this.rowOffset = other.rowOffset;
        this.colOffset = other.colOffset;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public int getColOffset() {
        return colOffset;
    }

    public void rotateClockwise()
    {
        int oldRowOffset = rowOffset;
        int oldColumnOffset = colOffset;
        int newRowOffset = oldColumnOffset;
        int newColOffset = -oldRowOffset;
        rowOffset = newRowOffset;
        colOffset = newColOffset;
    }

    public void rotateCounterClockwise() {
        int oldRowOffset = rowOffset;
        int oldColumnOffset = colOffset;
        int newRowOffset = -oldColumnOffset;
        int newColOffset = oldRowOffset;
        rowOffset = newRowOffset;
        colOffset = newColOffset;
    }
}

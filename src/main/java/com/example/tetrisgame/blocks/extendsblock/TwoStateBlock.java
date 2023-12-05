package com.example.tetrisgame.blocks.extendsblock;

import com.example.tetrisgame.blocks.Block;

public abstract class TwoStateBlock extends Block {

    private boolean rotateClockwise;

    @Override
    public void rotateBlock() {
        if(rotateClockwise){
            super.rotateBlock();
        }   else {
            rotateBlockCounterClockwise();
        }
        rotateClockwise = !rotateClockwise;
    }
}

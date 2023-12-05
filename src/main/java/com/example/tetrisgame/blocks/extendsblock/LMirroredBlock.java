package com.example.tetrisgame.blocks.extendsblock;

import com.example.tetrisgame.blocks.Block;
import com.example.tetrisgame.blocks.MiniBlock;

public class LMirroredBlock extends TwoStateBlock {

    public LMirroredBlock()
    {
        miniBlockList.add(new MiniBlock(0,0));
        miniBlockList.add(new MiniBlock(-2,0));
        miniBlockList.add(new MiniBlock(-1,0));
        miniBlockList.add(new MiniBlock(0,-1));
    }

    @Override
    public Block copy() {
        Block copy = new LMirroredBlock();
        copy(this, copy);
        return copy;
    }
}

package com.example.tetrisgame.blocks.extendsblock;

import com.example.tetrisgame.blocks.Block;
import com.example.tetrisgame.blocks.MiniBlock;

public class ZMirroredBlock extends TwoStateBlock {

    public ZMirroredBlock()
    {
        miniBlockList.add(new MiniBlock(0,0));
        miniBlockList.add(new MiniBlock(0,1));
        miniBlockList.add(new MiniBlock(1,0));
        miniBlockList.add(new MiniBlock(1,-1));
    }

    @Override
    public Block copy() {
        Block copy = new ZMirroredBlock();
        copy(this, copy);
        return copy;
    }
}

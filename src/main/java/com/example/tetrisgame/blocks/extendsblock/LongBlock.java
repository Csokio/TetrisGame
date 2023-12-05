package com.example.tetrisgame.blocks.extendsblock;

import com.example.tetrisgame.blocks.Block;
import com.example.tetrisgame.blocks.MiniBlock;

public class LongBlock extends TwoStateBlock {

    public LongBlock()
    {
        miniBlockList.add(new MiniBlock(0,0));
        miniBlockList.add(new MiniBlock(0,-1));
        miniBlockList.add(new MiniBlock(0,1));
        miniBlockList.add(new MiniBlock(0,2));
    }

    @Override
    public Block copy() {
        Block copy = new LongBlock();
        copy(this, copy);
        return copy;
    }

}

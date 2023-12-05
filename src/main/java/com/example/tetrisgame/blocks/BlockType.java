package com.example.tetrisgame.blocks;

import com.example.tetrisgame.blocks.extendsblock.*;

import java.util.Random;

public enum BlockType {

    LONG{
        @Override
        public Block createBlock() {
            return new LongBlock();
        }
    },
    SQUARE{
        @Override
        public Block createBlock() {
        return new SquareBlock();
        }
    },
    Z {
        @Override
        public Block createBlock() {
            return new ZBlock();
        }
    },
    L {
        @Override
        public Block createBlock() {
            return new LBlock();
        }
    },
    T {
        @Override
        public Block createBlock() {
            return new TBlock();
        }
    },
    L_MIRRORED {
        @Override
        public Block createBlock() {
            return new LMirroredBlock();
        }
    },
    Z_MIRRORED {
        @Override
        public Block createBlock() {
            return new ZMirroredBlock();
        }
    }
    ;


    private static final Random random = new Random();
    private static final BlockType[] blockTypes = values();

    public static BlockType getRandomBlockType(){
        return blockTypes[random.nextInt(values().length)];
    }

    public abstract Block createBlock();
}

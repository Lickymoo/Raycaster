package com.mitchellg.raycaster.engine.model.location;

public class Matrix1x4f extends BaseMatrix{

    public Matrix1x4f(){
        super();
    }

    public Matrix1x4f(float... value){
        super(value);
    }

    public Matrix1x4f(float[][] value){
        super(value);
    }

    @Override
    public int getSizeX() {
        return 1;
    }

    @Override
    public int getSizeY() {
        return 4;
    }
}

package com.mitchellg.raycaster.engine.model.location;

public class Matrix4x4f extends BaseMatrix{

    public Matrix4x4f(){
        super();
    }

    public Matrix4x4f(float... value){
        super(value);
    }

    public Matrix4x4f(float[][] value){
        super(value);
    }

    @Override
    public int getSizeX() {
        return 4;
    }

    @Override
    public int getSizeY() {
        return 4;
    }
}

package com.mitchellg.raycaster.engine.model.location;

public abstract class BaseMatrix {
    protected final float values[][];

    public abstract int getSizeX();
    public abstract int getSizeY();

    public BaseMatrix(){
        values = new float[getSizeY()][getSizeX()];

        //Identity matrix
        if(getSizeX() == getSizeY()){
            for(int y = 0; y < getSizeX(); y++) {
                for (int x = 0; x < getSizeX(); x++) {
                    if(y == x)
                        values[y][x] = 1f;
                }
            }
        }
    }

    public BaseMatrix(float... value){
        values = new float[getSizeY()][getSizeX()];

        if(value.length == 1){
            for(int y = 0; y < getSizeY(); y++){
                for(int x = 0; x < getSizeX(); x++){
                    this.values[y][x] = value[0];
                }
            }
        }else{
            for(int i = 0; i < value.length; i++){
                int x = i % getSizeX();
                int y = i / getSizeX();

                this.values[y][x] = value[i];
            }
        }
    }

    public BaseMatrix(float[][] value){
        values = new float[getSizeY()][getSizeX()];

        for(int y = 0; y < getSizeY(); y++){
            for(int x = 0; x < getSizeX(); x++){
                if(value.length < y || value[y].length < x)
                    continue;

                this.values[y][x] = value[y][x];
            }
        }
    }

    public float getValue(int x, int y){
        return values[y][x];
    }

    public void setValues(float[][] value){
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                this.values[y][x] = value[y][x];
            }
        }
    }

    public void setValue(int x, int y, float value){
        this.values[y][x] = value;
    }

    public void setValue(int y, float... value){
        for(int i = 0; i < value.length; i++){
            this.values[y][i] = value[i];
        }
    }

    public BaseMatrix add(BaseMatrix value){
        return add(value, this);
    }

    public BaseMatrix add(BaseMatrix value, BaseMatrix out){
        if(this.getSizeX() != value.getSizeX() || this.getSizeY() != value.getSizeY()){
            throw new IllegalArgumentException("Matrices must be identical size");
        }

        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                out.values[y][x] = this.values[y][x] + value.values[x][y];
            }
        }
        return out;
    }

    public BaseMatrix min(BaseMatrix value){
        return min(value, this);
    }

    public BaseMatrix min(BaseMatrix value, BaseMatrix out){
        if(this.getSizeX() != value.getSizeX() || this.getSizeY() != value.getSizeY()){
            throw new IllegalArgumentException("Matrices must be identical size");
        }

        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                out.values[y][x] = this.values[y][x] - value.values[x][y];
            }
        }
        return out;
    }

    public BaseMatrix mul(BaseMatrix value){
        return mul(value, this);
    }

    public BaseMatrix mul(BaseMatrix value, BaseMatrix out){
        if(this.getSizeY() != value.getSizeX()){
            throw new IllegalArgumentException("Matrix 2s rows must equal Matrix 1's columns");
        }

        float[][] valMatrix = new float[getSizeY()][getSizeX()];

        //For each spot in the new matrix
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                float output = 0;
                for(int inc = 0; inc < getSizeY(); inc++){
                    output += value.values[y][inc] * this.values[inc][x];
                }

                valMatrix[y][x] = output;
            }
        }

        out.setValues(valMatrix);
        return out;
    }


    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName());
        builder.append("[\n");
        for(int y = 0; y < getSizeY(); y++){
            for(int x = 0; x < getSizeX(); x++){
                builder.append(this.values[y][x]);
                builder.append(",");
            }
            builder.append("\n");
        }
        builder.append("]");

        return builder.toString();
    }
}

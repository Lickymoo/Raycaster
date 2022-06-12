package com.mitchellg.raycaster.test.objects;

import com.mitchellg.raycaster.engine.model.game.GameObject;
import com.mitchellg.raycaster.engine.model.game.ModelLoader;

public class CubeObj extends GameObject {
    public CubeObj(){

        float[] vertices = new float[]{
                -1, -1, -1,
                1, -1, -1,
                1, 1, -1,
                -1, 1, -1,
                -1, -1, 1,
                1, -1, 1,
                1, 1, 1,
                -1, 1, 1
        };
        float[] normals = new float[]{
                0, 0, 1,
                1, 0, 0,
                0, 0, -1,
                -1, 0, 0,
                0, 1, 0,
                0, -1, 0
        };

        int[] indices = new int[]{
                0, 1, 3, 3, 1, 2,
                1, 5, 2, 2, 5, 6,
                5, 4, 6, 6, 4, 7,
                4, 0, 7, 7, 0, 3,
                3, 2, 7, 7, 2, 6,
                4, 5, 0, 0, 5, 1};

        this.setSolids(ModelLoader.getGeometry(vertices, indices, normals));
    }

    @Override
    public void onUpdate() {


    }
}

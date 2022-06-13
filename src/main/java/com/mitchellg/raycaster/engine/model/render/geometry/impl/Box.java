package com.mitchellg.raycaster.engine.model.render.geometry.impl;

import com.mitchellg.raycaster.engine.model.game.GameObject;
import com.mitchellg.raycaster.engine.model.location.Bounds;
import com.mitchellg.raycaster.engine.model.location.Vector3f;
import com.mitchellg.raycaster.engine.model.render.geometry.Geometry;
import com.mitchellg.raycaster.engine.model.render.math.Ray;
import lombok.Getter;

import java.text.DecimalFormat;
import java.text.NumberFormat;

@Getter
public class Box extends Geometry {

    protected Bounds bounds;

    public Box(GameObject parent, Bounds bounds) {
        super(parent);
        this.bounds = bounds;
    }

    @Override
    public Vector3f calculateIntersection(Ray ray, Vector3f offset, Vector3f rotation, Vector3f scale) {
        if(isIgnoreRender()) return null;

        float t1, t2, tnear = Float.NEGATIVE_INFINITY, tfar = Float.POSITIVE_INFINITY, temp;
        boolean intersectFlag = true;
        float[] rayDirection = ray.getDirection().toArray();
        float[] rayOrigin = ray.getOrigin().toArray();

        float[] b1 = bounds.getMinPoint().rotate(rotation).multiply(scale).toArray();
        float[] b2 = bounds.getMaxPoint().rotate(rotation).multiply(scale).toArray();

        for(int i = 0; i < 3; i++){
            if(rayDirection[i] == 0){
                if(rayOrigin[i] < b1[i] || rayOrigin[i] > b2[i])
                    intersectFlag = false;
            }
            else{
                t1 = (b1[i] - rayOrigin[i])/rayDirection[i];
                t2 = (b2[i] - rayOrigin[i])/rayDirection[i];
                if(t1 > t2){
                    temp = t1;
                    t1 = t2;
                    t2 = temp;
                }
                if(t1 > tnear)
                    tnear = t1;
                if(t2 < tfar)
                    tfar = t2;
                if(tnear > tfar)
                    intersectFlag = false;
                if(tfar < 0)
                    intersectFlag = false;
            }
        }
        if(intersectFlag)
            return ray.getOrigin().add(ray.getDirection().multiply(tnear));
        else
            return null;
    }

    @Override
    public Vector3f getNormalAt(Vector3f point, Vector3f offset, Vector3f rotation) {


        return new Vector3f(0, 0, 0);/*
        float[] direction = point.subtract(offset).toArray();
        float biggestValue = Float.NaN;

        for (int i = 0; i<3; i++) {
            if (Float.isNaN(biggestValue) || biggestValue < Math.abs(direction[i])) {
                biggestValue = Math.abs(direction[i]);
            }
        }

        if (biggestValue == 0) {
            return new Vector3f(0, 0, 0);
        } else {
            for (int i = 0; i<3; i++) {
                if (Math.abs(direction[i]) == biggestValue) {
                    float[] normal = new float[] {0,0,0};
                    normal[i] = direction[i] > 0 ? 1 : -1;

                    return new Vector3f(normal[0], normal[1], normal[2]);
                }
            }
        }

        return new Vector3f(0, 0, 0);*/
    }
}

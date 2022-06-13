package com.mitchellg.raycaster.engine.model.location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//AABB
public class Bounds {
    private Vector3f minPoint;
    private Vector3f maxPoint;

    public Bounds(){
        this.minPoint = new Vector3f();
        this.maxPoint = new Vector3f();
    }

    public Bounds(Vector3f... points){
        this.minPoint = new Vector3f();
        this.maxPoint = new Vector3f();

        encapsulate(points);
    }

    public boolean contains(Vector3f point){
        float pX = point.getX();
        float pY = point.getY();
        float pZ = point.getZ();

        float miX = minPoint.getX();
        float miY = minPoint.getX();
        float miZ = minPoint.getX();

        float maX = maxPoint.getX();
        float maY = maxPoint.getX();
        float maZ = maxPoint.getX();

        //check if point is less than min
        if(pX < miX || pY < miY || pZ < miZ)
            return false;

        //check if point is more than max
        if(pX > maX || pY > maY || pZ > maZ)
            return false;

        return true;
    }

    //Enlargen bounds to encapsulate provided parameter
    public void encapsulate(Bounds bounds){
        encapsulate(bounds.maxPoint, bounds.minPoint);
    }

    public void encapsulate(Vector3f... points){
        for(Vector3f vec : points){
            encapsulate(vec);
        }
    }


    public void encapsulate(Vector3f point){
        float pX = point.getX();
        float pY = point.getY();
        float pZ = point.getZ();

        float miX = minPoint.getX();
        float miY = minPoint.getX();
        float miZ = minPoint.getX();

        float maX = maxPoint.getX();
        float maY = maxPoint.getX();
        float maZ = maxPoint.getX();


        minPoint = new Vector3f(Math.min(pX, miX), Math.min(pY, miY), Math.min(pZ, miZ));
        maxPoint = new Vector3f(Math.max(pX, maX), Math.max(pY, maY), Math.max(pZ, maZ));
    }
}

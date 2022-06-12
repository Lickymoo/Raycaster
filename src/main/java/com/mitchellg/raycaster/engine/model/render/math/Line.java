package com.mitchellg.raycaster.engine.model.render.math;

import com.mitchellg.raycaster.engine.model.location.Vector3f;

public class Line {
    public Vector3f pointA;
    public Vector3f pointB;

    public  Line(Vector3f pointA, Vector3f pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public Ray asRay() {
        return new Ray(pointA, pointB.subtract(pointA).normalize());
    }
}

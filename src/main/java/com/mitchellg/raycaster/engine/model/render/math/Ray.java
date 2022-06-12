package com.mitchellg.raycaster.engine.model.render.math;

import com.mitchellg.raycaster.engine.model.location.Vector3f;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Ray {
    private Vector3f origin;
    private Vector3f direction;
    boolean cullFace = true;

    @Setter
    private int vertsTested = 0;

    public  Ray(Vector3f origin, Vector3f direction) {
        this.origin = origin;

        if (direction.length() != 1) {
            direction = direction.normalize();
        }
        this.direction = direction;
    }

    public Line asLine(float length) {
        return new Line(origin, origin.add(direction.multiply(length)));
    }

    public Vector3f getOrigin() {
        return origin;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void incVertsTested(){
        vertsTested++;
    }
}

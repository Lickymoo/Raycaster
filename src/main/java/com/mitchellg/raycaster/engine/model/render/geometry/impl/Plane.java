package com.mitchellg.raycaster.engine.model.render.geometry.impl;

import com.mitchellg.raycaster.engine.model.game.GameObject;
import com.mitchellg.raycaster.engine.model.location.Vector3f;
import com.mitchellg.raycaster.engine.model.render.geometry.Geometry;
import com.mitchellg.raycaster.engine.model.render.math.Ray;

public class Plane extends Geometry {

    public Plane(GameObject parent) {
        super(parent);
    }

    @Override
    public Vector3f calculateIntersection(Ray ray, Vector3f offset, Vector3f rotation, Vector3f scale) {
        if(isIgnoreRender()) return null;
        float t = -(ray.getOrigin().getY()-offset.getY()) / ray.getDirection().getY();
        if (t > 0 && Float.isFinite(t))
        {
            return ray.getOrigin().add(ray.getDirection().multiply(t));
        }

        return null;
    }

    @Override
    public Vector3f getNormalAt(Vector3f point, Vector3f offset, Vector3f rotation) {
        return new Vector3f(0, 1, 0);
    }

}

package com.mitchellg.raycaster.engine.model.render.math;

import com.mitchellg.raycaster.engine.model.location.Vector3f;
import com.mitchellg.raycaster.engine.model.render.Geometry;
import lombok.Getter;

@Getter
public class RayHit {
    private Ray ray;
    private Geometry geometry;
    private Vector3f position;
    private Vector3f normal;

    public RayHit(Ray ray, Geometry geometry, Vector3f position) {
        this.ray = ray;
        this.geometry = geometry;
        this.position = position;
        this.normal = geometry.getNormalAt(position, geometry.getParent().getTransform().getPosition());
    }

}

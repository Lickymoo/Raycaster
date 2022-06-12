package com.mitchellg.raycaster.engine.model.render.geometry;

import com.mitchellg.raycaster.engine.model.game.GameObject;
import com.mitchellg.raycaster.engine.model.location.Vector3f;
import com.mitchellg.raycaster.engine.model.render.math.Ray;
import com.mitchellg.raycaster.engine.model.render.Geometry;


public class Sphere extends Geometry {
    private float radius;

    public Sphere(GameObject parent, float radius) {
        super(parent);
        this.radius = radius;
    }

    @Override
    public Vector3f calculateIntersection(Ray ray, Vector3f offset, Vector3f rotation, Vector3f scale) {
        if(isIgnoreRender()) return null;
        float r = radius * scale.getMinAttrib();

        float t = Vector3f.dot(offset.subtract(ray.getOrigin()), ray.getDirection());
        Vector3f p =  ray.getOrigin().add(ray.getDirection().multiply(t));

        float y = offset.subtract(p).length();
        if (y < r) {
            float x = (float) Math.sqrt(r*r - y*y);
            float t1 = t-x;
            if (t1 > 0) return ray.getOrigin().add(ray.getDirection().multiply(t1));
            else return null;
        } else {
            return null;
        }
    }

    @Override
    public Vector3f getNormalAt(Vector3f point, Vector3f offset, Vector3f rotation) {
        return point.subtract(offset).normalize();
    }
}

package com.mitchellg.raycaster.engine.model.render.geometry;

import com.mitchellg.raycaster.engine.model.game.GameObject;
import com.mitchellg.raycaster.engine.model.location.Matrix4x4f;
import com.mitchellg.raycaster.engine.model.location.Vector3f;
import com.mitchellg.raycaster.engine.model.render.math.Ray;
import com.mitchellg.raycaster.engine.model.render.Geometry;
import lombok.Getter;

@Getter
public class Vert extends Geometry {

    private static final double EPSILON = 0.0000001;
    private Vector3f n0;
    private Vector3f n1;
    private Vector3f n2;

    private Vector3f v0;
    private Vector3f v1;
    private Vector3f v2;

    public Vert(GameObject parent, Vector3f p0, Vector3f p1, Vector3f p2, Vector3f n0, Vector3f n1, Vector3f n2) {
        super(parent);
        this.v0 = p0;
        this.v1 = p1;
        this.v2 = p2;

        this.n0 = n0;
        this.n1 = n1;
        this.n2 = n2;
    }

    @Override
    public Vector3f calculateIntersection(Ray ray, Vector3f offset, Vector3f rotation, Vector3f scale) {
        if(isIgnoreRender()) return null;
        //Using Moller Trumbore algorithm
        Vector3f rayOrigin = ray.getOrigin();
        Vector3f rayVector = ray.getDirection();

        Vector3f vertex0 = v0.rotateXYZ(rotation.getX(), rotation.getY(), rotation.getZ()).multiply(scale).add(offset);
        Vector3f vertex1 = v1.rotateXYZ(rotation.getX(), rotation.getY(), rotation.getZ()).multiply(scale).add(offset);
        Vector3f vertex2 = v2.rotateXYZ(rotation.getX(), rotation.getY(), rotation.getZ()).multiply(scale).add(offset);

        Vector3f edge1;
        Vector3f edge2;
        Vector3f h ;
        Vector3f s;
        Vector3f q;

        float a, f, u, v;
        edge1 = vertex1.subtract(vertex0);
        edge2 = vertex2.subtract(vertex0);
        h = Vector3f.cross(rayVector, edge2);
        a = Vector3f.dot(edge1, h);
        if (a > -EPSILON && a < EPSILON) {
            return null;
        }
        f = 1.0f / a;
        s = rayOrigin.subtract(vertex0);
        u = f * (Vector3f.dot(s, h));
        if (u < 0.0 || u > 1.0) {
            return null;
        }
        q = Vector3f.cross(s, edge1);
        v = f * Vector3f.dot(rayVector, q);
        if (v < 0.0 || u + v > 1.0) {
            return null;
        }
        // At this stage we can compute t to find out where the intersection point is on the line.
        float t = f * Vector3f.dot(edge2, q);
        if (t > EPSILON) // ray intersection
        {
            return rayVector.multiply(t).add(rayOrigin);

        } else // This means that there is a line intersection but not a ray intersection.
        {
            return null;
        }
    }

    @Override
    public Vector3f getNormalAt(Vector3f point, Vector3f offset, Vector3f rotation) {
        Vector3f p0 = v0.rotate(rotation);
        Vector3f p1 = v1.rotate(rotation);
        Vector3f p2 = v2.rotate(rotation);

        Vector3f a = p1.subtract(p0);
        Vector3f b = p2.subtract(p0);
        Vector3f cross = Vector3f.cross(a, b);
        return cross.normalize();
    }
}

package com.mitchellg.raycaster.engine.model.render.geometry;

import com.mitchellg.raycaster.engine.model.game.GameObject;
import com.mitchellg.raycaster.engine.model.location.Vector3f;
import com.mitchellg.raycaster.engine.model.render.Color;
import com.mitchellg.raycaster.engine.model.render.math.Ray;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Geometry {
    public boolean ignoreRender = false;
    protected GameObject parent;

    public Geometry(GameObject parent){
        this.parent = parent;
    }

    public boolean rayIntersects(Ray ray, Vector3f offset, Vector3f rotation, Vector3f scale){
        return calculateIntersectionInvertIgnore(ray, offset, rotation, scale) != null;
    }

    private Vector3f calculateIntersectionInvertIgnore(Ray ray, Vector3f offset, Vector3f rotation, Vector3f scale){
        boolean stored = ignoreRender;
        ignoreRender = false;
        Vector3f val = calculateIntersection(ray, offset, rotation, scale);
        ignoreRender = stored;
        return val;
    }

    public abstract Vector3f calculateIntersection(Ray ray, Vector3f offset, Vector3f rotation, Vector3f scale);
    public abstract Vector3f getNormalAt(Vector3f point, Vector3f offset, Vector3f rotation);
}

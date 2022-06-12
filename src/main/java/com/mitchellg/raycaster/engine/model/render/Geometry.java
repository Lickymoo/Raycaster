package com.mitchellg.raycaster.engine.model.render;

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

    public abstract Vector3f calculateIntersection(Ray ray, Vector3f offset, Vector3f rotation, Vector3f scale);
    public abstract Vector3f getNormalAt(Vector3f point, Vector3f offset);
}

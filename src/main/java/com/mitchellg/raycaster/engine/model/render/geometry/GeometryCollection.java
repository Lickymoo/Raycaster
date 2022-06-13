package com.mitchellg.raycaster.engine.model.render.geometry;

import com.mitchellg.raycaster.engine.model.game.GameObject;
import com.mitchellg.raycaster.engine.model.location.Vector3f;
import com.mitchellg.raycaster.engine.model.render.math.Ray;
import com.mitchellg.raycaster.engine.model.render.math.RayHit;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/*
Handler for raycast occlusion
 */
@Getter
@Setter
public class GeometryCollection {
    public List<GameObject> geometries = new ArrayList<>();

    public RayHit raycast(Ray ray){
        return raycast(ray, false);
    }

    public RayHit raycast(Ray ray, boolean first) {
        RayHit closestHit = null;
        for(GameObject object : geometries){
            RayHit hitPos = object.raycast(ray, first);

            if (hitPos != null && (closestHit == null || Vector3f.distance(closestHit.getPosition(), ray.getOrigin()) > Vector3f.distance(hitPos.getPosition(), ray.getOrigin()))) {
                closestHit = hitPos;
            }
        }
        return closestHit;
    }
}

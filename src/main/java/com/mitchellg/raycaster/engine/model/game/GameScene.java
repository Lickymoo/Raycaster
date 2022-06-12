package com.mitchellg.raycaster.engine.model.game;

import com.mitchellg.raycaster.engine.model.location.Vector3f;
import com.mitchellg.raycaster.engine.model.render.Light;
import com.mitchellg.raycaster.engine.model.render.Skybox;
import com.mitchellg.raycaster.engine.model.render.math.Ray;
import com.mitchellg.raycaster.engine.model.render.math.RayHit;
import com.mitchellg.raycaster.engine.model.target.UpdatableTarget;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class GameScene implements UpdatableTarget {
    protected Game game;
    protected final List<GameObject> objectList = new ArrayList<>();
    protected Light light;
    protected Skybox skybox;

    public GameScene(Game game){
        this.game = game;
    }

    public RayHit raycast(Ray ray){
        return raycast(ray, false);
    }

    public RayHit raycast(Ray ray, boolean first) {
        RayHit closestHit = null;
        for(GameObject object : objectList){
            RayHit hitPos = object.raycast(ray, first);

            if (hitPos != null && (closestHit == null || Vector3f.distance(closestHit.getPosition(), ray.getOrigin()) > Vector3f.distance(hitPos.getPosition(), ray.getOrigin()))) {
                closestHit = hitPos;
            }
        }
        return closestHit;
    }

    public void addObjects(GameObject... objects){
        objectList.addAll(Arrays.asList(objects));
    }
}

package com.mitchellg.raycaster.engine.model.game;

import com.mitchellg.raycaster.engine.model.render.geometry.GeometryCollection;
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

    private GeometryCollection collection;

    public GameScene(Game game){
        this.game = game;
    }

    public RayHit raycast(Ray ray){
        return raycast(ray, false);
    }

    public RayHit raycast(Ray ray, boolean first) {
        return collection.raycast(ray, first);
    }

    /*
    Handler for Geometry occlusion
    -Oct tree
     */
    public void generateGeometryCollection(){
        collection = new GeometryCollection();
        collection.setGeometries(objectList);
    }

    public void addObjects(GameObject... objects){
        objectList.addAll(Arrays.asList(objects));
    }
}































package com.mitchellg.raycaster.engine.model.game;

import com.mitchellg.raycaster.engine.model.location.Bounds;
import com.mitchellg.raycaster.engine.model.location.Vector3f;
import com.mitchellg.raycaster.engine.model.render.Color;
import com.mitchellg.raycaster.engine.model.render.Material;
import com.mitchellg.raycaster.engine.model.render.geometry.impl.Box;
import com.mitchellg.raycaster.engine.model.render.geometry.impl.Vert;
import com.mitchellg.raycaster.engine.model.render.math.Ray;
import com.mitchellg.raycaster.engine.model.render.math.RayHit;
import com.mitchellg.raycaster.engine.model.render.geometry.Geometry;
import com.mitchellg.raycaster.engine.model.target.UpdatableTarget;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class GameObject implements UpdatableTarget {
    protected Transform transform;
    protected Material material;
    protected Vector3f scale = new Vector3f(1f);
    protected Geometry[] geometryList;

    protected Box boundingBox;
    protected boolean ignoreBoundingBox = false;

    public GameObject() {
        this.transform = new Transform();
        this.material = new Material(Color.WHITE, 0 ,0);
        this.transform.setPosition(new Vector3f(0f));
    }

    public void setSolids(Geometry... geometries){
        this.geometryList = geometries;
        for(Geometry geometry : geometries){
           geometry.setParent(this);
        }

        if(!ignoreBoundingBox)
        calculateBoundingBox();
    }

    public void calculateBoundingBox(){
        Bounds bounds = new Bounds();
        //generate encapsulating sphere
        for(Geometry geometry : getGeometryList()){
            if(geometry instanceof Vert vert){
                bounds.encapsulate(vert.getV0(), vert.getV1(), vert.getV2());
            }
        }
        boundingBox = new Box(this, bounds);
        //boundingBox.setIgnoreRender(true);
    }

    public RayHit raycast(Ray ray){
        return raycast(ray, false);
    }

    public RayHit raycast(Ray ray, boolean first) {
        //Calculate if ray hits object at all, if not ignore and dont bother iterating through all vertices
        if(first && !ignoreBoundingBox && boundingBox != null){
            if(!boundingBox.rayIntersects(ray, transform.getPosition(), transform.getRotation(), scale)){
                return null;
            }
        }

        RayHit closestHit = null;
        for (Geometry geometry : geometryList) {
            ray.incVertsTested();
            if (geometry == null)
                continue;

            Vector3f hitPos = geometry.calculateIntersection(ray, transform.getPosition(), transform.getRotation(), scale);
            if (hitPos != null && (closestHit == null || Vector3f.distance(closestHit.getPosition(), ray.getOrigin()) > Vector3f.distance(hitPos, ray.getOrigin()))) {
                closestHit = new RayHit(ray, geometry, hitPos);
            }
        }
        return closestHit;
    }

    public void setRotation(float x){
        this.transform.setRotation(new Vector3f(x, x, x));
    }

    public void setRotation(float x, float y, float z){
        this.transform.setRotation(new Vector3f(x, y, z));
    }

    public void setScale(float x, float y, float z){
        this.scale = new Vector3f(x, y, z);
    }

    public void setScale(float x){
        this.scale = new Vector3f(x, x, x);
    }

    public void setScale(Vector3f scale){
        this.scale = scale;
    }

    public void setPosition(float x, float y, float z){
        this.transform.setPosition(new Vector3f(x, y, z));
    }

    public void setPosition(Vector3f vec){
        this.getTransform().setPosition(vec);
    }

}

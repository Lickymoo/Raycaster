package com.mitchellg.raycaster.engine.model.game;

import com.mitchellg.raycaster.engine.model.location.Vector3f;
import com.mitchellg.raycaster.engine.model.render.Color;
import com.mitchellg.raycaster.engine.model.render.Material;
import com.mitchellg.raycaster.engine.model.render.geometry.Sphere;
import com.mitchellg.raycaster.engine.model.render.geometry.Vert;
import com.mitchellg.raycaster.engine.model.render.math.Ray;
import com.mitchellg.raycaster.engine.model.render.math.RayHit;
import com.mitchellg.raycaster.engine.model.render.Geometry;
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
    protected Sphere encapsulatingSphere;

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


        //generate encapsulating sphere
        float min = 0;
        float max = 0;
        for(Geometry geometry : geometries){
            if(geometry instanceof Vert vert){
                float maxV0, maxV1, maxV2;
                float minV0, minV1, minV2;

                maxV0 = vert.getV0().getMaxAttrib();
                maxV1 = vert.getV1().getMaxAttrib();
                maxV2 = vert.getV2().getMaxAttrib();

                minV0 = vert.getV0().getMinAttrib();
                minV1 = vert.getV1().getMinAttrib();
                minV2 = vert.getV2().getMinAttrib();
                min = Math.min(min, Math.min(minV0, Math.min(minV1, minV2)));
                max = Math.max(max, Math.max(maxV0, Math.max(maxV1, maxV2)));
            }
        }
        float diff = Math.abs((min - max));
        if(diff > 0){
            encapsulatingSphere = new Sphere(this, diff);
            encapsulatingSphere.setIgnoreRender(true);
        }
    }

    public RayHit raycast(Ray ray) {
        //Calculate if ray hits object at all, if not ignore and dont bother iterating through all vertices
        if(encapsulatingSphere != null){
            Vector3f hit = encapsulatingSphere.calculateIntersection(ray, transform.getPosition(), transform.getRotation(), scale);
            if(hit == null)
                return null;
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

    public void setPosition(float x, float y, float z){
        this.transform.setPosition(new Vector3f(x, y, z));
    }

    public void setPosition(Vector3f vec){
        this.getTransform().setPosition(vec);
    }

}

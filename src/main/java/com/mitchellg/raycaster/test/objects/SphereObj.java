package com.mitchellg.raycaster.test.objects;

import com.mitchellg.raycaster.engine.model.game.GameObject;
import com.mitchellg.raycaster.engine.model.render.geometry.impl.Sphere;

public class SphereObj extends GameObject {
    public SphereObj(float radius) {
        this.setIgnoreBoundingBox(true);
        this.setSolids(new Sphere(this, radius));
    }

    @Override
    public void onUpdate() {

    }
}

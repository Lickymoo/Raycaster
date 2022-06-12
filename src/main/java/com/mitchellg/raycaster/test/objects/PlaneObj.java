package com.mitchellg.raycaster.test.objects;

import com.mitchellg.raycaster.engine.model.game.GameObject;
import com.mitchellg.raycaster.engine.model.location.Vector3f;
import com.mitchellg.raycaster.engine.model.render.geometry.Plane;

public class PlaneObj extends GameObject {
    public PlaneObj(float height) {
        this.getTransform().setPosition(new Vector3f(0, height, 0));
        this.setSolids(new Plane(this));
    }

    @Override
    public void onUpdate() {

    }
}

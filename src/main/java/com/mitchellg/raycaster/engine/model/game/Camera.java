package com.mitchellg.raycaster.engine.model.game;

import com.mitchellg.raycaster.engine.model.location.Vector3f;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Camera {
    private Vector3f position;
    private Vector3f rotation;

    private float FOV;

    public Camera(float FOV){
        position = new Vector3f();
        rotation = new Vector3f();
        this.FOV = FOV;
    }

    public void setPosition(float x, float y, float z){
        position = new Vector3f(x, y, z);
    }

    public void setRotation(float x){
        this.rotation = new Vector3f(x, x, x);
    }

    public void setRotation(float x, float y, float z){
        this.rotation = new Vector3f(x, y, z);
    }

}

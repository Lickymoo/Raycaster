package com.mitchellg.raycaster.engine.model.game;

import com.mitchellg.raycaster.engine.model.location.Vector3f;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Camera {
    private Vector3f position;
    private float pitch;
    private float roll;
    private float yaw;

    private float FOV;

    public Camera(float FOV){
        position = new Vector3f();
        this.FOV = FOV;
    }

    public void setPosition(float x, float y, float z){
        position = new Vector3f(x, y, z);
    }
}

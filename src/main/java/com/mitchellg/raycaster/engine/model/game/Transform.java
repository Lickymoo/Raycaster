package com.mitchellg.raycaster.engine.model.game;

import com.mitchellg.raycaster.engine.model.location.Vector3f;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transform {
    private Vector3f position = new Vector3f();
    private Vector3f rotation = new Vector3f();
}

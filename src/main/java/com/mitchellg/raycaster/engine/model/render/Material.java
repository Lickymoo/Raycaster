package com.mitchellg.raycaster.engine.model.render;

import com.mitchellg.raycaster.engine.model.location.Vector3f;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Material {
    protected Color color;
    protected float reflectivity;
    protected float emission;

    public Color getTextureColor(Vector3f pos){
        return color;
    }


}

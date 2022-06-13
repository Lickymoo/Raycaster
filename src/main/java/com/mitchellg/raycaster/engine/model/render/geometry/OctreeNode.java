package com.mitchellg.raycaster.engine.model.render.geometry;

import com.mitchellg.raycaster.engine.model.location.Bounds;

public class OctreeNode {
    Bounds nodeBounds;
    float minSize;

    public OctreeNode(Bounds nodeBounds, float minSize){
        this.nodeBounds = nodeBounds;
        this.minSize = minSize;
    }
}

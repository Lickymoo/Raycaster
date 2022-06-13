package com.mitchellg.raycaster.engine.model.render.geometry;

import com.mitchellg.raycaster.engine.model.game.GameObject;
import com.mitchellg.raycaster.engine.model.location.Bounds;

import java.util.Collection;
public class Octree {
    public OctreeNode rootNode;

    //generate octtree with GameObjects
    public Octree(Collection<GameObject> gameObjects, float minSize){
        Bounds bounds = new Bounds();

        for(GameObject object : gameObjects){

        }
    }

}








































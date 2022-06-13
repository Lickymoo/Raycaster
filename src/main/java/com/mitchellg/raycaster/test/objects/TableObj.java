package com.mitchellg.raycaster.test.objects;

import com.mitchellg.raycaster.engine.model.game.GameObject;
import com.mitchellg.raycaster.engine.model.game.ModelLoader;

public class TableObj extends GameObject {

    public TableObj(){
        this.setSolids(
                ModelLoader.loadModel("E:\\models\\table.fbx", false)
        );
    }

    @Override
    public void onUpdate() {

    }
}

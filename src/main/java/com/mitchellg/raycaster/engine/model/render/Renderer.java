package com.mitchellg.raycaster.engine.model.render;

import com.mitchellg.raycaster.engine.model.game.Camera;
import com.mitchellg.raycaster.engine.model.game.Game;

import java.awt.*;

public abstract class Renderer {
    protected final Game game;
    protected final Camera camera;

    public Renderer(Game game, Camera camera){
        this.game = game;
        this.camera = camera;
    }

    public abstract void render(Graphics graphics);
}

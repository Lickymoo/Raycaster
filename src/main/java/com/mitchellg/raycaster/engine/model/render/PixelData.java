package com.mitchellg.raycaster.engine.model.render;

import lombok.Getter;

import java.awt.*;

@Getter
public class PixelData {
    private Color color;
    private float depth;
    private float emission;

    public PixelData(Color color, float depth, float emission) {
        this.color = color;
        this.depth = depth;
        this.emission = emission;
    }

    public void add(PixelData other) {
        this.color.addSelf(other.color);
        this.depth = (this.depth+other.depth)/2F;
        this.emission = this.emission+other.emission;
    }

    public void multiply(float brightness) {
        this.color = this.color.multiply(brightness);
    }
}

package com.mitchellg.raycaster.engine;


import lombok.Getter;

@Getter
public class GamePreferences {
    private int defaultWidth = 700;
    private int defaultHeight = 500;
    private int framesPerSecond = 1;
    private boolean displayFPS = false;
}

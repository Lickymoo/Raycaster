package com.mitchellg.raycaster.engine.model.game;

import com.mitchellg.raycaster.engine.model.render.Renderer;
import com.mitchellg.raycaster.engine.model.target.UpdatableTarget;
import lombok.Getter;

public class GameRunnable implements Runnable{

    public static final long ONE_NANO_SECOND = 1000000000;

    public boolean running = false;
    private final Renderer renderer;

    private final int MAX_UPDATES = 20;
    private int updates = 0;
    private long lastUpdateTime = 0;
    private long timePerFrame;
    private long timePerUpdate;

    @Getter
    private int lastFPS = 0;

    private final Game game;

    public GameRunnable(Game game){
        this.game = game;
        this.renderer = game.getRenderer();
        this.timePerFrame = ONE_NANO_SECOND / game.getGamePreferences().getFramesPerSecond();
        this.timePerUpdate = ONE_NANO_SECOND / MAX_UPDATES;
    }

    @Override
    public void run() {
        running = true;

        game.onLoad();
        tick();
        game.onExit();
    }

    private void tick(){
        while(game.isRunning()) {
            updateBuffer();
        }
    }

    int cycleFrames = 0;
    int effectiveFrames = 0;
    long lastFrame = 0;
    long lastUpdate = 0;
    private void updateBuffer(){
        //fps and update buffer
        long currentTime = System.nanoTime();
        cycleFrames++;

        //Ensure frames are evenly spaced between the second
        //If not all frames will fire first then no frames for remaining cycle
        boolean ignoreCycle = game.getGamePreferences().getFramesPerSecond() == -1;
        if(currentTime - lastFrame >= timePerFrame || lastFrame == 0 || ignoreCycle){
            //Ensure frame cap isnt gone over
            if(effectiveFrames < game.getGamePreferences().getFramesPerSecond() || ignoreCycle){
                game.getWindow().getPanel().repaint();
                lastFrame = System.nanoTime();

            }
            effectiveFrames++;
        }

        if(currentTime - lastUpdate >= timePerUpdate || lastUpdate == 0){
            if(updates < MAX_UPDATES){
                updates++;
                lastUpdate = System.nanoTime();
                update();
            }
        }


        if(currentTime - lastUpdateTime >= ONE_NANO_SECOND){
            if(game.getGamePreferences().isDisplayFPS())
                System.out.println("tick (Cycle Frames: " + cycleFrames + ", Effective Frames: " + effectiveFrames + ", Updates: " + updates);

            lastFPS = effectiveFrames;

            cycleFrames = 0;
            updates = 0;
            effectiveFrames = 0;

            lastUpdateTime = System.nanoTime();

        }

    }

    private void update(){
        if(game.getCurrentScene() == null) return;
        game.onUpdate();
        game.getCurrentScene().onUpdate();

        //send update to all GameObjects
        game.getCurrentScene().getObjectList().forEach(UpdatableTarget::onUpdate);
    }
}

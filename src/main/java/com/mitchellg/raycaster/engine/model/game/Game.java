package com.mitchellg.raycaster.engine.model.game;

import com.mitchellg.raycaster.engine.GamePreferences;
import com.mitchellg.raycaster.engine.GameWindow;
import com.mitchellg.raycaster.engine.model.render.RaycastPipeline;
import com.mitchellg.raycaster.engine.model.render.Renderer;
import com.mitchellg.raycaster.engine.model.target.UpdatableTarget;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Getter
@Setter
public abstract class Game implements UpdatableTarget {
    protected GameScene currentScene;
    protected GamePreferences gamePreferences;
    protected GameWindow window;
    protected Renderer renderer;
    protected GameRunnable gameRunnable;
    protected boolean running;

    protected final String windowName;

    public Game(String windowName){
        this.windowName = windowName;
        gamePreferences = new GamePreferences();
        onPreLoad();
        openWindow();

        running = true;
        renderer = new RaycastPipeline(this);
        gameRunnable = new GameRunnable(this);
        gameRunnable.run();
    }

    public void openWindow(){
        window = new GameWindow(this, windowName);
        window.setSize(gamePreferences.getDefaultWidth(), gamePreferences.getDefaultHeight());
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                window.dispose();
                close();
            }
        });;
    }

    public void close(){
        onExit();
        System.exit(0);
    }

    public int getWidth(){
        return window.getWidth();
    }

    public int getHeight(){
        return window.getHeight();
    }

    public abstract void onPreLoad();
    public abstract void onLoad();

    public abstract void onExit();
}

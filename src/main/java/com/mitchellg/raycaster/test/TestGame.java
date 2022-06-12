package com.mitchellg.raycaster.test;

import com.mitchellg.raycaster.engine.model.game.Game;
public class TestGame extends Game {

    public static void main(String[] args) {
        TestGame testGame = new TestGame("Ray Tracer");
    }

    public TestGame(String windowName){
        super(windowName);
    }

    @Override
    public void onPreLoad() {

    }

    @Override
    public void onLoad() {
        setCurrentScene(new TestScene(this));

    }

    @Override
    public void onExit() {
        System.out.println("exit");

    }

    @Override
    public void onUpdate() {

    }
}

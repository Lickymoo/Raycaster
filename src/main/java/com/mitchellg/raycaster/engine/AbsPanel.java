package com.mitchellg.raycaster.engine;

import com.mitchellg.raycaster.engine.model.game.Game;

import javax.swing.*;
import java.awt.*;

public class AbsPanel extends JPanel {
    private final Game game;
    public AbsPanel(Game game){
        this.game = game;
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(game.getRenderer() == null) return;
        game.getRenderer().render(g);
        g.dispose();
    }
}

package com.mitchellg.raycaster.engine;

import com.mitchellg.raycaster.engine.model.game.Game;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class GameWindow extends Frame {
    protected Game game;
    private AbsPanel panel;

    public GameWindow(Game game, String name){
        super(name);
        this.game = game;
        panel = new AbsPanel(game);
        add(panel);
    }


}

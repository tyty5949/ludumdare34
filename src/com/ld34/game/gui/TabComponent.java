package com.ld34.game.gui;

import com.ld34.game.Game;
import com.ld34.util.Button;

/**
 * Created on 12/11/2015.
 */
public abstract class TabComponent {

    public boolean active = false;
    public Button headerButton;
    public String name;

    public abstract void update(Game game);

    public abstract void render(Game game);
}

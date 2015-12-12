package com.ld34.util;

import com.ld34.Main;

/**
 * Created on 12/11/2015.
 */
public abstract class GameState {

    public boolean initialized = false;
    public String name = "gamestate";

    public abstract void init(Main main);

    public abstract void update(double delta, Main main);

    public abstract void render(double delta, Main main);
}

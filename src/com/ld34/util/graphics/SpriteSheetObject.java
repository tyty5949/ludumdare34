package com.ld34.util.graphics;

/**
 * Created on 12/12/2015.
 */
public class SpriteSheetObject {

    private float x, y, w, h;

    public SpriteSheetObject(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }
}

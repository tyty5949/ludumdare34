package com.ld34.util;

/**
 * Created on 12/11/2015.
 */
public class Button {

    public float x, y, w, h;

    public Button(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean contains(double x1, double y1) {
        return x < x1 && x + w > x1 && y < y1 && y + h > y1;
    }
}

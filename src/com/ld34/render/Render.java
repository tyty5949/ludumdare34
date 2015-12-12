package com.ld34.render;

import com.ld34.util.graphics.Glyph;
import com.ld34.util.graphics.Texture;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created on 12/11/2015.
 */
public class Render {

    public static void drawGlyph(float x, float y, Glyph g, Texture t, Color c, boolean alpha) {
        float w = g.width;
        float h = g.height;
        glColor4f((float) c.getRed() / 255f, (float) c.getGreen() / 255f, (float) c.getBlue() / 255f, 1f);
        if (alpha) {
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        }
        t.bind();
        glBegin(GL_QUADS);
        glTexCoord2f(g.x / t.getWidth(), (g.y + g.height) / t.getHeight());
        glVertex2f(x, y);
        glTexCoord2f((g.x + g.width) / t.getWidth(), (g.y + g.height) / t.getHeight());
        glVertex2f(x + w, y);
        glTexCoord2f((g.x + g.width) / t.getWidth(), g.y / t.getHeight());
        glVertex2f(x + w, y + h);
        glTexCoord2f(g.x / t.getWidth(), g.y / t.getHeight());
        glVertex2f(x, y + h);
        glEnd();
        if (alpha)
            glDisable(GL_BLEND);
    }
}

package com.ld34.render;

import com.ld34.util.graphics.Glyph;
import com.ld34.util.graphics.SpriteSheetObject;
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

    public static void drawFilledRect(float x, float y, float w, float h, Color c) {
        glDisable(GL_TEXTURE_2D);
        glColor4f((float) c.getRed() / 255f, (float) c.getGreen() / 255f, (float) c.getBlue() / 255f, (float) c.getAlpha() / 255f);

        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + w, y);
        glVertex2f(x + w, y + h);
        glVertex2f(x, y + h);
        glEnd();

        glEnable(GL_TEXTURE_2D);
    }

    public static void drawSpriteSheetObject(SpriteSheetObject s, float x, float y, float w, float h, Texture t) {
        glPushMatrix();
        glColor4f(1f, 1f, 1f, 1f);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        t.bind();
        glBegin(GL_QUADS);
        glTexCoord2f(s.getX() / t.getWidth(), s.getY() / t.getHeight());
        glVertex2f(x, y);
        glTexCoord2f((s.getX() + s.getWidth()) / t.getWidth(), s.getY() / t.getHeight());
        glVertex2f(x + w, y);
        glTexCoord2f((s.getX() + s.getWidth()) / t.getWidth(), (s.getY() + s.getHeight()) / t.getHeight());
        glVertex2f(x + w, y + h);
        glTexCoord2f(s.getX() / t.getWidth(), (s.getY() + s.getHeight()) / t.getHeight());
        glVertex2f(x, y + h);
        glEnd();

        glDisable(GL_BLEND);
        glPopMatrix();
    }

    public static void drawPixelLine(float startX, float startY, float endX, float endY, Color c) {
        glDisable(GL_TEXTURE_2D);
        glColor4f((float) c.getRed() / 255f, (float) c.getGreen() / 255f, (float) c.getBlue() / 255f, (float) c.getAlpha() / 255f);

        glBegin(GL_LINES);
        glVertex2f(startX, startY - 3);
        glVertex2f(endX, endY - 3);
        glVertex2f(startX, startY - 2);
        glVertex2f(endX, endY - 2);
        glVertex2f(startX, startY - 1);
        glVertex2f(endX, endY - 1);
        glVertex2f(startX, startY);
        glVertex2f(endX, endY);
        glEnd();

        glEnable(GL_TEXTURE_2D);
    }
}

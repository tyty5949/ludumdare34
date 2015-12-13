package com.ld34.util;

import com.ld34.game.data.Colors;
import com.ld34.game.data.Fonts;
import com.ld34.render.Render;

/**
 * Created on 12/13/2015.
 */
public class Textbox extends Button {
    private boolean mouseCooldown;

    private int maxChars;
    private String string;
    private boolean active;
    private boolean numbersOnly;

    public Textbox(float x, float y, float w, float h, int maxChars, String startString, boolean numbersOnly) {
        super(x, y, w, h);
        this.maxChars = maxChars;
        string = startString;
        active = false;
        this.numbersOnly = numbersOnly;
    }

    public void update() {
        if (Mouse.isButtonDown(0) && !mouseCooldown) {
            active = contains(Mouse.getX(), Mouse.getY());
        }

        if (active && Keyboard.getRecentKey() > -1) {
            if (Keyboard.isKeyTyped(Keyboard.KEY_BACKSPACE)) {
                if (string.length() > 0)
                    string = string.substring(0, string.length() - 1);
            }

            if (Keyboard.isKeyTyped(Keyboard.KEY_BACKSPACE) && string.length() > 0)
                string = string.substring(0, string.length() - 1);

            if (Keyboard.isKeyTyped(Keyboard.getRecentKey())) {
                char c = (char) Keyboard.getAscii(Keyboard.getRecentKey());
                if (!numbersOnly && ((c >= 'a' && c <= 'z') || (c == ' ')) && Fonts.chartHeader.getWidth(string) < w - 22)
                    string += c;
                else if (c >= '0' && c <= '9' && Fonts.chartHeader.getWidth(string) < w)
                    string += c;
            }

        }

        mouseCooldown = Mouse.isButtonDown(0);
    }

    public void render() {
        if (active)
            Render.drawFilledRect(x + (Fonts.chartHeader.getWidth(string)) + 6, y + 2, 1, h - 4, Colors.FONT_LIGHT);
        Fonts.chartHeader.drawText(string, (x + 4), (y + 2), Colors.FONT_LIGHT);
    }

    public String getText() {
        return string;
    }

    public void setText(String string) {
        this.string = string;
    }
}

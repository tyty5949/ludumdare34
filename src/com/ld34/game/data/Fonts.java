package com.ld34.game.data;

import com.ld34.util.graphics.TrueTypeFont;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created on 12/11/2015.
 */
public class Fonts {

    public static TrueTypeFont uiFont;

    public static void init() {
        try {
            uiFont = new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/kenpixel.ttf")).deriveFont(Font.PLAIN, 24));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}

package com.ld34.game.data;

import com.ld34.util.graphics.TrueTypeFont;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created on 12/11/2015.
 */
public class Fonts {

    public static TrueTypeFont debug;
    public static TrueTypeFont topBar;
    public static TrueTypeFont tabHeaderSmall;
    public static TrueTypeFont tabHeaderLarge;

    public static void init() {
        try {
            debug = new TrueTypeFont(new Font("Impact", Font.PLAIN, 24));
            topBar = new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/kenpixel_square.ttf")).deriveFont(Font.PLAIN, 34));
            tabHeaderSmall = new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/kenpixel_square.ttf")).deriveFont(Font.PLAIN, 22));
            tabHeaderLarge = new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/kenpixel_high_square.ttf")).deriveFont(Font.PLAIN, 46));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}

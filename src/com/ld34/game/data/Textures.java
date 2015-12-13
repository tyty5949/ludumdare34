package com.ld34.game.data;

import com.ld34.util.graphics.SpriteSheetObject;
import com.ld34.util.graphics.Texture;

import java.io.File;

/**
 * Created on 12/11/2015.
 */
public class Textures {

    public static Texture spriteSheet;

    public static SpriteSheetObject pauseButton;
    public static SpriteSheetObject playButton;
    public static SpriteSheetObject tabBackground;
    public static SpriteSheetObject[] tabHeaders;
    public static SpriteSheetObject[] baseShopButtons;
    public static SpriteSheetObject[] shopButtons;
    public static SpriteSheetObject graph;
    public static SpriteSheetObject chart;
    public static SpriteSheetObject scrollLine;
    public static SpriteSheetObject scrollDot;
    public static SpriteSheetObject lineBreak;
    public static SpriteSheetObject toggleBox;
    public static SpriteSheetObject toggleBoxActive;
    public static SpriteSheetObject textBox;
    public static SpriteSheetObject fillBar;
    public static SpriteSheetObject plusMinus;
    public static SpriteSheetObject[] buttons;

    public static void init() {
        spriteSheet = new Texture(new File("res/spritesheet.png"));
        pauseButton = new SpriteSheetObject(0, 0, 9, 9);
        playButton = new SpriteSheetObject(9, 0, 6, 9);
        tabBackground = new SpriteSheetObject(0, 72, 223, 184);
        tabHeaders = new SpriteSheetObject[4];
        tabHeaders[0] = new SpriteSheetObject(0, 54, 56, 18);
        tabHeaders[1] = new SpriteSheetObject(56, 54, 55, 18);
        tabHeaders[2] = new SpriteSheetObject(111, 54, 56, 18);
        tabHeaders[3] = new SpriteSheetObject(167, 54, 56, 18);
        baseShopButtons = new SpriteSheetObject[3];
        baseShopButtons[0] = new SpriteSheetObject(0, 256, 64, 64);
        baseShopButtons[1] = new SpriteSheetObject(64, 256, 64, 64);
        baseShopButtons[2] = new SpriteSheetObject(128, 256, 64, 64);
        shopButtons = new SpriteSheetObject[6];
        shopButtons[0] = new SpriteSheetObject(15, 0, 44, 33);
        shopButtons[1] = new SpriteSheetObject(15, 0, 44, 33);
        shopButtons[2] = new SpriteSheetObject(15, 0, 44, 33);
        shopButtons[3] = new SpriteSheetObject(15, 0, 44, 33);
        shopButtons[4] = new SpriteSheetObject(15, 0, 44, 33);
        shopButtons[5] = new SpriteSheetObject(15, 0, 44, 33);
        graph = new SpriteSheetObject(224, 0, 69, 69);
        chart = new SpriteSheetObject(224, 69, 203, 94);
        scrollLine = new SpriteSheetObject(224, 164, 5, 87);
        scrollDot = new SpriteSheetObject(229, 164, 7, 8);
        lineBreak = new SpriteSheetObject(0, 9, 215, 1);
        toggleBox = new SpriteSheetObject(0, 40, 40, 13);
        toggleBoxActive = new SpriteSheetObject(40, 40, 40, 13);
        textBox = new SpriteSheetObject(0, 28, 127, 12);
        fillBar = new SpriteSheetObject(0, 25, 57, 3);
        plusMinus = new SpriteSheetObject(0, 22, 8, 3);
        buttons = new SpriteSheetObject[3];
        buttons[0] = new SpriteSheetObject(0, 10, 28, 11);
        buttons[1] = new SpriteSheetObject(28, 10, 28, 11);
        buttons[2] = new SpriteSheetObject(56, 10, 28, 11);
    }
}

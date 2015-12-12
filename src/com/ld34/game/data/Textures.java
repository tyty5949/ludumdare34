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

    }
}

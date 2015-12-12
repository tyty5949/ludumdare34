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

    public static void init() {
        spriteSheet = new Texture(new File("res/spritesheet.png"));
        pauseButton = new SpriteSheetObject(0, 0, 9, 9);
        playButton = new SpriteSheetObject(9, 0, 6, 9);
    }
}

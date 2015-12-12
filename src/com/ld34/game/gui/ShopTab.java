package com.ld34.game.gui;

import com.ld34.game.data.Colors;
import com.ld34.game.data.Fonts;
import com.ld34.game.data.Textures;
import com.ld34.render.Render;
import com.ld34.util.Button;

/**
 * Created on 12/11/2015.
 */
public class ShopTab extends TabComponent {

    public ShopTab() {
        headerButton = new Button(479f, 69.2f, 159, 51.36f);
        name = "Shop";
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Render.drawSpriteSheetObject(Textures.tabHeaders[3], 479f, 69.2f, 159, 51.36f, Textures.spriteSheet);
        Fonts.tabHeaderLarge.drawText(name, 556.5f - ((float) Fonts.tabHeaderLarge.getWidth(name) / 2f), 72f, Colors.FONT_LIGHT);
    }
}

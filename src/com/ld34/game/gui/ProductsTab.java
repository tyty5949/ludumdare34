package com.ld34.game.gui;

import com.ld34.game.Game;
import com.ld34.game.data.Colors;
import com.ld34.game.data.Fonts;
import com.ld34.game.data.Textures;
import com.ld34.render.Render;
import com.ld34.util.Button;

/**
 * Created on 12/11/2015.
 */
public class ProductsTab extends TabComponent {

    public ProductsTab() {
        headerButton = new Button(161f, 69.2f, 159, 51.36f);
        name = "Products";
    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void render(Game game) {
        Render.drawSpriteSheetObject(Textures.tabHeaders[1], 161f, 69.2f, 159, 51.36f, Textures.spriteSheet);
        Fonts.tabHeaderLarge.drawText(name, 238.5f - ((float) Fonts.tabHeaderLarge.getWidth(name) / 2f), 72f, Colors.FONT_LIGHT);
    }
}

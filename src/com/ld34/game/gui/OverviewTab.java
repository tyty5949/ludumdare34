package com.ld34.game.gui;

import com.ld34.game.data.Colors;
import com.ld34.game.data.Fonts;
import com.ld34.game.data.Textures;
import com.ld34.render.Render;
import com.ld34.util.Button;

/**
 * Created on 12/11/2015.
 */
public class OverviewTab extends TabComponent {

    public OverviewTab() {
        headerButton = new Button(2f, 69.2f, 159, 51.36f);
        name = "Overview";
        active = true;
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Render.drawSpriteSheetObject(Textures.tabHeaders[0], 2f, 69.2f, 159, 51.36f, Textures.spriteSheet);
        Fonts.tabHeaderLarge.drawText(name, 81.5f - ((float) Fonts.tabHeaderLarge.getWidth(name) / 2f), 72f, Colors.FONT_LIGHT);
    }
}

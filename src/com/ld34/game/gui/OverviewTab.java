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
public class OverviewTab extends TabComponent {

    public OverviewTab() {
        headerButton = new Button(2f, 69.2f, 159, 51.36f);
        name = "Overview";
        active = true;
    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void render(Game game) {
        Render.drawSpriteSheetObject(Textures.tabHeaders[0], 2f, 69.2f, 159, 51.36f, Textures.spriteSheet);
        Fonts.tabHeaderLarge.drawText(name, 81.5f - ((float) Fonts.tabHeaderLarge.getWidth(name) / 2f), 72f, Colors.FONT_LIGHT);

        // Graphs
        Render.drawSpriteSheetObject(Textures.graph, 20, 152, 218, 218, Textures.spriteSheet);
        Fonts.tabHeaderSmall.drawText("profit", 91, 130, Colors.FONT_LIGHT);
        if (game.getLastDailyProfits().size() >= 17) {
            int count = 0;
            for (int i = game.getLastDailyProfits().size() - 17; i < game.getLastDailyProfits().size() - 1; i++) {
                Render.drawPixelLine(22.85f + (count * 12.8f), 367f - ((float) game.getLastDailyProfits().get(i) / 10.0f),
                        22.85f + ((count + 1) * 12.8f), 367f - ((float) game.getLastDailyProfits().get(i + 1) / 10.0f), Colors.FONT_LIGHT);
                count++;
            }
        } else {
            for (int i = 0; i < game.getLastDailyProfits().size() - 1; i++) {
                Render.drawPixelLine(22.85f + (i * 12.8f), 367f - ((float) game.getLastDailyProfits().get(i) / 10.0f),
                        22.85f + ((i + 1) * 12.8f), 367f - ((float) game.getLastDailyProfits().get(i + 1) / 10.0f), Colors.FONT_LIGHT);
            }
        }

        Render.drawSpriteSheetObject(Textures.graph, 20, 410, 218, 218, Textures.spriteSheet);
        Fonts.tabHeaderSmall.drawText("market share", 40, 388, Colors.FONT_LIGHT);
        if (game.getLastDailyMarketShares().size() >= 17) {
            int count = 0;
            for (int i = game.getLastDailyMarketShares().size() - 17; i < game.getLastDailyMarketShares().size() - 1; i++) {
                Render.drawPixelLine(22.85f + (count * 12.8f), 624f - (Float.parseFloat(game.getLastDailyMarketShares().get(i) + "") / 10.0f),
                        22.85f + ((count + 1) * 12.8f), 624f - (Float.parseFloat(game.getLastDailyMarketShares().get(i + 1) + "") / 10.0f), Colors.FONT_LIGHT);
                count++;
            }
        } else {
            for (int i = 0; i < game.getLastDailyMarketShares().size() - 1; i++) {
                Render.drawPixelLine(22.85f + (i * 12.8f), 624f - (Float.parseFloat(game.getLastDailyMarketShares().get(i) + "") / 10.0f),
                        22.85f + ((i + 1) * 12.8f), 624f - (Float.parseFloat(game.getLastDailyMarketShares().get(i + 1) + "") / 10.0f), Colors.FONT_LIGHT);
            }
        }
    }
}

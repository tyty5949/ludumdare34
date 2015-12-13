package com.ld34.game.gui;

import com.ld34.game.Game;
import com.ld34.game.data.Colors;
import com.ld34.game.data.Fonts;
import com.ld34.game.data.Textures;
import com.ld34.render.Render;
import com.ld34.util.Button;
import com.ld34.util.Mouse;
import com.ld34.util.Textbox;

/**
 * Created on 12/11/2015.
 */
public class ProductsTab extends TabComponent {

    private int scrollBallOffset;
    private int scrollOffset;

    private Textbox nameTextbox;
    private Button[] types;
    private int selectedType;

    public ProductsTab() {
        headerButton = new Button(161f, 69.2f, 159, 51.36f);
        name = "Products";
        scrollBallOffset = 0;
        scrollOffset = 0;
        selectedType = 0;

        nameTextbox = new Textbox(90f, 404f, 362f, 30f, 25, "Quadcopter", false);
        types = new Button[4];
        for (int i = 0; i < 4; i++)
            types[i] = new Button(20f + (114f * i), 440f, 114f, 37f);
    }

    @Override
    public void update(Game game) {
        nameTextbox.update();
        for (int i = 0; i < 4; i++) {
            if (Mouse.isButtonDown(0) && types[i].contains(Mouse.getX(), Mouse.getY())) {
                selectedType = i;
                String s = nameTextbox.getText();
                if (s.equals("") || s.equals("Quadcopter") || s.equals("Micro Quadcopter") || s.equals("Helicopter") || s.equals("Airplane")) {
                    if (selectedType == 0)
                        nameTextbox.setText("Quadcopter");
                    else if (selectedType == 1)
                        nameTextbox.setText("Micro Quadcopter");
                    else if (selectedType == 2)
                        nameTextbox.setText("Helicopter");
                    else if (selectedType == 3)
                        nameTextbox.setText("Airplane");
                }
            }
        }
    }

    @Override
    public void render(Game game) {
        Render.drawSpriteSheetObject(Textures.tabHeaders[1], 161f, 69.2f, 159, 51.36f, Textures.spriteSheet);
        Fonts.tabHeaderLarge.drawText(name, 238.5f - ((float) Fonts.tabHeaderLarge.getWidth(name) / 2f), 72f, Colors.FONT_LIGHT);

        // Product Chart
        Render.drawSpriteSheetObject(Textures.chart, 46, 131f, 553.4f, 247f, Textures.spriteSheet);
        Fonts.chartHeader.drawText("Product Name", 54f, 132f, Colors.FONT_LIGHT);
        Fonts.chartHeader.drawText("$ per day", 480f, 132f, Colors.FONT_LIGHT);

        // Scroll bar
        Render.drawSpriteSheetObject(Textures.scrollLine, 18, 131f, 14.26f, 247f, Textures.spriteSheet);
        Render.drawSpriteSheetObject(Textures.scrollDot, 15f, 145f, 19.96f, 19.96f, Textures.spriteSheet);

        // New product
        Render.drawSpriteSheetObject(Textures.lineBreak, 14f, 390f, 612f, 3f, Textures.spriteSheet);
        Fonts.chartHeader.drawText("Name:", 20f, 406f, Colors.FONT_LIGHT);
        Render.drawSpriteSheetObject(Textures.textBox, 90f, 400f, 362f, 34.2f, Textures.spriteSheet);
        nameTextbox.render();
        for (int i = 0; i < 4; i++) {
            if (i == selectedType)
                Render.drawSpriteSheetObject(Textures.toggleBoxActive, 20f + (114f * i), 440f, 114f, 37f, Textures.spriteSheet);
            else
                Render.drawSpriteSheetObject(Textures.toggleBox, 20f + (114f * i), 440f, 114f, 37f, Textures.spriteSheet);
        }
        Fonts.chartHeader.drawText("Quad", 49f, 442f, Colors.FONT_LIGHT);
        Fonts.chartHeader.drawText("Micro", 156.5f, 442f, Colors.FONT_LIGHT);
        Fonts.chartHeader.drawText("Heli", 281f, 442f, Colors.FONT_LIGHT);
        Fonts.chartHeader.drawText("Plane", 385.5f, 442f, Colors.FONT_LIGHT);
    }
}

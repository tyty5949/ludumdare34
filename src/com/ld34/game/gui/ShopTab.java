package com.ld34.game.gui;

import com.ld34.game.Game;
import com.ld34.game.data.Colors;
import com.ld34.game.data.Fonts;
import com.ld34.game.data.Textures;
import com.ld34.render.Render;
import com.ld34.util.Button;
import com.ld34.util.Mouse;
import com.ld34.util.Utilities;

/**
 * Created on 12/11/2015.
 */
public class ShopTab extends TabComponent {

    private boolean mouseCooldown;

    private Button[] buttons;
    private int activeButton;
    private int activeButtonState;

    private long[] prices;
    private String[] priceStrings;

    public ShopTab() {
        mouseCooldown = false;
        headerButton = new Button(479f, 69.2f, 159, 51.36f);
        name = "Shop";

        buttons = new Button[6];
        buttons[0] = new Button(20, 152, 186, 186);
        buttons[1] = new Button(226, 152, 186, 186);
        buttons[2] = new Button(432, 152, 186, 186);
        buttons[3] = new Button(20, 378, 186, 186);
        buttons[4] = new Button(226, 378, 186, 186);
        buttons[5] = new Button(432, 378, 186, 186);
        activeButton = 0;
        activeButtonState = 0;
        prices = new long[4];
        priceStrings = new String[4];
        for (int i = 0; i < prices.length; i++) {
            prices[i] = Game.STARTING_PRICES[i];
            priceStrings[i] = Utilities.formatMoney(prices[i], Utilities.ADD_COMMAS);
        }
    }

    @Override
    public void update(Game game) {
        activeButton = -1;
        for (int i = 0; i < 4; i++) {
            if (buttons[i].contains(Mouse.getX(), Mouse.getY())) {
                activeButton = i;
                if (Mouse.isButtonDown(0))
                    activeButtonState = 1;
                else
                    activeButtonState = 0;
            }
        }

        if (Mouse.isButtonDown(0) && !mouseCooldown) {
            if (buttons[0].contains(Mouse.getX(), Mouse.getY()) && game.getMoney() >= prices[0]) {
                game.setAdvertisingStaff(game.getAdvertisingStaff() + 1);
                game.setMoney(game.getMoney() - prices[0]);
                prices[0] = Game.STARTING_PRICES[0] + (Game.STARTING_PRICES[0] * Utilities.factorial(game.getAdvertisingStaff()));
                priceStrings[0] = Utilities.formatMoney(prices[0], Utilities.ADD_COMMAS);
                game.setMoneyString(Utilities.formatMoney(game.getMoney(), Utilities.ADD_COMMAS));
            } else if (buttons[1].contains(Mouse.getX(), Mouse.getY()) && game.getMoney() >= prices[1]) {
                game.setResearchStaff(game.getResearchStaff() + 1);
                game.setMoney(game.getMoney() - prices[1]);
                prices[1] = Game.STARTING_PRICES[1] + (Game.STARTING_PRICES[1] * Utilities.factorial(game.getResearchStaff()));
                priceStrings[1] = Utilities.formatMoney(prices[1], Utilities.ADD_COMMAS);
                game.setMoneyString(Utilities.formatMoney(game.getMoney(), Utilities.ADD_COMMAS));
            } else if (buttons[2].contains(Mouse.getX(), Mouse.getY()) && game.getMoney() >= prices[2]) {
                game.setBookCookers(game.getBookCookers() + 1);
                game.setMoney(game.getMoney() - prices[2]);
                prices[2] = Game.STARTING_PRICES[2] + (Game.STARTING_PRICES[2] * Utilities.factorial(game.getBookCookers()));
                priceStrings[2] = Utilities.formatMoney(prices[2], Utilities.ADD_COMMAS);
                game.setMoneyString(Utilities.formatMoney(game.getMoney(), Utilities.ADD_COMMAS));
            } else if (buttons[3].contains(Mouse.getX(), Mouse.getY()) && game.getMoney() >= prices[3]) {
                if (game.getFactoryLevel() + 1 <= 5) {
                    game.setFactoryLevel(game.getFactoryLevel() + 1);
                    game.setMoney(game.getMoney() - prices[3]);
                    prices[3] = Game.STARTING_PRICES[3] + (Game.STARTING_PRICES[3] * Utilities.factorial(game.getFactoryLevel()));
                    priceStrings[3] = Utilities.formatMoney(prices[3], Utilities.ADD_COMMAS);
                    game.setMoneyString(Utilities.formatMoney(game.getMoney(), Utilities.ADD_COMMAS));
                }
            }
        }

        mouseCooldown = Mouse.isButtonDown(0);
    }

    @Override
    public void render(Game game) {
        Render.drawSpriteSheetObject(Textures.tabHeaders[3], 479f, 69.2f, 159, 51.36f, Textures.spriteSheet);
        Fonts.tabHeaderLarge.drawText(name, 556.5f - ((float) Fonts.tabHeaderLarge.getWidth(name) / 2f), 72f, Colors.FONT_LIGHT);

        for (int i = 0; i < 4; i++) {
            if (i == activeButton)
                Render.drawSpriteSheetObject(Textures.baseShopButtons[activeButtonState + 1], 20 + (206 * (i % 3)), 152 + (226 * (i / 3)), 186, 186, Textures.spriteSheet);
            else
                Render.drawSpriteSheetObject(Textures.baseShopButtons[0], 20 + (206 * (i % 3)), 152 + (226 * (i / 3)), 186, 186, Textures.spriteSheet);
            //Render.drawSpriteSheetObject(Textures.shopButtons[i], 20 + (206 * (i % 3)), 152 + (226 * (i / 3)), 186, 186, Textures.spriteSheet);
        }

        Fonts.shopButtonLabel.drawText("advertising staff", 29, 152, Colors.FONT_LIGHT);
        if (game.getMoney() >= prices[0])
            Fonts.shopButtonLabel.drawText("price: " + priceStrings[0], 29, 170, Colors.FONT_LIGHT);
        else
            Fonts.shopButtonLabel.drawText("price: " + priceStrings[0], 29, 170, Colors.FONT_ERROR);
        Fonts.shopButtonLabel.drawText("employed: " + game.getAdvertisingStaff(), 29, 295, Colors.FONT_LIGHT);
        Fonts.shopButtonLabel.drawText("+ 1-5% sales", 29, 313, Colors.FONT_LIGHT);

        Fonts.shopButtonLabel.drawText("research staff", 235, 152, Colors.FONT_LIGHT);
        if (game.getMoney() >= prices[1])
            Fonts.shopButtonLabel.drawText("price: " + priceStrings[1], 235, 170, Colors.FONT_LIGHT);
        else
            Fonts.shopButtonLabel.drawText("price: " + priceStrings[1], 235, 170, Colors.FONT_ERROR);
        Fonts.shopButtonLabel.drawText("employed: " + game.getResearchStaff(), 235, 295, Colors.FONT_LIGHT);
        Fonts.shopButtonLabel.drawText("+ desire accuracy", 235, 313, Colors.FONT_LIGHT);

        Fonts.shopButtonLabel.drawText("book cookers", 441, 152, Colors.FONT_LIGHT);
        if (game.getMoney() >= prices[2])
            Fonts.shopButtonLabel.drawText("price: " + priceStrings[2], 441, 170, Colors.FONT_LIGHT);
        else
            Fonts.shopButtonLabel.drawText("price: " + priceStrings[2], 441, 170, Colors.FONT_ERROR);
        Fonts.shopButtonLabel.drawText("employed: " + game.getBookCookers(), 441, 295, Colors.FONT_LIGHT);
        Fonts.shopButtonLabel.drawText("- 1-5% expenses", 441, 313, Colors.FONT_LIGHT);

        Fonts.shopButtonLabel.drawText("factory upgrade", 29, 378, Colors.FONT_LIGHT);
        if (game.getFactoryLevel() == 5)
            Fonts.shopButtonLabel.drawText("max level", 29, 396, Colors.FONT_LIGHT);
        else {
            if (game.getMoney() >= prices[3])
                Fonts.shopButtonLabel.drawText("price: " + priceStrings[3], 29, 396, Colors.FONT_LIGHT);
            else
                Fonts.shopButtonLabel.drawText("price: " + priceStrings[3], 29, 396, Colors.FONT_ERROR);
        }
        Fonts.shopButtonLabel.drawText(Game.FACTORY_LEVELS[game.getFactoryLevel()], 29, 521, Colors.FONT_LIGHT);
        Fonts.shopButtonLabel.drawText("+ 100% production", 29, 539, Colors.FONT_LIGHT);

    }
}

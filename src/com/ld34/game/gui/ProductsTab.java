package com.ld34.game.gui;

import com.ld34.game.Game;
import com.ld34.game.Product;
import com.ld34.game.data.Colors;
import com.ld34.game.data.Fonts;
import com.ld34.game.data.Textures;
import com.ld34.render.Render;
import com.ld34.util.Button;
import com.ld34.util.Mouse;
import com.ld34.util.Textbox;
import com.ld34.util.Utilities;

/**
 * Created on 12/11/2015.
 */
public class ProductsTab extends TabComponent {

    private boolean mouseCooldown = false;

    private int scrollBallOffset;
    private int scrollOffset;

    private Textbox nameTextbox;
    private Button[] types;
    private Button[] addPoints;
    private Button[] subtractPoints;
    private int selectedType;
    private int pointsUsed = 0;
    private int speedPoints = 0;
    private int agilityPoints = 0;
    private int strengthPoints = 0;
    private int[][] perks;
    private Button submitButton;
    private int buttonState;

    private int price;
    private Textbox priceTextbox;

    private Product selectedProduct;
    private Product tempProduct;
    private int advertisingCost;
    private Textbox advertisingTextbox;
    private int productionCost;

    private int lastDay = 0;

    public ProductsTab(Game game) {
        headerButton = new Button(161f, 69.2f, 159, 51.36f);
        name = "Products";
        scrollBallOffset = 0;
        scrollOffset = 0;
        selectedType = 0;

        submitButton = new Button(540, 406, 79.86f, 31.37f);
        nameTextbox = new Textbox(90f, 404f, 362f, 30f, 25, "Quadcopter", false);
        types = new Button[4];
        addPoints = new Button[3];
        subtractPoints = new Button[3];
        for (int i = 0; i < 4; i++)
            types[i] = new Button(20f + (114f * i), 450f, 114f, 37f);
        for (int i = 0; i < 3; i++)
            addPoints[i] = new Button(171.2f, 537 + (i * 28), 16.2f, 10);
        for (int i = 0; i < 3; i++)
            subtractPoints[i] = new Button(155, 537 + (i * 28), 16.2f, 10);
        perks = new int[4][0];
        perks[0] = new int[]{0};
        advertisingCost = 1000;
        price = 100;
        priceTextbox = new Textbox(422, 529, 114, 31.4f, 25, "" + price, true);
        generateTempProduct(game);
    }

    @Override
    public void update(Game game) {
        if (game.getDayCounter() != lastDay) {
            lastDay = game.getDayCounter();
            generateTempProduct(game);
        }

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
                    generateTempProduct(game);
                }
            }
        }

        if (Mouse.isButtonDown(0) && !mouseCooldown) {
            // TODO - Allow products to be edited
            for (int i = 0; i < 3; i++) {
                if (addPoints[i].contains(Mouse.getX(), Mouse.getY())) {
                    if (pointsUsed < game.getBuildPoints()) {
                        if (i == 0 && speedPoints < 10) {
                            speedPoints++;
                            pointsUsed++;
                            calculatePerks(game);
                        } else if (i == 1 && agilityPoints < 10) {
                            agilityPoints++;
                            pointsUsed++;
                            calculatePerks(game);
                        } else if (i == 2 && strengthPoints < 10) {
                            strengthPoints++;
                            pointsUsed++;
                            calculatePerks(game);
                        }
                    }
                }
            }

            for (int i = 0; i < 3; i++) {
                if (subtractPoints[i].contains(Mouse.getX(), Mouse.getY())) {
                    if (i == 0 && speedPoints > 0) {
                        speedPoints--;
                        pointsUsed--;
                        calculatePerks(game);
                    } else if (i == 1 && agilityPoints > 0) {
                        agilityPoints--;
                        pointsUsed--;
                        calculatePerks(game);
                    } else if (i == 2 && strengthPoints > 0) {
                        strengthPoints--;
                        pointsUsed--;
                        calculatePerks(game);
                    }
                }
            }

            if (submitButton.contains(Mouse.getX(), Mouse.getY())) {
                // TODO - Update product if editing
                // TODO - Add in things that stop from buying
                generateTempProduct(game);
                game.addProduct(tempProduct);
                resetForm(game);
            }
        }

        if (submitButton.contains(Mouse.getX(), Mouse.getY())) {
            if (Mouse.isButtonDown(0))
                buttonState = 2;
            else
                buttonState = 1;
        } else
            buttonState = 0;

        priceTextbox.update();
        if (priceTextbox.getText().equals("")) {
            price = 0;
            generateTempProduct(game);
        } else if (Integer.parseInt(priceTextbox.getText()) != price) {
            price = Integer.parseInt(priceTextbox.getText());
            generateTempProduct(game);
        }

        mouseCooldown = Mouse.isButtonDown(0);
    }

    @Override
    public void render(Game game) {
        Render.drawSpriteSheetObject(Textures.tabHeaders[1], 161f, 69.2f, 159, 51.36f, Textures.spriteSheet);
        Fonts.tabHeaderLarge.drawText(name, 238.5f - ((float) Fonts.tabHeaderLarge.getWidth(name) / 2f), 72f, Colors.FONT_LIGHT);

        // Product Chart
        Render.drawSpriteSheetObject(Textures.chart, 46, 131f, 553.4f, 247f, Textures.spriteSheet);
        Fonts.chartHeader.drawText("Product Name", 54f, 132f, Colors.FONT_LIGHT);
        Fonts.chartHeader.drawText("$ per day", 480f, 132f, Colors.FONT_LIGHT);
        for (int i = 0; i < game.getProducts().size(); i++) {
            Fonts.chartText.drawText(game.getProducts().get(i).getName(), 54, 170 + (i * 22), Colors.FONT_LIGHT);
            Fonts.chartText.drawText("" + Utilities.formatMoney(game.getProducts().get(i).getProfit(), Utilities.ADD_COMMAS), 484, 170 + (i * 22), Colors.FONT_LIGHT);
        }

        // Scroll bar
        Render.drawSpriteSheetObject(Textures.scrollLine, 18, 131f, 14.26f, 247f, Textures.spriteSheet);
        Render.drawSpriteSheetObject(Textures.scrollDot, 15f, 145f, 19.96f, 19.96f, Textures.spriteSheet);

        // New product
        Render.drawSpriteSheetObject(Textures.lineBreak, 14f, 390f, 612f, 3f, Textures.spriteSheet);
        Fonts.chartHeader.drawText("Name:", 20f, 406f, Colors.FONT_LIGHT);
        Render.drawSpriteSheetObject(Textures.textBox, 88f, 405f, 362f, 34.2f, Textures.spriteSheet);

        //TODO - Change button to say editing
        Render.drawSpriteSheetObject(Textures.buttons[buttonState], 540, 406, 79.86f, 31.37f, Textures.spriteSheet);
        Fonts.chartHeader.drawText("Add", 560, 407, Colors.FONT_LIGHT);

        nameTextbox.render();
        for (int i = 0; i < 4; i++) {
            if (i == selectedType)
                Render.drawSpriteSheetObject(Textures.toggleBoxActive, 20f + (114f * i), 450f, 114f, 37f, Textures.spriteSheet);
            else
                Render.drawSpriteSheetObject(Textures.toggleBox, 20f + (114f * i), 450f, 114f, 37f, Textures.spriteSheet);
        }
        Fonts.chartHeader.drawText("Quad", 49f, 452f, Colors.FONT_LIGHT);
        Fonts.chartHeader.drawText("Micro", 156.5f, 452f, Colors.FONT_LIGHT);
        Fonts.chartHeader.drawText("Heli", 281f, 452f, Colors.FONT_LIGHT);
        Fonts.chartHeader.drawText("Plane", 385.5f, 452f, Colors.FONT_LIGHT);

        Fonts.chartHeader.drawText("Attributes", 40, 489f, Colors.FONT_LIGHT);
        Fonts.shopButtonLabel.drawText("Points Remaining: " + (game.getBuildPoints() - pointsUsed), 15, 510, Colors.FONT_LIGHT);
        Fonts.shopButtonLabel.drawText("Speed " + speedPoints, 20f, 534, Colors.FONT_LIGHT);
        Render.drawFilledRect(21, 555, speedPoints * 16f, 4, Colors.FONT_GOOD);
        Render.drawSpriteSheetObject(Textures.fillBar, 20, 554, 162.6f, 5.6f, Textures.spriteSheet);
        Render.drawSpriteSheetObject(Textures.plusMinus, 159.8f, 538, 22.8f, 8.6f, Textures.spriteSheet);
        Fonts.shopButtonLabel.drawText("Agility " + agilityPoints, 20f, 562, Colors.FONT_LIGHT);
        Render.drawFilledRect(21, 583, agilityPoints * 16f, 4, Colors.FONT_GOOD);
        Render.drawSpriteSheetObject(Textures.fillBar, 20, 582, 162.6f, 5.6f, Textures.spriteSheet);
        Render.drawSpriteSheetObject(Textures.plusMinus, 159.8f, 566, 22.8f, 8.6f, Textures.spriteSheet);
        Fonts.shopButtonLabel.drawText("Strength " + strengthPoints, 20f, 590, Colors.FONT_LIGHT);
        Render.drawFilledRect(21, 611, strengthPoints * 16f, 4, Colors.FONT_GOOD);
        Render.drawSpriteSheetObject(Textures.fillBar, 20, 610, 162.6f, 5.6f, Textures.spriteSheet);
        Render.drawSpriteSheetObject(Textures.plusMinus, 159.8f, 594, 22.8f, 8.6f, Textures.spriteSheet);

        Fonts.chartHeader.drawText("Perks", 272, 489, Colors.FONT_LIGHT);
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < perks[i].length; j++) {
                Fonts.shopButtonLabel.drawText(Game.PERKS[i][perks[i][j]], 212, 510 + (count * 14), Colors.FONT_LIGHT);
                count++;
            }
        }

        Fonts.chartHeader.drawText("Est Profit:", 400, 489, Colors.FONT_LIGHT);
        if (tempProduct.getProfit() <= 0)
            Fonts.chartHeader.drawText("$" + tempProduct.getProfit(), 535, 489, Colors.FONT_ERROR);
        else
            Fonts.chartHeader.drawText("$" + tempProduct.getProfit(), 535, 489, Colors.FONT_GOOD);

        Fonts.chartText.drawText("Price", 450, 508, Colors.FONT_LIGHT);
        Render.drawSpriteSheetObject(Textures.moneyTextBox, 420, 530, 114, 31.4f, Textures.spriteSheet);
        priceTextbox.render();
    }

    private void resetForm(Game game) {
        perks = new int[4][0];
        perks[0] = new int[]{0};
        pointsUsed = 0;
        speedPoints = 0;
        agilityPoints = 0;
        strengthPoints = 0;
        selectedType = 0;
        nameTextbox.setText("Quadcopter");
        generateTempProduct(game);
    }

    private void calculatePerks(Game game) {
        if (speedPoints == 10)
            perks[0] = new int[]{9};
        else if (speedPoints >= 8)
            perks[0] = new int[]{7};
        else if (speedPoints >= 5)
            perks[0] = new int[]{4};
        else if (speedPoints >= 3)
            perks[0] = new int[]{2};
        else
            perks[0] = new int[]{0};

        if (agilityPoints == 10)
            perks[1] = new int[]{2, 4, 9};
        else if (agilityPoints >= 5)
            perks[1] = new int[]{2, 4};
        else if (agilityPoints >= 3)
            perks[1] = new int[]{2};
        else
            perks[1] = new int[]{};

        if (strengthPoints >= 9)
            perks[2] = new int[]{4, 8};
        else if (strengthPoints >= 5)
            perks[2] = new int[]{4};
        else
            perks[2] = new int[]{};

        generateTempProduct(game);
    }

    private void generateTempProduct(Game game) {
        if (selectedType == 0)
            productionCost = (99 * speedPoints) + (99 * agilityPoints) + (99 * strengthPoints);
        else if (selectedType == 1)
            productionCost = (16 * speedPoints) + (16 * agilityPoints) + (16 * strengthPoints);
        else if (selectedType == 2)
            productionCost = (49 * speedPoints) + (49 * agilityPoints) + (49 * strengthPoints);
        else if (selectedType == 3)
            productionCost = (83 * speedPoints) + (83 * agilityPoints) + (83 * strengthPoints);
        tempProduct = new Product(selectedType, nameTextbox.getText(), speedPoints, strengthPoints, agilityPoints, price, perks, advertisingCost, productionCost, game);
    }
}

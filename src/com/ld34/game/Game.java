package com.ld34.game;

import com.ld34.Main;
import com.ld34.game.data.Colors;
import com.ld34.game.data.Fonts;
import com.ld34.game.data.Textures;
import com.ld34.game.gui.*;
import com.ld34.render.Render;
import com.ld34.util.Button;
import com.ld34.util.GameState;
import com.ld34.util.Mouse;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created on 12/11/2015.
 */
public class Game extends GameState {

    public static final int[] DAYS_IN_MONTHS = {
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public static final String[] MONTHS = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    private boolean paused;
    private Button pauseButton;
    private boolean mouseCooldown;

    private int dayCounter;
    private double dayTimer = 2;
    private int month;
    private int day;

    private double marketShare = 100;
    private long money;
    private String moneyString;
    private TabComponent[] tabs;

    @Override
    public void init(Main main) {
        tabs = new TabComponent[4];
        tabs[0] = new OverviewTab();
        tabs[1] = new ProductsTab();
        tabs[2] = new MarketingTab();
        tabs[3] = new ShopTab();

        paused = true;
        pauseButton = new Button(0, 0, 48, 64);
        mouseCooldown = false;
        money = 784785444;
        simulateDay();

        name = "Game";
        initialized = true;
    }

    @Override
    public void update(double delta, Main main) {
        if (Mouse.isButtonDown(0) && !mouseCooldown) {
            if (pauseButton.contains(Mouse.getX(), Mouse.getY()))
                paused = !paused;
        }

        if (!paused) {
            dayTimer += delta;
            if (dayTimer >= 1.5) {
                simulateDay();
                dayTimer = 0;
                dayCounter++;
                day = dayCounter;
                for (int i = 0; i < month; i++) day -= DAYS_IN_MONTHS[i];
                if (day > DAYS_IN_MONTHS[month]) {
                    month++;
                    day = 1;
                }
            }
        }

        mouseCooldown = Mouse.isButtonDown(0);
    }

    @Override
    public void render(double delta, Main main) {
        // Top Bar
        Render.drawFilledRect(0f, 0f, 640f, 64f, Colors.UI_COLOR_DARK);
        Fonts.topBar.drawText(moneyString, 650 - ((float) Fonts.topBar.getWidth(moneyString)), 4, Colors.FONT_DARK);
        Fonts.topBar.drawText("MS: " + marketShare + "%", 320f - ((float) Fonts.topBar.getWidth("MS: " + marketShare + "%") / 2.0f), 4f, Colors.FONT_DARK);
        Fonts.topBar.drawText(MONTHS[month] + ", " + day, 48, 4, Colors.FONT_DARK);
        if (paused)
            Render.drawSpriteSheetObject(Textures.pauseButton, 8, 17, 32, 32, Textures.spriteSheet);
        else
            Render.drawSpriteSheetObject(Textures.playButton, 8, 17, 32, 32, Textures.spriteSheet);

        // Tabs

        // Helps
    }

    private void simulateDay() {


        // Compute money string
        String suf = "";
        double tempMoney = (double) money;
        if (money > 999999999) {
            suf = "bil";
            tempMoney /= 1000000000;
        } else if (money > 999999) {
            suf = "mil";
            tempMoney /= 1000000;
        }
        if (money > 999999)
            moneyString = "$" + new DecimalFormat("#,##0.0").format(new BigDecimal(tempMoney)) + " " + suf;
        else
            moneyString = "$" + new DecimalFormat("#,##0").format(new BigDecimal(tempMoney)) + " " + suf;
    }
}

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
import com.ld34.util.Utilities;

import java.util.ArrayList;

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

    public static final String[] FACTORY_LEVELS = {
            "Parents Basement", "Your Own Garage", "Small Factory", "Large Factory", "Mega Factory", "Chinese Outsource"
    };

    public static final String[][] PERKS = {
            {"Max Speed of 5 mph", "", "Max Speed of 10 mph", "", "Max Speed of 40 mph", "", "", "Max Speed of 75 mph", "", "Max Speed of 100 mph"},
            {"", "", "Can do Flips", "", "Can do Loops", "", "", "", "", "Autonomous Flight"},
            {"", "", "", "", "Robust Motors", "", "", "", "All Carbon Fiber", ""},
            {}
    };

    public static final int[] STARTING_PRICES = {
            1200, 1200, 1900, 100000
    };

    private boolean paused;
    private Button pauseButton;
    private boolean mouseCooldown;

    private Economy economy;

    private int dayCounter;
    private double dayTimer;
    private int month;
    private int day;

    private double marketShare = 1.0;

    private ArrayList<Double> lastDailyMarketShares;

    private long money;

    private long profit = 1;
    private ArrayList<Long> lastDailyProfits;
    private long expenditure = 0;
    private long income = 0;

    private String moneyString;
    private TabComponent[] tabs;

    private ArrayList<Product> products;

    private int advertisingStaff;
    private int researchStaff;
    private int bookCookers;
    private int factoryLevel;

    private int buildPoints;

    @Override
    public void init(Main main) {
        economy = new Economy();

        paused = true;
        pauseButton = new Button(0, 0, 48, 64);
        mouseCooldown = false;
        dayTimer = 0;
        dayCounter = 1;
        day = 1;
        money = 0;
        moneyString = Utilities.formatMoney(money, Utilities.ADD_COMMAS);

        products = new ArrayList<>();

        lastDailyProfits = new ArrayList<>();
        lastDailyProfits.add(1L);
        lastDailyMarketShares = new ArrayList<>();
        lastDailyMarketShares.add(-1.0);

        buildPoints = 10;
        tabs = new TabComponent[4];
        tabs[0] = new OverviewTab();
        tabs[1] = new ProductsTab(this);
        tabs[2] = new ResearchTab();
        tabs[3] = new ShopTab();

        name = "Game";
        initialized = true;
    }

    @Override
    public void update(double delta, Main main) {
        if (Mouse.isButtonDown(0) && !mouseCooldown) {
            if (pauseButton.contains(Mouse.getX(), Mouse.getY()))
                paused = !paused;

            if (tabs[0].headerButton.contains(Mouse.getX(), Mouse.getY())) {
                tabs[0].active = true;
                tabs[1].active = false;
                tabs[2].active = false;
                tabs[3].active = false;
            } else if (tabs[1].headerButton.contains(Mouse.getX(), Mouse.getY())) {
                tabs[0].active = false;
                tabs[1].active = true;
                tabs[2].active = false;
                tabs[3].active = false;
            } else if (tabs[2].headerButton.contains(Mouse.getX(), Mouse.getY())) {
                tabs[0].active = false;
                tabs[1].active = false;
                tabs[2].active = true;
                tabs[3].active = false;
            } else if (tabs[3].headerButton.contains(Mouse.getX(), Mouse.getY())) {
                tabs[0].active = false;
                tabs[1].active = false;
                tabs[2].active = false;
                tabs[3].active = true;
            }
        }
        for (TabComponent tab : tabs) if (tab.active) tab.update(this);

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
        Fonts.topBar.drawText(moneyString, 636 - ((float) Fonts.topBar.getWidth(moneyString)), 4, Colors.FONT_LIGHT);
        Fonts.topBar.drawText("MS: " + marketShare + "%", 320f - ((float) Fonts.topBar.getWidth("MS: " + marketShare + "%") / 2.0f), 4f, Colors.FONT_LIGHT);
        Fonts.topBar.drawText(MONTHS[month] + ", " + day, 48, 4, Colors.FONT_LIGHT);
        if (paused)
            Render.drawSpriteSheetObject(Textures.playButton, 8, 17, 32, 32, Textures.spriteSheet);
        else
            Render.drawSpriteSheetObject(Textures.pauseButton, 8, 17, 32, 32, Textures.spriteSheet);

        // Tabs
        for (int i = 0; i < 4; i++) {
            Render.drawSpriteSheetObject(Textures.tabHeaders[i], 2f + (i * 159), 77.76f, 159, 51.36f, Textures.spriteSheet);
            Fonts.tabHeaderSmall.drawText(tabs[i].name, 81.5f + (i * 159) - ((float) Fonts.tabHeaderSmall.getWidth(tabs[i].name) / 2f), 77.76f, Colors.FONT_LIGHT);
        }
        Render.drawSpriteSheetObject(Textures.tabBackground, 2, 112, 636, 526, Textures.spriteSheet);
        if (tabs[0].active)
            tabs[0].render(this);
        else if (tabs[1].active)
            tabs[1].render(this);
        else if (tabs[2].active)
            tabs[2].render(this);
        else if (tabs[3].active)
            tabs[3].render(this);

        // Helps
    }

    private void simulateDay() {
        economy.simulate(this);

        // Update graphs
        lastDailyMarketShares.add(marketShare);
        lastDailyProfits.add(profit);

        // Compute money string
        moneyString = Utilities.formatMoney(money, Utilities.ADD_COMMAS);
    }

    public void computePerks() {

    }

    public int getAdvertisingStaff() {
        return advertisingStaff;
    }

    public void setAdvertisingStaff(int advertisingStaff) {
        this.advertisingStaff = advertisingStaff;
        computePerks();
    }

    public int getResearchStaff() {
        return researchStaff;
    }

    public void setResearchStaff(int researchStaff) {
        this.researchStaff = researchStaff;
        computePerks();
    }

    public int getBookCookers() {
        return bookCookers;
    }

    public void setBookCookers(int bookCookers) {
        this.bookCookers = bookCookers;
        computePerks();
    }

    public int getFactoryLevel() {
        return factoryLevel;
    }

    public void setFactoryLevel(int factoryLevel) {
        this.factoryLevel = factoryLevel;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getMoneyString() {
        return moneyString;
    }

    public void setMoneyString(String moneyString) {
        this.moneyString = moneyString;
    }

    public ArrayList<Double> getLastDailyMarketShares() {
        return lastDailyMarketShares;
    }

    public void setLastDailyMarketShares(ArrayList<Double> lastDailyMarketShares) {
        this.lastDailyMarketShares = lastDailyMarketShares;
    }

    public ArrayList<Long> getLastDailyProfits() {
        return lastDailyProfits;
    }

    public void setLastDailyProfits(ArrayList<Long> lastDailyProfits) {
        this.lastDailyProfits = lastDailyProfits;
    }

    public int getBuildPoints() {
        return buildPoints;
    }

    public void setBuildPoints(int buildPoints) {
        this.buildPoints = buildPoints;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void setProduct(int i, Product product) {
        products.set(i, product);
    }

    public Economy getEconomy() {
        return economy;
    }

    public int getDayCounter() {
        return dayCounter;
    }

    public void setDayCounter(int dayCounter) {
        this.dayCounter = dayCounter;
    }

    public double getMarketShare() {
        return marketShare;
    }

    public void setMarketShare(double marketShare) {
        this.marketShare = marketShare;
    }

    public long getProfit() {
        return profit;
    }

    public void setProfit(long profit) {
        this.profit = profit;
    }
}

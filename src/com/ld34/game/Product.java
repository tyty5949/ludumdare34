package com.ld34.game;

/**
 * Created on 12/12/2015.
 */
public class Product {

    private int type;
    private String name;
    private int speed;
    private int strength;
    private int agility;
    private int price;
    private int[][] perks;
    private int advertisingCost;
    private int productionCost;

    private int profit;

    public Product(int type, String name, int speed, int strength, int agility, int price, int[][] perks, int advertisingCost, int productionCost, Game game) {
        this.type = type;
        this.name = name;
        this.speed = speed;
        this.strength = strength;
        this.agility = agility;
        this.price = price;
        this.perks = perks;
        this.advertisingCost = advertisingCost;
        this.productionCost = productionCost;
        profit = game.getEconomy().calculateProfit(this, game);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int[][] getPerks() {
        return perks;
    }

    public void setPerks(int[][] perks) {
        this.perks = perks;
    }

    public int getAdvertisingCost() {
        return advertisingCost;
    }

    public void setAdvertisingCost(int advertisingCost) {
        this.advertisingCost = advertisingCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(int productionCost) {
        this.productionCost = productionCost;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }
}
package com.ld34.game;

import com.ld34.util.Utilities;

/**
 * Created on 12/13/2015.
 */
public class Economy {

    private double speedDesire;
    private double agilityDesire;
    private double strengthDesire;

    private double speedDesireMomentum;
    private double agilityDesireMomentum;
    private double strengthDesireMomentum;

    private double people;

    public Economy() {
        speedDesire = Math.random() * 10;
        agilityDesire = Math.random() * 10;
        strengthDesire = Math.random() * 10;
        speedDesireMomentum = 0;
        agilityDesireMomentum = 0;
        strengthDesireMomentum = 0;
    }

    public void simulate(Game game) {
        // People
        //TODO - Add people

        // Speed
        if (speedDesire > 5.0)
            speedDesireMomentum--;
        else
            speedDesireMomentum++;

        if (speedDesireMomentum > 0.0) {
            if (Math.random() * 50.0 < speedDesireMomentum) {
                speedDesire += (speedDesireMomentum / 8.2) * Utilities.boundedRandom(.8, 1.2);
                speedDesireMomentum = 0;
                if (speedDesire > 10.0)
                    speedDesire = 10.0;
            }
        } else {
            if (Math.random() * 50.0 < speedDesireMomentum * -1) {
                speedDesire += (speedDesireMomentum / 8.0) * Utilities.boundedRandom(.8, 1.2);
                if (speedDesire < 0.0)
                    speedDesire = 0.0;
                speedDesireMomentum = 0;
            }
        }

        // Agility
        if (agilityDesire > 5.0)
            agilityDesireMomentum--;
        else
            agilityDesireMomentum++;

        if (agilityDesireMomentum > 0.0) {
            if (Math.random() * 50.0 < agilityDesireMomentum) {
                agilityDesire += (agilityDesireMomentum / 10) * Utilities.boundedRandom(.8, 1.2);
                agilityDesireMomentum = 0;
                if (agilityDesire > 10.0)
                    agilityDesire = 10.0;
            }
        } else {
            if (Math.random() * 50.0 < agilityDesireMomentum * -1) {
                agilityDesire += (agilityDesireMomentum / 10) * Utilities.boundedRandom(.8, 1.2);
                if (agilityDesire < 0.0)
                    agilityDesire = 0.0;
                agilityDesireMomentum = 0;
            }
        }

        // Strength
        if (strengthDesire > 5.0)
            strengthDesireMomentum--;
        else
            strengthDesireMomentum++;

        if (strengthDesireMomentum > 0.0) {
            if (Math.random() * 50.0 < strengthDesireMomentum) {
                strengthDesire += (strengthDesireMomentum / 10) * Utilities.boundedRandom(.8, 1.2);
                strengthDesireMomentum = 0;
                if (strengthDesire > 10.0)
                    strengthDesire = 10.0;
            }
        } else {
            if (Math.random() * 50.0 < strengthDesireMomentum * -1) {
                strengthDesire += (strengthDesireMomentum / 10) * Utilities.boundedRandom(.8, 1.2);
                if (strengthDesire < 0.0)
                    strengthDesire = 0.0;
                strengthDesireMomentum = 0;
            }
        }

        long profit = 0;
        for (int i = 0; i < game.getProducts().size(); i++) {
            Product p = game.getProducts().get(i);
            double money = calculateProfit(p, game);
            profit += money;
            game.getProducts().get(i).setProfit((int) money);
        }
        game.setProfit(profit);
        game.setMoney(game.getMoney() + profit);
    }

    public int calculateProfit(Product p, Game game) {
        double medianPrice = medianPrice(p);
        System.out.println(medianPrice);
        double numberPurchased = (20 * game.getMarketShare()) * medianDesire(p);
        if (p.getPrice() > medianPrice) {
            numberPurchased *= (medianPrice / p.getPrice()) / 5;
            double profit = p.getPrice() * (numberPurchased);
            profit -= p.getAdvertisingCost() + (p.getProductionCost() * numberPurchased);
            return (int) profit;
        } else {
            return (int) (p.getPrice() * numberPurchased) - (int) (p.getAdvertisingCost() + (p.getProductionCost() * numberPurchased));
        }
    }

    public double medianPrice(Product product) {
        double price = 0;
        if (product.getType() == 0) {
            price = 300 + (medianDesire(product) * 3000);
        } else if (product.getType() == 1) {
            price = 50 + (medianDesire(product) * 500);
        } else if (product.getType() == 2) {
            price = 150 + (medianDesire(product) * 1500);
        } else if (product.getType() == 3) {
            price = 205 + (medianDesire(product) * 2050);
        }
        if (price <= 0)
            return 0;
        else
            return price;
    }

    public double medianDesire(Product product) {
        double desire = 0;
        if (speedDesire >= product.getSpeed())
            desire += product.getSpeed() / speedDesire;
        else if (speedDesire < product.getSpeed())
            desire += speedDesire / product.getSpeed();

        if (agilityDesire >= product.getAgility())
            desire += product.getAgility() / agilityDesire;
        else if (agilityDesire < product.getAgility())
            desire += agilityDesire / product.getAgility();

        if (strengthDesire >= product.getStrength())
            desire += product.getStrength() / strengthDesire;
        else if (strengthDesire < product.getStrength())
            desire += strengthDesire / product.getStrength();

        return desire / 3;
    }
}

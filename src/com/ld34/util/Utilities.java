package com.ld34.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created on 12/12/2015.
 */
public class Utilities {

    public static final DecimalFormat ADD_COMMAS = new DecimalFormat("#,##0.0");

    public static int factorial(int n) {
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    public static String formatMoney(long money, DecimalFormat df) {
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
            return "$" + df.format(new BigDecimal(tempMoney)) + " " + suf;
        else
            return "$" + new DecimalFormat("#,##0").format(new BigDecimal(tempMoney));
    }
}

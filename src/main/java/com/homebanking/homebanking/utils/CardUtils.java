package com.homebanking.homebanking.utils;

import java.text.DecimalFormat;

public class CardUtils {
    public static String createCardNumber() {
        DecimalFormat format = new DecimalFormat("0000");
        String number = "";
        for (int i = 0; i < 4; i++) {
            number += format.format((int) (Math.random() * 9999));
            if (i != 3) {
                number += "-";
            }
        }
        return number;
    }
    public static int generateCvv() {
        return (int) (Math.random() * 999);
    }
}

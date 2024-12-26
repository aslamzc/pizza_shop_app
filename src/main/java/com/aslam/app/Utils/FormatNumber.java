package com.aslam.app.Utils;

import java.text.NumberFormat;

public class FormatNumber {

    public static String currency(double amount) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingUsed(true);
        return formatter.format(amount);
    }
}

package com.leshka_and_friends.lgvb.view.ui_utils.card;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class CardFormatter {

    public static String formatCardNumber(String rawNumber) {
        if (rawNumber == null || rawNumber.length() != 16) {
            return rawNumber;
        }
        return rawNumber.replaceAll("(.{4})(?!$)", "$1   "); // groups of 4 with 3 spaces
    }

    public static String maskCardNumber(String raw) {
        if (raw == null || raw.isBlank()) {
            return "#".repeat(16);
        }

        return "*".repeat(raw.length() - 4) + raw.substring(raw.length() - 4);
    }

    public static String formatExpiryDate(YearMonth expiryDate) {
        if (expiryDate == null) {
            return "N/A";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        return expiryDate.format(formatter);
    }
}
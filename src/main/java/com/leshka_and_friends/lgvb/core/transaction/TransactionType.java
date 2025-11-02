package com.leshka_and_friends.lgvb.core.transaction;

public enum TransactionType {
    // Enum constants with their corresponding display/db string and icon
    DEPOSIT("deposit", "💰"),
    TRANSFER("transfer", "💸"),
    PAY_BILLS("pay_bills", "🛒");

    private final String strValue;
    private final String icon;

    // Constructor to set the values for each constant
    TransactionType(String strValue, String icon) {
        this.strValue = strValue;
        this.icon = icon;
    }

    public String getStrValue() {
        return strValue;
    }

    public String getIcon() {
        return icon;
    }

    // Utility method to get the Enum from a string (useful when reading from the DB)
    public static TransactionType fromString(String text) {
        for (TransactionType b : TransactionType.values()) {
            if (b.strValue.equalsIgnoreCase(text)) {
                return b;
            }
        }
        // Handle invalid/unknown strings safely
        return null;
    }
}

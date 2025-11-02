package com.leshka_and_friends.lgvb.core.transaction;

public enum TransactionStatus {
    SUCCESS,
    FAILED,
    PENDING;

    public static TransactionStatus fromString(String text) {
        for (TransactionStatus s : TransactionStatus.values()) {
            if (s.name().equalsIgnoreCase(text)) {
                return s;
            }
        }
        return null;
    }
}

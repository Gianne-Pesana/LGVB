package com.leshka_and_friends.lgvb.core.wallet;

public class WalletStateFactory {
    public static WalletState create(String status) {
        switch (status.trim().toUpperCase()) {
            case "ACTIVE" -> { return new ActiveWalletState(); }
            case "FROZEN" -> { return new FrozenWalletState(); }
            case "CLOSED" -> { return new ClosedWalletState(); }
            default -> { return null; }
        }
    }
}

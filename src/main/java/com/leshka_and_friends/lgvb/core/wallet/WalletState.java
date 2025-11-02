package com.leshka_and_friends.lgvb.core.wallet;

public interface WalletState {
    void deposit(Wallet wallet, double amount);
    void withdraw(Wallet wallet, double amount);
    void transfer(Wallet wallet, Wallet targetWallet, double amount);

    void activate(Wallet wallet);
    void freeze(Wallet wallet);
    void close(Wallet wallet);

    String getName();
}

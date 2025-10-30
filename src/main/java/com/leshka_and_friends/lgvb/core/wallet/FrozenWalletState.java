package com.leshka_and_friends.lgvb.core.wallet;

public class FrozenWalletState implements WalletState {

    @Override
    public void deposit(Wallet wallet, double amount) {

    }

    @Override
    public void withdraw(Wallet wallet, double amount) {
        System.out.println("cannot withdraw");
    }

    @Override
    public void transfer(Wallet wallet, Wallet targetWallet, double amount) {

    }

    @Override
    public void freeze(Wallet wallet) {

    }

    @Override
    public void close(Wallet wallet) {

    }

    @Override
    public String getName() {
        return "FROZEN";
    }
}

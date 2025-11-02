package com.leshka_and_friends.lgvb.core.wallet;

public class FrozenWalletState implements WalletState {

    @Override
    public void deposit(Wallet wallet, double amount) {
        wallet.setBalance(wallet.getBalance() + amount);
        System.out.println("Deposited " + amount);
    }

    @Override
    public void withdraw(Wallet wallet, double amount) {
        throw new IllegalStateException("Wallet is frozen. Cannot perform operation");
    }


    @Override
    public void transfer(Wallet wallet, Wallet targetWallet, double amount) {
        throw new IllegalStateException("Wallet is frozen. Cannot transfer");
    }

    @Override
    public void activate(Wallet wallet) {
        wallet.setState(new ActiveWalletState());
        wallet.setStatus("ACTIVE");
        System.out.println("Wallet is now active.");
    }

    @Override
    public void freeze(Wallet wallet) {
        throw new IllegalStateException("Wallet is already frozen!");
    }

    @Override
    public void close(Wallet wallet) {
        wallet.setState(new ClosedWalletState());
        wallet.setStatus("CLOSED");
        System.out.println("Wallet closed permanently.");
    }

    @Override
    public String getName() {
        return "FROZEN";
    }
}

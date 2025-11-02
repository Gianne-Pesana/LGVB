package com.leshka_and_friends.lgvb.core.wallet;

public class ClosedWalletState implements WalletState {

    @Override
    public void deposit(Wallet wallet, double amount) {
        throw new IllegalStateException("Wallet is closed, cannot perform deposit");
    }

    @Override
    public void transfer(Wallet wallet, Wallet targetWallet, double amount) {
        throw new IllegalStateException("Wallet is closed, cannot perform transfer");
    }

    @Override
    public void activate(Wallet wallet) {
        throw new IllegalStateException("Cannot perform operation, wallet is closed permanently");
    }

    @Override
    public void freeze(Wallet wallet) {
        throw new IllegalStateException("Cannot perform operation");
    }

    @Override
    public void close(Wallet wallet) {
        throw new IllegalStateException("Wallet is already closed");
    }

    @Override
    public String getName() {
        return "CLOSED";
    }
}

package com.leshka_and_friends.lgvb.core.wallet;

public class PendingWalletState implements WalletState {

    @Override
    public void deposit(Wallet wallet, double amount) {
        throw new IllegalStateException("Cannot deposit to a pending wallet");
    }

    @Override
    public void withdraw(Wallet wallet, double amount) {
        throw new IllegalStateException("Cannot withdraw from a pending wallet");
    }

    @Override
    public void transfer(Wallet wallet, Wallet targetWallet, double amount) {
        throw new IllegalStateException("Cannot transfer from a pending wallet");
    }

    @Override
    public void activate(Wallet wallet) {
        wallet.setState(new ActiveWalletState());
    }

    @Override
    public void freeze(Wallet wallet) {
        throw new IllegalStateException("Cannot freeze a pending wallet");
    }

    @Override
    public void close(Wallet wallet) {
        wallet.setState(new ClosedWalletState());
    }

    @Override
    public String getName() {
        return "Pending";
    }
}

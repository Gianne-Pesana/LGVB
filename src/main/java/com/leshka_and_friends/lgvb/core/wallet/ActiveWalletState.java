package com.leshka_and_friends.lgvb.core.wallet;

public class ActiveWalletState implements WalletState {

    @Override
    public void deposit(Wallet wallet, double amount) {
        System.out.println("[Active wallet] Old balance: " + wallet.getBalance());
        wallet.setBalance(wallet.getBalance() + amount);
        System.out.println("Deposited " + amount);
        System.out.println("[Active wallet] New balance: " + wallet.getBalance());

    }

    @Override
    public void transfer(Wallet wallet, Wallet targetWallet, double amount) {
        wallet.setBalance(wallet.getBalance() - amount);
        targetWallet.setBalance(targetWallet.getBalance() + amount);
        System.out.println("Transferred " + amount + " to " + targetWallet.getUserId());
    }

    @Override
    public void activate(Wallet wallet) {
        throw new IllegalStateException("Wallet is already active!");
    }

    @Override
    public void freeze(Wallet wallet) {
        wallet.setState(new FrozenWalletState());
        wallet.setStatus("FROZEN");
        System.out.println("Wallet is now frozen.");
    }

    @Override
    public void close(Wallet wallet) {
        wallet.setState(new ClosedWalletState());
        wallet.setStatus("CLOSED");
        System.out.println("Wallet closed permanently.");
    }

    @Override
    public String getName() {
        return "ACTIVE";
    }
}

package com.leshka_and_friends.lgvb.core.wallet;

public class ActiveWalletState implements WalletState {

    @Override
    public void deposit(Wallet wallet, double amount) {
        wallet.setBalance(wallet.getBalance() + amount);
        System.out.println("Deposited " + amount);
    }

    @Override
    public void withdraw(Wallet wallet, double amount) {
        if (wallet.getBalance() < amount) {
            throw new IllegalStateException("Insufficient funds");
        }
        wallet.setBalance(wallet.getBalance() - amount);
        System.out.println("Withdrew " + amount);
    }

    @Override
    public void transfer(Wallet wallet, Wallet targetWallet, double amount) {
        withdraw(wallet, amount);
        targetWallet.deposit(amount);
        System.out.println("Transferred " + amount + " to " + targetWallet.getUserId());
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

package com.leshka_and_friends.lgvb.core.admin;

import com.leshka_and_friends.lgvb.core.user.User;
import com.leshka_and_friends.lgvb.core.user.UserDAO;
import com.leshka_and_friends.lgvb.core.wallet.Wallet;
import com.leshka_and_friends.lgvb.core.wallet.WalletDAO;

public class AdminService {

    private final UserDAO userDAO;
    private final WalletDAO walletDAO;

    public AdminService(UserDAO userDAO, WalletDAO walletDAO) {
        this.userDAO = userDAO;
        this.walletDAO = walletDAO;
    }

    public User searchUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public Wallet getWalletByUserId(int userId) {
        return walletDAO.getWalletByUserId(userId);
    }

    public void updateUserWalletStatus(Wallet wallet, String status) {
        if (wallet != null) {
            wallet.setStatus(status);
            walletDAO.updateWallet(wallet);
        }
    }
}

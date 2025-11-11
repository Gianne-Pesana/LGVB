package com.leshka_and_friends.lgvb.core.admin;

import com.leshka_and_friends.lgvb.core.loan.Loan;
import com.leshka_and_friends.lgvb.core.loan.LoanDAO;
import com.leshka_and_friends.lgvb.core.user.User;
import com.leshka_and_friends.lgvb.core.user.UserDAO;
import com.leshka_and_friends.lgvb.core.wallet.Wallet;
import com.leshka_and_friends.lgvb.core.wallet.WalletDAO;

import java.util.ArrayList;
import java.util.List;

public class AdminService {

    private final UserDAO userDAO;
    private final WalletDAO walletDAO;
    private final LoanDAO loanDAO;

    public AdminService(UserDAO userDAO, WalletDAO walletDAO, LoanDAO loanDAO) {
        this.userDAO = userDAO;
        this.walletDAO = walletDAO;
        this.loanDAO = loanDAO;
    }

    public List<LoanWithUser> getAllLoanApplications() {
        List<Loan> pendingLoans = loanDAO.getPendingLoans();
        List<LoanWithUser> loanApplications = new ArrayList<>();

        for (Loan loan : pendingLoans) {
            Wallet wallet = walletDAO.getWalletById(loan.getWalletId());
            if (wallet != null) {
                User user = userDAO.getUserById(wallet.getUserId());
                if (user != null) {
                    LoanWithUser lwu = new LoanWithUser();
                    lwu.loanId = loan.getLoanId();
                    lwu.walletId = loan.getWalletId();
                    lwu.referenceNumber = loan.getReferenceNumber();
                    lwu.principal = loan.getPrincipal();
                    lwu.interestRate = loan.getInterestRate();
                    lwu.status = loan.getStatus();
                    lwu.createdAt = loan.getCreatedAt();
                    lwu.termInMonths = loan.getTermInMonths();
                    lwu.loanType = loan.getClass().getSimpleName().replace("Loan", "");
                    lwu.accountNumber = wallet.getAccountNumber();
                    lwu.email = user.getEmail();
                    lwu.firstName = user.getFirstName();
                    lwu.lastName = user.getLastName();
                    loanApplications.add(lwu);
                }
            }
        }
        return loanApplications;
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

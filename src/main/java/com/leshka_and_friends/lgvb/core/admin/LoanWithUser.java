package com.leshka_and_friends.lgvb.core.admin;

import java.sql.Timestamp;

public class LoanWithUser {
    // User fields
    public String email;
    public String firstName;
    public String lastName;

    // Wallet fields
    public String accountNumber;

    // Loan fields
    public int loanId;
    public int walletId;
    public String referenceNumber;
    public double principal;
    public double interestRate;
    public String status;
    public Timestamp createdAt;
    public int termInMonths;
    public String loanType;
}

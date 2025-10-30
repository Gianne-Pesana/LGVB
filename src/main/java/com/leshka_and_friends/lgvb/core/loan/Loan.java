package com.leshka_and_friends.lgvb.core.loan;

import java.sql.Timestamp;

public class Loan {
    private static final String ACTIVE = "active";
    private static final String PENDING = "pending";
    private static final String OVERDUE = "overdue";
    private static final String CLOSED = "closed";

    private int loanId;
    private int userId;
    private String referenceNumber;
    private double principal;
    private double remainingBalance;
    private double interestRate;
    private String status; // "PENDING", "ACTIVE", "OVERDUE", "CLOSED"
    private Timestamp createdAt;

}

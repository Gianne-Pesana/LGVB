/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.card;

import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.Date;

public class Card {
    private int cardId;
    private int accountId;
    private String cardNumber;
    private YearMonth expiryDate;
    private String cvv;
    private int cardTypeId;   // Use int instead of CardType
    private String status;       // "active", "blocked", "expired"
    private Timestamp issuedAt;

    public Card() {}

    public Card(int cardId, int accountId, String cardNumber, YearMonth expiryDate,
                String cvv, int cardTypeId, String status, Timestamp issuedAt) {
        this.cardId = cardId;
        this.accountId = accountId;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardTypeId = cardTypeId;
        this.status = status;
        this.issuedAt = issuedAt;
    }

    // --- getters & setters ---

    public int getCardId() { return cardId; }
    public void setCardId(int cardId) { this.cardId = cardId; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public YearMonth getExpiryDate() { return expiryDate; }
    public void setExpiryDate(YearMonth expiryDate) { this.expiryDate = expiryDate; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }

    public int getCardTypeId() { return cardTypeId; }
    public void setCardTypeId(int cardTypeId) { this.cardTypeId = cardTypeId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getIssuedAt() { return issuedAt; }
    public void setIssuedAt(Timestamp issuedAt) { this.issuedAt = issuedAt; }
}

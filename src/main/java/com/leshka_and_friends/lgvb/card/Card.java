/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.card;


public class Card {
    private int cardId;
    private int accountId;
    private String cardType;
    private String fullCardNumber;
    private String cardLast4;
    private int expiryMonth;
    private int expiryYear;
    private String status;

    // getters and setters

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    
    
    public int getCardId() { return cardId; }
    public void setCardId(int cardId) { this.cardId = cardId; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getFullCardNumber() { return fullCardNumber; }
    public void setFullCardNumber(String fullCardNumber) { this.fullCardNumber = fullCardNumber; }

    public String getCardLast4() { return cardLast4; }
    public void setCardLast4(String cardLast4) { this.cardLast4 = cardLast4; }

    public int getExpiryMonth() { return expiryMonth; }
    public void setExpiryMonth(int expiryMonth) { this.expiryMonth = expiryMonth; }

    public int getExpiryYear() { return expiryYear; }
    public void setExpiryYear(int expiryYear) { this.expiryYear = expiryYear; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public void printInfo() {
        System.out.println("cardId: " + (cardId < 1 ? "wala" : cardId));
        System.out.println("accountId: " + accountId);
        System.out.println("cardType: " + (cardType == null ? "wala pudt" : cardType));
        System.out.println("cardLast4: " + cardLast4);
        System.out.println("Expiry month: " + expiryMonth);
        System.out.println("Expiry year: " + expiryYear);
        System.out.println("status: " + cardId);
    }
}


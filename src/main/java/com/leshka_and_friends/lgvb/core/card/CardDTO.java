/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.core.card;

import java.time.YearMonth;

/**
 *
 * @author giann
 */
public class CardDTO {
    private String type;
    private String maskedNumber;
    private String holder;
    private YearMonth expiryDate;

    public CardDTO() {
    }

    public CardDTO(String type, String maskedNumber, String holder, YearMonth expiryDate) {
        this.type = type;
        setMaskedNumber(maskedNumber);
        this.holder = holder;
        this.expiryDate = expiryDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaskedNumber() {
        return maskedNumber;
    }

    public void setMaskedNumber(String cardLast4) {
        if (cardLast4 == null || cardLast4.isBlank()) {
            this.maskedNumber = "#".repeat(16);
        }

        this.maskedNumber = "*".repeat(12) + cardLast4;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public YearMonth getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(int year, int month) {
        this.expiryDate = YearMonth.of(year, month);
    }

}

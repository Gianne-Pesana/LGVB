/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.dto;

import com.leshka_and_friends.lgvb.model.CardType;
import java.time.YearMonth;

/**
 *
 * @author giann
 */
public class CardDTO {

    private CardType type;
    private String maskedNumber;
    private String holder;
    private YearMonth expiryDate;

    public CardDTO() {
    }

    public CardDTO(CardType type, String maskedNumber, String holder, YearMonth expiryDate) {
        this.type = type;
        setMaskedNumber(maskedNumber);
        this.holder = holder;
        this.expiryDate = expiryDate;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getMaskedNumber() {
        return maskedNumber;
    }

    public void setMaskedNumber(String raw) {
        if (raw == null || raw.isBlank()) {
            this.maskedNumber = "#".repeat(16);
        }

        this.maskedNumber = "*".repeat(raw.length() - 4) + raw.substring(raw.length() - 4);
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

    public void setExpiryDate(YearMonth expiryDate) {
        this.expiryDate = expiryDate;
    }

}

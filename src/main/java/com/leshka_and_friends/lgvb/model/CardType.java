/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.leshka_and_friends.lgvb.model;

/**
 *
 * @author giann
 */

public class CardType {
    private int cardTypeID;     // matches card_type_id in DB
    private String name;

    public CardType() {}

    public CardType(int cardTypeID, String name) {
        this.cardTypeID = cardTypeID;
        this.name = name;
    }

    public int getCardTypeID() {
        return cardTypeID;
    }

    public void setCardTypeID(int cardTypeID) {
        this.cardTypeID = cardTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

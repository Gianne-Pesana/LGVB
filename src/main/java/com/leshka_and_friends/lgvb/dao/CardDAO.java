/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.dao;

import com.leshka_and_friends.lgvb.model.Card;
import java.util.List;

public interface CardDAO {

    Card getCardById(int cardId);

    List<Card> getCardsByAccountId(int accountId);

    boolean addCard(Card card);

    boolean updateCard(Card card);

    boolean deleteCard(int cardId);
}

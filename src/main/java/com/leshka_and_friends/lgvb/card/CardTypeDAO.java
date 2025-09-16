package com.leshka_and_friends.lgvb.card;

import com.leshka_and_friends.lgvb.card.CardType;

import java.util.List;

public interface CardTypeDAO {
    CardType findById(int id);
    List<CardType> getAll();
}
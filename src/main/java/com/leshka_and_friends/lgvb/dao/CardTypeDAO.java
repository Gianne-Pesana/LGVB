package com.leshka_and_friends.lgvb.dao;

import com.leshka_and_friends.lgvb.model.CardType;

import java.util.List;

public interface CardTypeDAO {
    CardType findById(int id);
    List<CardType> getAll();
}
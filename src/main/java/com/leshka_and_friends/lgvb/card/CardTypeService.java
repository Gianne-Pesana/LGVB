package com.leshka_and_friends.lgvb.card;

import com.leshka_and_friends.lgvb.card.CardTypeDAO;
import com.leshka_and_friends.lgvb.card.CardType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CardTypeService {

    private final CardTypeDAO cardTypeDAO;
    private final Map<Integer, CardType> cardTypeCache;

    public CardTypeService(CardTypeDAO cardTypeDAO) {
        this.cardTypeDAO = cardTypeDAO;
        this.cardTypeCache = new ConcurrentHashMap<>();
        loadCardTypesIntoCache();
    }

    private void loadCardTypesIntoCache() {
        List<CardType> cardTypes = cardTypeDAO.getAll();
        cardTypeCache.putAll(cardTypes.stream()
                .collect(Collectors.toMap(CardType::getCardTypeID, Function.identity())));
    }

    public CardType getCardTypeById(int id) {
        return cardTypeCache.get(id);
    }

    public void refreshCache() {
        cardTypeCache.clear();
        loadCardTypesIntoCache();
    }
}
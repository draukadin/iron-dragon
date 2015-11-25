package com.pphi.iron.dragon.component.deck;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ForemanDeck.class),
        @JsonSubTypes.Type(value = ShipDeck.class),
        @JsonSubTypes.Type(value = DemandDeck.class)
})
public abstract class Deck<T> {

    @JsonProperty("deck")
    public final List<T> deck;

    protected Deck() {
        deck = newArrayList();
    }

    public T getRandomCard() {
        int randomIndex = new Random().nextInt(deck.size());
        return deck.remove(randomIndex);
    }

    protected abstract void discard(T card);
    protected abstract List<T> combineDiscardPileWithDeck();
    protected abstract T getCardFromDiscardPile();
    protected abstract DiscardPile<T> getDiscardPile();

    protected int size() {
        return deck.size();
    }
}

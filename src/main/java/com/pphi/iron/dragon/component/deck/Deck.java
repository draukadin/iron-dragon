package com.pphi.iron.dragon.component.deck;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ForemanDeck.class)
})
public abstract class Deck<T> {

    @JsonProperty("deck")
    protected final List<T> deck;

    protected Deck() {
        deck = newArrayList();
    }

    protected T getRandomCard() {
        int randomIndex = new Random().nextInt(deck.size());
        return deck.remove(randomIndex);
    }

    protected int size() {
        return deck.size();
    }
}

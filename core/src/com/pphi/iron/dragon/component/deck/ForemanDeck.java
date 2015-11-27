package com.pphi.iron.dragon.component.deck;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pphi.iron.dragon.component.card.foreman.Foreman;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class ForemanDeck extends Deck<Foreman> {

    private final ForemanDiscardPile foremanDiscardPile;

    public ForemanDeck() {
        super();
        foremanDiscardPile = new ForemanDiscardPile();
    }

    @Override
    @JsonIgnore
    public DiscardPile<Foreman> getDiscardPile() {
        return foremanDiscardPile;
    }

    @Override
    public void discard(Foreman card) {
        foremanDiscardPile.addCardToDiscardPile(card);
    }

    @Override
    @JsonIgnore
    public Foreman getCardFromDiscardPile() {
        return foremanDiscardPile.getCardFromTopOfPile();
    }

    @Override
    @JsonIgnore
    public List<Foreman> combineDiscardPileWithDeck() {
        deck.addAll(foremanDiscardPile.getDiscardPile());
        foremanDiscardPile.emptyDiscardPile();
        return deck;
    }

    public List<Foreman> getEntireDeck() {
        return deck;
    }

    public Foreman chooseCardFromDeck(int index) {
        return deck.remove(index);
    }
}

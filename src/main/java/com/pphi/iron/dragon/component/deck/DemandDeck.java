package com.pphi.iron.dragon.component.deck;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pphi.iron.dragon.component.card.demand.DemandCard;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class DemandDeck extends Deck<DemandCard> {

    private final DemandDiscardPile demandDiscardPile;

    public DemandDeck() {
        super();
        demandDiscardPile = new DemandDiscardPile();
    }

    @Override
    protected void discard(DemandCard card) {
        demandDiscardPile.addCardToDiscardPile(card);
    }

    @Override
    @JsonIgnore
    protected List<DemandCard> combineDiscardPileWithDeck() {
        deck.addAll(demandDiscardPile.getDiscardPile());
        demandDiscardPile.emptyDiscardPile();
        return deck;
    }

    @Override
    @JsonIgnore
    protected DemandCard getCardFromDiscardPile() {
        return demandDiscardPile.getCardFromTopOfPile();
    }

    @Override
    @JsonIgnore
    public DiscardPile<DemandCard> getDiscardPile() {
        return demandDiscardPile;
    }
}

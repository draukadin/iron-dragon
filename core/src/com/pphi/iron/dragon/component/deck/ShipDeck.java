package com.pphi.iron.dragon.component.deck;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pphi.iron.dragon.component.card.ship.ShipCard;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class ShipDeck extends Deck<ShipCard> {

    private final ShipDiscardPile shipDiscardPile;

    public ShipDeck() {
        super();
        shipDiscardPile = new ShipDiscardPile();
    }

    @Override
    @JsonIgnore
    public DiscardPile<ShipCard> getDiscardPile() {
        return shipDiscardPile;
    }

    @Override
    public void discard(ShipCard card) {
        shipDiscardPile.addCardToDiscardPile(card);
    }

    @Override
    @JsonIgnore
    public List<ShipCard> combineDiscardPileWithDeck() {
        deck.addAll(shipDiscardPile.getDiscardPile());
        shipDiscardPile.emptyDiscardPile();
        return deck;
    }

    @Override
    @JsonIgnore
    protected ShipCard getCardFromDiscardPile() {
        throw new UnsupportedOperationException("That action is not allowed");
    }
}

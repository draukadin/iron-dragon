package com.pphi.iron.dragon.component.deck;

import com.pphi.iron.dragon.component.card.ship.ShipCard;

public class ShipDiscardPile extends DiscardPile<ShipCard> {

    public ShipDiscardPile() {
        super();
    }

    @Override
    protected ShipCard getCardFromTopOfPile() {
        throw new UnsupportedOperationException("The rules do not allow you to obtain a ship from the discard pile");
    }
}

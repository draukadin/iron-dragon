package com.pphi.iron.dragon.component.deck;

import com.pphi.iron.dragon.component.card.foreman.Foreman;
import com.pphi.iron.dragon.exceptions.EmptyDiscardPileException;

public class ForemanDiscardPile extends DiscardPile<Foreman> {

    public ForemanDiscardPile() {
        super();
    }

    @Override
    protected Foreman getCardFromTopOfPile() {
        if (discardPile.size() == 0) {
            throw new EmptyDiscardPileException("There are no Foreman Cards in the discard pile");
        }
        return super.getCardFromTopOfPile();
    }
}

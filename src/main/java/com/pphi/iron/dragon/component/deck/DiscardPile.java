package com.pphi.iron.dragon.component.deck;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.pphi.iron.dragon.exceptions.EmptyDiscardPileException;

public class DiscardPile<T> {

    protected List<T> discardPile;

    protected DiscardPile() {
        discardPile = newArrayList();
    }

    protected void addCardToDiscardPile(T card) {
        discardPile.add(card);
    }

    protected T getCardFromTopOfPile() {
        if (discardPile.size() == 0) {
            throw new EmptyDiscardPileException();
        }
        int top = discardPile.size() - 1;
        return discardPile.remove(top);
    }

    protected List<T> getDiscardPile() {
        return discardPile;
    }

    protected void emptyDiscardPile() {
        discardPile = newArrayList();
    }
}

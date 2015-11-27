package com.pphi.iron.dragon.component.deck;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.pphi.iron.dragon.component.card.foreman.Foreman;
import com.pphi.iron.dragon.exceptions.EmptyDiscardPileException;
import com.pphi.iron.dragon.jackson.JacksonUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ForemanDeckTest {

    private static final Path PATH = Paths.get("GameData/ForemanData.json");
    private ForemanDeck foremanDeck;

    @BeforeMethod
    public void setUp() throws Exception {
        foremanDeck = JacksonUtil.deserialize(PATH, ForemanDeck.class);
    }

    @Test
    public void deckSizeTest() throws IOException {
        assertEquals(foremanDeck.size(), 13);
    }

    @Test
    public void getRandomCard() throws IOException {
        foremanDeck.getRandomCard();
        assertEquals(foremanDeck.size(), 12);
    }


    @Test
    public void discardForemanTest() throws IOException {
        Foreman foreman = foremanDeck.getRandomCard();
        assertFalse(foremanDeck.deck.contains(foreman));
        DiscardPile<Foreman> foremanDiscardPile = foremanDeck.getDiscardPile();
        foremanDeck.discard(foreman);
        assertEquals(foremanDiscardPile.getCardFromTopOfPile(), foreman);
    }

    @Test(expectedExceptions = EmptyDiscardPileException.class)
    public void getCardFromEmptyDiscardPile() throws IOException {
        DiscardPile<Foreman> foremanDiscardPile = foremanDeck.getDiscardPile();
        foremanDiscardPile.getCardFromTopOfPile();
    }

    @Test
    public void getCardFromDiscardPileTest() throws IOException {
        Foreman player1 = foremanDeck.getRandomCard();
        foremanDeck.discard(player1);
        assertEquals(foremanDeck.getDiscardPile().getDiscardPile().size(), 1);
        Foreman discardedForeman = foremanDeck.getCardFromDiscardPile();
        assertEquals(discardedForeman, player1);
        assertTrue(foremanDeck.getDiscardPile().getDiscardPile().isEmpty());
    }

    @Test
    public void combineDiscardPileWithDeckTest() throws IOException {
        Foreman player1 = foremanDeck.getRandomCard();
        Foreman player2 = foremanDeck.getRandomCard();
        Foreman player3 = foremanDeck.getRandomCard();
        Foreman player4 = foremanDeck.getRandomCard();
        Foreman player1Discard = player1;
        Foreman player2Discard = player2;
        Foreman player3Discard = player3;
        Foreman player4Discard = player4;
        assertEquals(foremanDeck.deck.size(), 9);
        foremanDeck.discard(player1);
        assertFalse(foremanDeck.deck.contains(player1));
        assertTrue(foremanDeck.getDiscardPile().getDiscardPile().contains(player1));
        foremanDeck.getRandomCard();
        foremanDeck.discard(player3);
        assertFalse(foremanDeck.deck.contains(player3));
        assertTrue(foremanDeck.getDiscardPile().getDiscardPile().contains(player3));
        foremanDeck.getRandomCard();
        foremanDeck.discard(player2);
        assertFalse(foremanDeck.deck.contains(player2));
        assertTrue(foremanDeck.getDiscardPile().getDiscardPile().contains(player2));
        foremanDeck.getRandomCard();
        foremanDeck.discard(player4);
        assertFalse(foremanDeck.deck.contains(player4));
        assertTrue(foremanDeck.getDiscardPile().getDiscardPile().contains(player4));
        foremanDeck.getRandomCard();
        assertEquals(foremanDeck.deck.size(), 5);
        foremanDeck.combineDiscardPileWithDeck();
        assertTrue(foremanDeck.getDiscardPile().getDiscardPile().isEmpty());
        assertEquals(foremanDeck.deck.size(), 9);
        assertTrue(foremanDeck.deck.contains(player1Discard));
        assertTrue(foremanDeck.deck.contains(player2Discard));
        assertTrue(foremanDeck.deck.contains(player3Discard));
        assertTrue(foremanDeck.deck.contains(player4Discard));
        assertEquals(foremanDeck.deck.size(), 9);
    }
}

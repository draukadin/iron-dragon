package com.pphi.iron.dragon.component.deck;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.pphi.iron.dragon.jackson.JacksonUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShipDeckTest {

    private static final Path PATH = Paths.get("../GameData/ShipData.json");
    private ShipDeck shipDeck;

    @BeforeMethod
    public void setUp() throws Exception {
        shipDeck = JacksonUtil.deserialize(PATH, ShipDeck.class);
    }

    @Test
    public void deckSizeTest() throws IOException {
        assertEquals(shipDeck.size(), 13);
    }

    @Test
    public void getRandomCard() throws IOException {
        shipDeck.getRandomCard();
        assertEquals(shipDeck.size(), 12);
    }
}

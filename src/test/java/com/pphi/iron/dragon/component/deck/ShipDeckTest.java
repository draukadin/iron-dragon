package com.pphi.iron.dragon.component.deck;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShipDeckTest {

    private static final Path PATH = Paths.get("GameData/ShipData.json");
    private ShipDeck shipDeck;

    @BeforeMethod
    public void setUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        shipDeck = objectMapper.readValue(PATH.toFile(), ShipDeck.class);
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

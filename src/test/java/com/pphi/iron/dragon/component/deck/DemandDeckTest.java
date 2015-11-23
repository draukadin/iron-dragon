package com.pphi.iron.dragon.component.deck;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DemandDeckTest {

    private ObjectMapper objectMapper;
    @BeforeTest
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createDeckTest() throws IOException {
        File file = new File("GameData/DemandDeckData.json");
        DemandDeck demandDeck = objectMapper.readValue(file, DemandDeck.class);
        assertEquals(demandDeck.size(), 146);
    }
}

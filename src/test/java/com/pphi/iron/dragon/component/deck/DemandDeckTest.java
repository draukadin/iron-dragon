package com.pphi.iron.dragon.component.deck;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.pphi.iron.dragon.jackson.JacksonUtil;
import org.testng.annotations.Test;

public class DemandDeckTest {

    @Test
    public void createDeckTest() throws IOException {
        Path file = Paths.get("GameData/DemandDeckData.json");
        DemandDeck demandDeck = JacksonUtil.deserialize(file, DemandDeck.class);
        assertEquals(demandDeck.size(), 146);
    }
}

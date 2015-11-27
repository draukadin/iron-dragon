package com.pphi.iron.dragon.component.deck;

import static org.testng.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.pphi.iron.dragon.component.card.train.TrainCard;
import com.pphi.iron.dragon.exceptions.TrainTypeNotAvailableException;
import com.pphi.iron.dragon.jackson.JacksonUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TrainDeckTest {

    private static final Path PATH = Paths.get("../GameData/TrainData.json");

    private TrainDeck trainDeck;

    @BeforeMethod
    public void setUp() throws Exception {
        trainDeck = JacksonUtil.deserialize(PATH, TrainDeck.class);
    }

    @Test
    public void numberOfTrainsInDeck() throws Exception {
        assertEquals(trainDeck.size(), 30);
    }

    @Test
    public void numberOfTeapotTrains() throws Exception {
        int count = 0;
        try {
            for (int i = 0; i < 7; i++) {
                trainDeck.getStartingTrain();
                count++;
            }
        } catch (IllegalStateException ex) {
            assertEquals(count, 6);
        }
    }

    @DataProvider(name = "trains")
    public static Object[][] trainsProvider() throws Exception {
        return new Object[][] {
                {"Sardar", 3},
                {"Salamander", 3},
                {"Fire Drake", 3},
                {"White Dragon", 3},
                {"Red Dragon", 3},
                {"Black Dragon", 3},
                {"Iron Dragon", 6}
        };
    }

    @Test(dataProvider = "trains")
    public void numberOfTrains(String trainName, int expectedCount) throws Exception {
        TrainCard trainCard = trainDeck.getStartingTrain();
        validateTrainCount(trainCard, trainName, expectedCount);
    }

    private void validateTrainCount(TrainCard trainCard, String newTrainName, int expectedCount) {
        int count = 0;
        try {
            for (int i = 0; i < 7; i++) {
                trainDeck.upgradeTrain(trainCard, newTrainName);
                count++;
            }
        } catch (IllegalStateException | TrainTypeNotAvailableException ex) {
            assertEquals(count, expectedCount);
        }
    }
}

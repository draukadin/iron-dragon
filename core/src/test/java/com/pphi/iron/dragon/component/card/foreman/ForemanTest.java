package com.pphi.iron.dragon.component.card.foreman;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import com.google.common.base.Optional;
import com.pphi.iron.dragon.component.TerrainType;
import com.pphi.iron.dragon.component.card.ship.ShipTypes;
import org.testng.annotations.Test;

public class ForemanTest {

    private Foreman foreman1 = new StandardForeman(1, "ELF_HALF_ELF", "Percy", "");
    private Foreman foreman2 = new StandardForeman(2, "DWARF", "Joe", "");
    private Foreman foreman3 = new StandardForeman(3, "CATMAN", "Lucy", "");
    private Foreman foreman4 = new WeeFolkForeman(4, "WEE_FOLK", "I'm on a boat", "Draw Three");
    private Foreman foreman5 = new HumanForeman(5, "HUMAN", "Joe", "Builds Bridges");
    private Foreman foreman6 = new HumanForeman(5, "HUMAN", "Joe", "Builds Bridges");
    private Foreman foreman7 = new HumanForeman(5, "HUMAN", "Joe", "Builds Bridges");
    private Foreman foreman8 = new HumanForeman(6, "HUMAN", "Bob", "Builds Bridges");
    private Foreman foreman9 = new UndergroundForeman(7, "TROLL", "", "");
    private Foreman foreman10 = new UndergroundForeman(8, "ORC", "", "");

    @Test
    public void playerCollectsBribesTest() {
        assertEquals(foreman1.playerCollectsBribes(), false);
        assertEquals(foreman2.playerCollectsBribes(), false);
        assertEquals(foreman3.playerCollectsBribes(), false);
        assertEquals(foreman4.playerCollectsBribes(), false);
        assertEquals(foreman5.playerCollectsBribes(), false);
        assertEquals(foreman9.playerCollectsBribes(), false);
        assertEquals(foreman10.playerCollectsBribes(), true);
    }

    @Test
    public void playerCanBuildOnMainMapTest() {
        assertEquals(foreman1.playerCanBuildOnMainMap(), true);
        assertEquals(foreman2.playerCanBuildOnMainMap(), true);
        assertEquals(foreman3.playerCanBuildOnMainMap(), true);
        assertEquals(foreman4.playerCanBuildOnMainMap(), true);
        assertEquals(foreman5.playerCanBuildOnMainMap(), true);
        assertEquals(foreman9.playerCanBuildOnMainMap(), false);
        assertEquals(foreman10.playerCanBuildOnMainMap(), true);
    }

    @Test
    public void getUndergroundCostTest() {
        assertEquals(foreman1.getUnderGroundCost(TerrainType.PLAIN), 1);
        assertEquals(foreman2.getUnderGroundCost(TerrainType.PLAIN), 1);
        assertEquals(foreman3.getUnderGroundCost(TerrainType.PLAIN), 1);
        assertEquals(foreman4.getUnderGroundCost(TerrainType.PLAIN), 1);
        assertEquals(foreman5.getUnderGroundCost(TerrainType.PLAIN), 1);
        assertEquals(foreman9.getUnderGroundCost(TerrainType.PLAIN), 2);
        assertEquals(foreman10.getUnderGroundCost(TerrainType.PLAIN), 1);
        assertEquals(foreman9.getUnderGroundCost(TerrainType.UNDERGROUND_ROCK), 2);
        assertEquals(foreman10.getUnderGroundCost(TerrainType.UNDERGROUND_ROCK), 5);
    }

    @Test
    public void getTerrainTypeTest() {
        assertEquals(foreman1.getTerrainType().get(), TerrainType.FOREST);
        assertEquals(foreman2.getTerrainType().get(), TerrainType.MOUNTAIN);
        assertEquals(foreman3.getTerrainType().get(), TerrainType.JUNGLE);
        assertEquals(foreman4.getTerrainType(), Optional.absent());
        assertEquals(foreman5.getTerrainType(), Optional.absent());
        assertEquals(foreman9.getTerrainType(), Optional.absent());
        assertEquals(foreman10.getTerrainType(), Optional.absent());
    }

    @Test
    public void getRiverCrossingCostTest() {
        assertEquals(foreman1.getRiverCrossingCost(), 2);
        assertEquals(foreman2.getRiverCrossingCost(), 2);
        assertEquals(foreman3.getRiverCrossingCost(), 2);
        assertEquals(foreman4.getRiverCrossingCost(), 2);
        assertEquals(foreman5.getRiverCrossingCost(), 0);
        assertEquals(foreman9.getRiverCrossingCost(), 2);
        assertEquals(foreman10.getRiverCrossingCost(), 2);
    }

    @Test
    public void getOceanInletCrossingCost() {
        assertEquals(foreman1.getOceanInletCrossingCost(), 3);
        assertEquals(foreman2.getOceanInletCrossingCost(), 3);
        assertEquals(foreman3.getOceanInletCrossingCost(), 3);
        assertEquals(foreman4.getOceanInletCrossingCost(), 3);
        assertEquals(foreman5.getOceanInletCrossingCost(), 1);
        assertEquals(foreman9.getOceanInletCrossingCost(), 3);
        assertEquals(foreman10.getOceanInletCrossingCost(), 3);
    }

    @Test
    public void getBoardingFeeTest() {
        assertEquals(foreman1.getBoardingFee(ShipTypes.CLASS_6), 3);
        assertEquals(foreman2.getBoardingFee(ShipTypes.CLASS_6), 3);
        assertEquals(foreman3.getBoardingFee(ShipTypes.CLASS_6), 3);
        assertEquals(foreman4.getBoardingFee(ShipTypes.CLASS_6), 0);
        assertEquals(foreman5.getBoardingFee(ShipTypes.CLASS_6), 3);
        assertEquals(foreman6.getBoardingFee(ShipTypes.CLASS_6), 3);
        assertEquals(foreman10.getBoardingFee(ShipTypes.CLASS_6), 3);
    }

    @Test
    public void getDrawCardsTest() {
        assertEquals(foreman1.drawShipCards(), 1);
        assertEquals(foreman2.drawShipCards(), 1);
        assertEquals(foreman3.drawShipCards(), 1);
        assertEquals(foreman4.drawShipCards(), 3);
        assertEquals(foreman5.drawShipCards(), 1);
        assertEquals(foreman9.drawShipCards(), 1);
        assertEquals(foreman10.drawShipCards(), 1);
    }

    @Test
    public void getDescriptionTest() {
        assertEquals(foreman5.getDescription(), "Builds Bridges");
    }

    @Test
    public void getNameTest() {
        assertEquals(foreman5.getName(), "Joe");
    }

    @Test
    public void getNumberTest() {
        assertEquals(foreman5.getNumber(), 5);
    }

    @Test
    public void getRaceTest() {
        assertEquals(foreman5.getRace(), ForemanRace.HUMAN);
    }

    @Test
    public void getUnderGrounForemanTest() {
        assertEquals(foreman5.getUndergroundForeman(), false);
    }

    @Test
    public void toStringTest() {
        assertNotNull(foreman5.toString());
        assertTrue(foreman5.toString().length() > 0);
    }

    @Test
    public void reflexiveTest() {
        assertTrue(foreman5.equals(foreman5));
    }

    @Test
    public void symmetryTest() {
        assertTrue(foreman5.equals(foreman6));
        assertTrue(foreman6.equals(foreman5));
    }

    @Test
    public void nullabilityTest() {
        assertFalse(foreman5.equals(null));
    }

    @Test
    public void transitiveTest() {
        assertTrue(foreman5.equals(foreman6));
        assertTrue(foreman6.equals(foreman7));
        assertTrue(foreman7.equals(foreman5));
    }

    @Test
    public void nonEqualTest() {
        assertFalse(foreman5.equals(foreman8));
    }

    @Test
    public void hashCodeEqualTest() {
        assertEquals(foreman5.hashCode(), foreman6.hashCode());
    }

    @Test
    public void hashCodeNotEqualTest() {
        assertNotEquals(foreman5.hashCode(), foreman8.hashCode());
    }
}

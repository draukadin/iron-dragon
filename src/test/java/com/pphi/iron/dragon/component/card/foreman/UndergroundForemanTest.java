package com.pphi.iron.dragon.component.card.foreman;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import com.pphi.iron.dragon.component.TerrainType;
import org.testng.annotations.Test;

public class UndergroundForemanTest {

    private Foreman undergroundForeman1 = new UndergroundForeman(1, "ORC", "Thrall", "");
    private Foreman undergroundForeman2 = new UndergroundForeman(1, "ORC", "Thrall", "");
    private Foreman undergroundForeman3 = new UndergroundForeman(1, "ORC", "Thrall", "");
    private Foreman undergroundForeman4 = new UndergroundForeman(1, "TROLL", "Sojin", "");

    @Test
    public void getUnderGroundCostTest() {
        assertEquals(undergroundForeman1.getUnderGroundCost(TerrainType.UNDERGROUND_ROCK), 5);
        assertEquals(undergroundForeman4.getUnderGroundCost(TerrainType.UNDERGROUND_ROCK), 2);
    }

    @Test
    public void getBuildTracksTopSideTest() {
        assertEquals(undergroundForeman1.playerCanBuildOnMainMap(), true);
        assertEquals(undergroundForeman4.playerCanBuildOnMainMap(), false);
    }

    @Test
    public void getPlayerGetsBribesTest() {
        assertEquals(undergroundForeman1.playerCollectsBribes(), true);
        assertEquals(undergroundForeman4.playerCollectsBribes(), false);
    }

    @Test
    public void toStringTest() {
        assertNotNull(undergroundForeman1.toString());
        assertTrue(undergroundForeman1.toString().length() > 0);
    }

    @Test
    public void reflexiveTest() {
        assertTrue(undergroundForeman1.equals(undergroundForeman1));
    }

    @Test
    public void symmetryTest() {
        assertTrue(undergroundForeman1.equals(undergroundForeman2));
        assertTrue(undergroundForeman2.equals(undergroundForeman1));
    }

    @Test
    public void nullabilityTest() {
        assertFalse(undergroundForeman1.equals(null));
    }

    @Test
    public void transitiveTest() {
        assertTrue(undergroundForeman1.equals(undergroundForeman2));
        assertTrue(undergroundForeman2.equals(undergroundForeman3));
        assertTrue(undergroundForeman3.equals(undergroundForeman1));
    }

    @Test
    public void nonEqualTest() {
        assertFalse(undergroundForeman1.equals(undergroundForeman4));
    }

    @Test
    public void hashCodeEqualTest() {
        assertEquals(undergroundForeman1.hashCode(), undergroundForeman2.hashCode());
    }

    @Test
    public void hashCodeNotEqualTest() {
        assertNotEquals(undergroundForeman1.hashCode(), undergroundForeman4.hashCode());
    }
}

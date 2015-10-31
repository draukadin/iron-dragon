package com.pphi.iron.dragon.component;

import static org.testng.Assert.assertEquals;

import com.iron.dragon.exceptions.UnsupportedEnumException;
import com.pphi.iron.dragon.component.TerrainType;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TerrainTypeTest {

    @Test
    public void getPlainCost() {
        assertEquals(TerrainType.PLAIN.getBuildingCost(), 1);
    }

    @Test
    public void getDesertCost() {
        assertEquals(TerrainType.DESERT.getBuildingCost(), 1);
    }

    @Test
    public void getForestCost() {
        assertEquals(TerrainType.FOREST.getBuildingCost(), 2);
    }

    @Test
    public void getMountainCost() {
        assertEquals(TerrainType.MOUNTAIN.getBuildingCost(), 2);
    }

    @Test
    public void getJungleCost() {
        assertEquals(TerrainType.JUNGLE.getBuildingCost(), 3);
    }

    @Test
    public void getAlpineCost() {
        assertEquals(TerrainType.ALPINE.getBuildingCost(), 5);
    }

    @Test
    public void getVolcanoCost() {
        assertEquals(TerrainType.VOLCANO.getBuildingCost(), 5);
    }

    @Test
    public void getUndergroundRockCost() {
        assertEquals(TerrainType.UNDERGROUND_ROCK.getBuildingCost(), 5);
    }

    @Test
    public void getUndergroundEntranceCost() {
        assertEquals(TerrainType.UNDERGROUND_ENTRANCE.getBuildingCost(), 2);
    }

    @Test
    public void getPortCost() {
        assertEquals(TerrainType.PORT.getBuildingCost(), 2);
    }

    @Test
    public void getSmallCityCost() {
        assertEquals(TerrainType.SMALL.getBuildingCost(), 3);
    }

    @Test
    public void getMediumCityCost() {
        assertEquals(TerrainType.MEDIUM.getBuildingCost(), 3);
    }

    @Test
    public void getMajorCityCost() {
        assertEquals(TerrainType.MAJOR.getBuildingCost(), 5);
    }

    @Test
    public void getTerrainImageFileNameTest() {
        assertEquals(TerrainType.PORT.getImageFileName(), "port.png");
    }

    @Test(expectedExceptions = {UnsupportedEnumException.class})
    public void getInvalidTerrainType() {
        TerrainType.getTerrainType("xyz");
    }

    @Test
    public void getTerrainType() {
        Assert.assertEquals(TerrainType.getTerrainType("PLAIN"), TerrainType.PLAIN);
        Assert.assertEquals(TerrainType.getTerrainType("Plain"), TerrainType.PLAIN);
        Assert.assertEquals(TerrainType.getTerrainType("plain"), TerrainType.PLAIN);
    }
}

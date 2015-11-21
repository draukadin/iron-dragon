package com.pphi.iron.dragon.component;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import com.google.common.collect.ImmutableList;
import com.iron.dragon.exceptions.UnsupportedEnumException;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CityTests {

    @Test
    public void getCityNameTest() {
        assertEquals(City.getCity("wyrre"), City.WYRRE);
    }

    @Test(expectedExceptions = UnsupportedEnumException.class)
    public void getCityWithInvalidArgumentTest() {
        City.getCity("");
    }

    @Test
    public void toStringTest() {
        assertNotNull(City.WYRRE.toString());
        assertTrue(City.WYRRE.toString().length() > 0);
    }

    @Test
    public void getLocationTest() {
        Assert.assertEquals(City.KODANKYE.getCubeCoordinate(), new HexagonCubeCoordinate(-17, 41, -24));
    }

    @Test
    public void getCountryTest() {
        Assert.assertEquals(City.KODANKYE.getCountry(), Country.KOLAND);
    }

    @Test
    public void getAvailableLoadsTest() {
        Assert.assertEquals(City.KODANKYE.getAvailableLoads(), ImmutableList.of(Load.IVORY));
    }

    @Test
    public void getTerrainTypeTest() {
        Assert.assertEquals(City.KOLA.getTerrainType(), TerrainType.MAJOR);
    }
}

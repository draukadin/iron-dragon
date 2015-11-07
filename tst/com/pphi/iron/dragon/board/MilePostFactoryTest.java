package com.pphi.iron.dragon.board;

import com.pphi.iron.dragon.component.BasicMilePost;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.TerrainType;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class MilePostFactoryTest {

    private MilePostFactory milePostFactory;

    @BeforeMethod
    public void setUp() throws Exception {
        milePostFactory = new MilePostFactory();
    }

    @Test  //This test should pass once all the MilePosts have been created
    public void getMapCoordinatesTest() throws Exception {
        Set<HexagonCubeCoordinate> actual = milePostFactory.getMapCoordinates();
        assertEquals(actual.size(), 4813);
    }

    @Test
    public void createMilePostTest() throws Exception {
        HexagonCubeCoordinate cubeCoordinate1 = new HexagonCubeCoordinate(-19, -12, 31);
        HexagonCubeCoordinate cubeCoordinate2 = new HexagonCubeCoordinate(4, -23, 19);
        MilePost milePost1 = milePostFactory.createMilePost(cubeCoordinate1);
        MilePost milePost2 = milePostFactory.createMilePost(cubeCoordinate2);

        assertTrue(milePost1.getMilePost().isPresent());
        assertFalse(milePost1.getCityMilePost().isPresent());
        assertEquals(milePost1.getCubeCoordinate(), cubeCoordinate1);
        BasicMilePost basicMilePost = milePost1.getMilePost().get();
        assertEquals(basicMilePost.getTerrainType(), TerrainType.JUNGLE);

        assertTrue(milePost2.getMilePost().isPresent());
        assertTrue(milePost2.getCityMilePost().isPresent());
        assertEquals(milePost2.getCubeCoordinate(), cubeCoordinate2);
        City city = milePost2.getCityMilePost().get();
        assertEquals(city.getName(), "Wikkedde");
        assertEquals(city.getTerrainType(), TerrainType.MAJOR);
    }
}

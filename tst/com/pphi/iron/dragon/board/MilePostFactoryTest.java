package com.pphi.iron.dragon.board;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.iron.dragon.component.BasicMilePost;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.TerrainType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newTreeSet;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class MilePostFactoryTest {

    private MilePostFactory milePostFactory;

    @BeforeMethod
    public void setUp() throws Exception {
        milePostFactory = new MilePostFactory();
    }

    @Test  //This test should pass once all the MilePosts have been created
    public void getMapCoordinatesTest() throws Exception {
        Set<HexagonCubeCoordinate> mapCoordinates = milePostFactory.getMapCoordinates();
        Multimap<Integer, MilePost> milePostMap = ArrayListMultimap.create();
        for (HexagonCubeCoordinate cubeCoordinate : mapCoordinates) {
            milePostMap.putAll(cubeCoordinate.getZ(), milePostFactory.createMilePost(cubeCoordinate));
        }
        int count = 0;
        for (Map.Entry<Integer, Collection<MilePost>> entry : milePostMap.asMap().entrySet()) {
            int z = entry.getKey();
            Collection<MilePost> value = newTreeSet(entry.getValue());
            if (z % 2 == 0) {
                if (value.size() != 65) {
                    printRow(z, value, 65);
                    count++;
                }
            } else {
                if (value.size() != 66) {
                    printRow(z, value, 66);
                    count++;
                }
            }
        }
        System.out.println(String.format("%d errors found", count));
        if (count > 0) {
            fail("Rows without the correct number of mileposts were detected");
        }
    }

    private void printRow(int z, Collection<MilePost> milePosts, int expectedSize) {
        System.out.println(String.format("Row %d has %d entries.  Should have %d", z, milePosts.size(), expectedSize));
        List<MilePost> list = newArrayList(milePosts);
        int nextIndex = 0;
        for (int i = 0; i < list.size(); i++) {
            int currentX = list.get(i).getCubeCoordinate().getX();
            if (++nextIndex < list.size()) {
                int nextX = list.get(nextIndex).getCubeCoordinate().getX();
                if (!(nextX - currentX == 1)) {
                    System.out.println(String.format("Missing x: %d", currentX + 1));
                }
            }
        }
    }

    @Test
    public void createMilePostTest() throws Exception {
        HexagonCubeCoordinate cubeCoordinate1 = new HexagonCubeCoordinate(-19, -12, 31);
        HexagonCubeCoordinate cubeCoordinate2 = new HexagonCubeCoordinate(4, -23, 19);
        MilePost milePost1 = milePostFactory.createMilePost(cubeCoordinate1).iterator().next();
        MilePost milePost2 = milePostFactory.createMilePost(cubeCoordinate2).iterator().next();

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

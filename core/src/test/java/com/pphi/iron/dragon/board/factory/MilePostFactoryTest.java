package com.pphi.iron.dragon.board.factory;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Sets.newTreeSet;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.iron.dragon.board.model.MilePost;
import com.pphi.iron.dragon.component.BasicMilePost;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.Country;
import com.pphi.iron.dragon.component.TerrainType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MilePostFactoryTest {

    private static final int MAIN_MAP_EVEN_ROW_SIZE = 65;
    private static final int MAIN_MAP_ODD_ROW_SIZE = 66;
    private static final int MAIN_AND_SIDE_MAP_EVEN_ROW_SIZE = 82;
    private static final int MAIN_AND_SIDE_MAP_ODD_ROW_SIZE = 84;

    private MilePostFactory milePostFactory;
    private Set<Integer> undergrounMapRows;

    @BeforeMethod
    public void setUp() throws Exception {
        milePostFactory = new MilePostFactory();
        undergrounMapRows = newHashSet();
        for (int i = -11; i < 17; i++) {
            undergrounMapRows.add(i);
        }
    }

    @Test
    @Parameters({ "rowToPrint" })
    public void getMainMapCoordinatesTest(Integer rowToPrint) throws Exception {
        Set<HexagonCubeCoordinate> mapCoordinates = milePostFactory.getMapCoordinates();
        Multimap<Integer, MilePost> milePostMap = ArrayListMultimap.create();
        for (HexagonCubeCoordinate cubeCoordinate : mapCoordinates) {
            milePostMap.putAll(cubeCoordinate.getZ(), milePostFactory.createMilePost(cubeCoordinate, 15));
        }
        int count = 0;
        for (Map.Entry<Integer, Collection<MilePost>> entry : milePostMap.asMap().entrySet()) {
            int z = entry.getKey();
            int expectedRowSize;
            if (undergrounMapRows.contains(z)) {
                if (z % 2 == 0) {
                    expectedRowSize = MAIN_AND_SIDE_MAP_EVEN_ROW_SIZE;
                } else {
                    expectedRowSize = MAIN_AND_SIDE_MAP_ODD_ROW_SIZE;
                }
            } else {
                if (z % 2 == 0) {
                    expectedRowSize = MAIN_MAP_EVEN_ROW_SIZE;
                } else {
                    expectedRowSize = MAIN_MAP_ODD_ROW_SIZE;
                }
            }
            Collection<MilePost> value = newTreeSet(entry.getValue());
            if (z % 2 == 0) {
                if (value.size() != expectedRowSize) {
                    printRow(z, value, expectedRowSize);
                    count++;
                }
            } else {
                if (value.size() != expectedRowSize) {
                    printRow(z, value, expectedRowSize);
                    count++;
                }
            }
            if (rowToPrint != null && rowToPrint.equals(z)) {
                printMilePosts(entry.getValue());
            }
        }
        System.out.println(String.format("%d errors found", count));
        if (count > 0) {
            fail("Rows without the correct number of mileposts were detected");
        }
    }

    private void printMilePosts(Collection<MilePost> value) {
        List<MilePost> sortedList = newArrayList(value);
        Collections.sort(sortedList);
        for (MilePost milePost : sortedList) {
            System.out.println(milePost);
        }
    }

    @Test
    public void getSideMapTest() throws Exception {
        Set<HexagonCubeCoordinate> mapCoordinates = milePostFactory.getMapCoordinates();
        Multimap<Integer, MilePost> milePostMap = ArrayListMultimap.create();
        for (HexagonCubeCoordinate cubeCoordinate : mapCoordinates) {
            milePostMap.putAll(cubeCoordinate.getZ(), milePostFactory.createMilePost(cubeCoordinate, 15));
        }
        int count = 0;
        Multimap<Integer, MilePost> sideMapMilePosts = HashMultimap.create();
        for (Map.Entry<Integer, Collection<MilePost>> entry : milePostMap.asMap().entrySet()) {
            int z = entry.getKey();
            Collection<MilePost> value = entry.getValue();
            if (value.size() > 66) {
                for (MilePost milePost : value) {
                    if (milePost.getCountry() == Country.UNDERGROUND) {
                        sideMapMilePosts.put(z, milePost);
                    }
                }
            }
        }
        for (Map.Entry<Integer, Collection<MilePost>> entry : sideMapMilePosts.asMap().entrySet()) {
            int z = entry.getKey();
            Collection<MilePost> value = newTreeSet(entry.getValue());
            if (z % 2 == 0) {
                if (value.size() != 17) {
                    printRow(z, value, 17);
                    count++;
                }
            } else {
                if (value.size() != 18) {
                    printRow(z, value, 18);
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
        MilePost milePost1 = milePostFactory.createMilePost(cubeCoordinate1, 15).iterator().next();
        MilePost milePost2 = milePostFactory.createMilePost(cubeCoordinate2, 15).iterator().next();

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

    @Test
    public void validateEachRowOnMainMapHasCorrectNumberOfTerrainTypes() throws Exception {
        //Arrange
        Path expectedValuesPath = Paths.get("../src/test/resources/ExpectedValuesRowData.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(expectedValuesPath.toFile());
        Table<Integer, TerrainType, Integer> expectedValues = HashBasedTable.create();
        for (JsonNode childNode : node) {
            int z = childNode.get("z").asInt();
            for (TerrainType terrainType : TerrainType.values()) {
                int number = childNode.get(terrainType.name()).asInt();
                expectedValues.put(z, terrainType, number);
            }
        }

        //Act
        Set<HexagonCubeCoordinate> mapCoordinates = milePostFactory.getMapCoordinates();
        Multimap<Integer, MilePost> milePostMap = ArrayListMultimap.create();
        Table<Integer, TerrainType, Integer> actualValues = HashBasedTable.create();
        for (HexagonCubeCoordinate cubeCoordinate : mapCoordinates) {
            milePostMap.putAll(cubeCoordinate.getZ(), milePostFactory.createMilePost(cubeCoordinate, 15));
        }
        for (Map.Entry<Integer, Collection<MilePost>> entry : milePostMap.asMap().entrySet()) {
            int z = entry.getKey();
            for (TerrainType terrainType : TerrainType.values()) {
                actualValues.put(z, terrainType, 0);
            }
            Collection<MilePost> value = entry.getValue();
            for (MilePost milePost : value) {
                TerrainType terrainType = milePost.getTerrainType();
                actualValues.put(z, terrainType, actualValues.get(z, terrainType) + 1);
            }
        }

        //Assert
        SortedMap<Integer, String> errors = new TreeMap<>();
        for (Table.Cell<Integer, TerrainType, Integer> cell : expectedValues.cellSet()) {
            int row = cell.getRowKey();
            TerrainType terrainType = cell.getColumnKey();
            int expected = cell.getValue();
            int actual = actualValues.get(row, terrainType);
            try {
                assertEquals(actual, expected, String.format("On row %d for terrain type %s", row, terrainType));
            } catch (AssertionError ex) {
                errors.put(row, ex.getMessage());
            }
        }
        System.out.println(String.format("Number of Errors: %d", errors.size()));
        if (!errors.isEmpty()) {
            for (Map.Entry<Integer, String> entry : errors.entrySet()) {
                System.out.println(entry.getValue());
            }
            fail();
        }
    }
}

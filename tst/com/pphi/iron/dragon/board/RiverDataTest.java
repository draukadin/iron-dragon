package com.pphi.iron.dragon.board;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.hexagon.neighbors.PointyTopCubeNeighbors;
import com.pphi.hexagon.util.CoordinateUtil;
import edu.uci.ics.jung.graph.util.Pair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.pphi.iron.dragon.board.WaterCrossingType.RIVER;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class RiverDataTest {

    @Test
    public void allRiverDataEntered() throws Exception {
        //Arrange
        Map<String, Integer> expectedValues = getExpectedValues();

        //Act
        Multimap<String, Pair<HexagonCubeCoordinate>> actual = act();

        //Assert
        validate(expectedValues, actual);
    }

    private Map<String, Integer> getExpectedValues() throws Exception {
        Map<String, Integer> map = newHashMap();
        Path path = Paths.get("tstData/ExpectedNumberOfRiverCrossings.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodes = objectMapper.readTree(path.toFile());
        for (JsonNode riverData : nodes) {
            String riverName = riverData.get("riverName").asText();
            int crossingCount = riverData.get("crossingCount").asInt();
            map.put(riverName, crossingCount);
        }
        return map;
    }

    private Multimap<String, Pair<HexagonCubeCoordinate>> act() throws IOException {
        Path path = Paths.get("GameData/Rivers.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodes = objectMapper.readTree(path.toFile());
        Multimap<String, Pair<HexagonCubeCoordinate>> data = HashMultimap.create();
        for (JsonNode node : nodes) {
            String name = node.get("name").asText();
            WaterCrossingJson waterCrossing = new WaterCrossingJson(name, RIVER);
            for (JsonNode coordinate : node.get("coordinates")) {
                JsonNode origin = coordinate.get("origin");
                HexagonCubeCoordinate nearSide = createCubeCoordinate(origin);
                JsonNode crossings = coordinate.get("crossing");
                for (JsonNode crossing : crossings) {
                    PointyTopCubeNeighbors direction = PointyTopCubeNeighbors.valueOf(crossing.asText());
                    HexagonCubeCoordinate farSide = PointyTopCubeNeighbors.getNeighbor(nearSide, direction);
                    data.put(waterCrossing.getName(), new Pair<>(nearSide, farSide));
                }
            }
        }
        return data;
    }

    private HexagonCubeCoordinate createCubeCoordinate(JsonNode node) {
        int x = node.get("x").asInt();
        int z = node.get("z").asInt();
        int y = CoordinateUtil.solveForY(x, z);
        return new HexagonCubeCoordinate(x, y, z);
    }

    private void validate(Map<String, Integer> expectedValues,
                Multimap<String, Pair<HexagonCubeCoordinate>> actual) {
        List<String> errors = newArrayList();
        for (Map.Entry<String, Integer> entry : expectedValues.entrySet()) {
            String name = entry.getKey();
            int expectedCrossingCount = entry.getValue();
            try {
                String message = String.format("%s River does not have the correct number of crossings", name);
                assertEquals(actual.get(name).size(), expectedCrossingCount, message);
            } catch (AssertionError ex) {
                errors.add(ex.getMessage());
            }
        }
        if (!errors.isEmpty()) {
            for (String errorMessage : errors) {
                System.out.println(errorMessage);
            }
            fail(String.format("Found %d rivers with errors", errors.size()));
        }
    }
}

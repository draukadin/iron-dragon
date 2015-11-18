package com.pphi.iron.dragon.board;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.hexagon.neighbors.PointyTopCubeNeighbors;
import com.pphi.hexagon.util.CoordinateUtil;
import com.pphi.iron.dragon.component.Country;
import com.pphi.iron.dragon.component.TerrainType;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.pphi.iron.dragon.component.TerrainType.CITY_AND_PORT;
import static com.pphi.iron.dragon.component.TerrainType.PORT;
import static com.pphi.iron.dragon.component.TerrainType.SEA_POINT;

public class MilePostLinkFactory {

    private Table<HexagonCubeCoordinate, HexagonCubeCoordinate, WaterCrossingJson> waterCrossingTable;

    public MilePostLinkFactory() throws IOException {
        Table<HexagonCubeCoordinate, HexagonCubeCoordinate, WaterCrossingJson> builder =
            deserialize(Paths.get("GameData/Rivers.json"), WaterCrossingType.RIVER);
        builder.putAll(deserialize(Paths.get("GameData/OceanInlets.json"), WaterCrossingType.OCEAN_INLET));
        waterCrossingTable = builder;
    }

    public Optional<MilePostLink> createLink(MilePost src, MilePost dest) {
        TerrainType srcTerrainType = src.getTerrainType();
        TerrainType destTerrainType = dest.getTerrainType();
        Optional<MilePostLink> linkOptional = Optional.absent();
        MilePostLink.Builder builder = MilePostLink.builder();

        if (srcTerrainType == SEA_POINT && destTerrainType == SEA_POINT) {
            checkForBorderCrossing(src, dest, builder);
            linkOptional = builder.build();
        } else if (srcTerrainType != SEA_POINT && destTerrainType != SEA_POINT) {
            checkForBorderCrossing(src, dest, builder);
            checkForWaterCrossing(src, dest, builder);
            linkOptional = builder.build();
        } else if ((srcTerrainType == SEA_POINT && destTerrainType == PORT)
                || (srcTerrainType == SEA_POINT && destTerrainType == CITY_AND_PORT)
                || (srcTerrainType == PORT)
                || (srcTerrainType == CITY_AND_PORT)) {
            checkForBorderCrossing(src, dest, builder);
            checkForWaterCrossing(src, dest, builder);
            linkOptional = builder.build();
        }
        return linkOptional;
    }

    private void checkForWaterCrossing(MilePost src, MilePost dest, MilePostLink.Builder builder) {
        HexagonCubeCoordinate srcCoord = src.getCubeCoordinate();
        HexagonCubeCoordinate destCoord = dest.getCubeCoordinate();
        if (waterCrossingTable.contains(srcCoord, destCoord)) {
            WaterCrossingJson waterCrossing = waterCrossingTable.get(srcCoord, destCoord);
            WaterCrossingType waterCrossingType = waterCrossing.getWaterCrossingType();
            switch (waterCrossingType) {
                case RIVER:
                    builder.river(true);
                    break;
                case OCEAN_INLET:
                    builder.inlet(true);
                    break;
                default:
                    throw new IllegalStateException("Invalid water crossing type: " + waterCrossingType);
            }
        }
    }

    private void checkForBorderCrossing(MilePost src, MilePost dest, MilePostLink.Builder builder) {
        Country srcCountry = src.getCountry();
        Country destCountry = dest.getCountry();
        if (srcCountry != destCountry) {
            builder.border(true);
        }
    }

    private Table<HexagonCubeCoordinate, HexagonCubeCoordinate, WaterCrossingJson> deserialize(Path path,
            WaterCrossingType waterCrossingType) throws IOException {
        Table<HexagonCubeCoordinate, HexagonCubeCoordinate, WaterCrossingJson> table = HashBasedTable.create();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodes = objectMapper.readTree(path.toFile());
        for (JsonNode node : nodes) {
            String name = node.get("name").asText();
            WaterCrossingJson waterCrossing = new WaterCrossingJson(name, waterCrossingType);
            for (JsonNode coordinate : node.get("coordinates")) {
                JsonNode origin = coordinate.get("origin");
                HexagonCubeCoordinate nearSide = createCubeCoordinate(origin);
                JsonNode crossings = coordinate.get("crossing");
                for (JsonNode crossing : crossings) {
                    PointyTopCubeNeighbors direction = PointyTopCubeNeighbors.valueOf(crossing.asText());
                    HexagonCubeCoordinate farSide = PointyTopCubeNeighbors.getNeighbor(nearSide, direction);
                    table.put(nearSide, farSide, waterCrossing);
                }
            }
        }
        return table;
    }

    private HexagonCubeCoordinate createCubeCoordinate(JsonNode node) {
        int x = node.get("x").asInt();
        int z = node.get("z").asInt();
        int y = CoordinateUtil.solveForY(x, z);
        return new HexagonCubeCoordinate(x, y, z);
    }
}

package com.pphi.iron.dragon.board.factory;

import static com.google.common.collect.Lists.newArrayList;
import static com.pphi.iron.dragon.component.TerrainType.CITY_AND_PORT;
import static com.pphi.iron.dragon.component.TerrainType.MAJOR;
import static com.pphi.iron.dragon.component.TerrainType.PORT;
import static com.pphi.iron.dragon.component.TerrainType.SEA_POINT;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.hexagon.neighbors.PointyTopCubeNeighbors;
import com.pphi.hexagon.util.CoordinateUtil;
import com.pphi.iron.dragon.board.model.MilePost;
import com.pphi.iron.dragon.board.model.MilePostLink;
import com.pphi.iron.dragon.board.raw.WaterCrossingJson;
import com.pphi.iron.dragon.board.raw.WaterCrossingType;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.Country;
import com.pphi.iron.dragon.component.TerrainType;
import edu.uci.ics.jung.graph.Graph;

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
        MilePostLink.Builder builder = MilePostLink.builder(src, dest);

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

    private enum EdgeAction {
        CREATE, DESTROY
    }

    public void createMagicConnection(Graph<MilePost, MilePostLink> graph, City near, City far,
            MilePostFactory milePostFactory) {
        if (near.getTerrainType().equals(MAJOR) && far.getTerrainType().equals(MAJOR)) {
            List<HexagonCubeCoordinate> farSideCoordinates = getHexagonCubeCoordinates(near);
            List<HexagonCubeCoordinate> nearSideCoordinates = getHexagonCubeCoordinates(far);
            modifyGraph(graph, milePostFactory, farSideCoordinates, nearSideCoordinates, EdgeAction.CREATE);
        } else {
            //TODO Logging statement
        }
    }

    public void destroyMagicConnection(Graph<MilePost, MilePostLink> graph, City near, City far,
            MilePostFactory milePostFactory) {
        if (near.getTerrainType().equals(MAJOR) && far.getTerrainType().equals(MAJOR)) {
            List<HexagonCubeCoordinate> farSideCoordinates = getHexagonCubeCoordinates(near);
            List<HexagonCubeCoordinate> nearSideCoordinates = getHexagonCubeCoordinates(far);
            modifyGraph(graph, milePostFactory, farSideCoordinates, nearSideCoordinates, EdgeAction.DESTROY);
        } else {
            //TODO Logging statement
        }
    }

    private List<HexagonCubeCoordinate> getHexagonCubeCoordinates(City city) {
        HexagonCubeCoordinate cubeCoordinate = city.getCubeCoordinate();
        List<HexagonCubeCoordinate> farSideCoordinates = newArrayList();
        farSideCoordinates.addAll(PointyTopCubeNeighbors.getNeighbors(cubeCoordinate));
        farSideCoordinates.add(city.getCubeCoordinate());
        return farSideCoordinates;
    }

    private void modifyGraph(Graph<MilePost, MilePostLink> graph, MilePostFactory milePostFactory,
            List<HexagonCubeCoordinate> farSideCoordinates, List<HexagonCubeCoordinate> nearSideCoordinates,
            EdgeAction edgeAction) {
        for (HexagonCubeCoordinate nearSide : nearSideCoordinates) {
            for (HexagonCubeCoordinate farSide : farSideCoordinates) {
                MilePost src = getMilePost(milePostFactory, nearSide);
                MilePost dest = getMilePost(milePostFactory, farSide);
                MilePostLink hereToThere = MilePostLink.builder(src, dest).build().get();
                MilePostLink thereToHere = MilePostLink.builder(dest, src).build().get();
                switch (edgeAction) {
                    case CREATE:
                    graph.addEdge(hereToThere, src, dest);
                    graph.addEdge(thereToHere, dest, src);
                    break;
                default:
                    graph.removeEdge(hereToThere);
                    graph.removeEdge(thereToHere);
                }
            }
        }
    }

    private MilePost getMilePost(MilePostFactory milePostFactory, HexagonCubeCoordinate coordinate) {
        Collection<MilePost> mileposts = milePostFactory.createMilePost(coordinate);
        if (mileposts.size() == 1) {
            return mileposts.iterator().next();
        }
        throw new IllegalStateException(String.format("Expected to only be one mile post for %s.  Found %d",
                coordinate, mileposts.size()));
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

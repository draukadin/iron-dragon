package com.pphi.iron.dragon.board;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Multimap;
import com.pphi.iron.dragon.component.BasicMilePost;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.PortMilePost;
import com.pphi.iron.dragon.component.TerrainType;
import com.pphi.iron.dragon.util.CoordinateDeserializationUtil;
import edu.uci.ics.jung.graph.util.Pair;
import org.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import org.pphi.hexagon.neighbors.PointyTopCubeNeighbors;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

public class MilePostFactory {

    private Set<HexagonCubeCoordinate> mapCoordinates;

    private Map<HexagonCubeCoordinate, BasicMilePost> basicMilePosts;
    private Map<HexagonCubeCoordinate, City> cityMilePosts;
    private Map<HexagonCubeCoordinate, PortMilePost> portMilePosts;

    public MilePostFactory() throws IOException {
        mapCoordinates = newHashSet();
        basicMilePosts = newHashMap();
        buildMilePosts(Paths.get("GameData/AlpineMilePosts.json"), TerrainType.ALPINE);
        buildMilePosts(Paths.get("GameData/DesertMilePosts.json"), TerrainType.DESERT);
        buildMilePosts(Paths.get("GameData/ForestMilePosts.json"), TerrainType.FOREST);
        buildMilePosts(Paths.get("GameData/JungleMilePosts.json"), TerrainType.JUNGLE);
        buildMilePosts(Paths.get("GameData/MountainMilePosts.json"), TerrainType.MOUNTAIN);
        buildMilePosts(Paths.get("GameData/OceanMilePosts.json"), TerrainType.SEA_POINT);
        buildMilePosts(Paths.get("GameData/PlainMilePosts.json"), TerrainType.PLAIN);
        buildCityMilePosts();
        buildPortMilePosts();
    }

    private void buildMilePosts(Path path, TerrainType terrainType) throws IOException {
        Multimap<Integer, Pair<Integer>> zAndXCoords = CoordinateDeserializationUtil.getCoords(path);
        for (Map.Entry<Integer, Collection<Pair<Integer>>> entry : zAndXCoords.asMap().entrySet()) {
            int z = entry.getKey();
            for (Pair<Integer> xCords : entry.getValue()) {
                for (int i = xCords.getFirst(); i <= xCords.getSecond(); i++) {
                    BasicMilePost basicMilePost = new BasicMilePost(i, z, terrainType);
                    HexagonCubeCoordinate coord = basicMilePost.getCubeCoordinate();
                    mapCoordinates.add(coord);
                    basicMilePosts.put(coord, basicMilePost);
                }
            }
        }
    }

    private void buildPortMilePosts() throws IOException {
        Path path = Paths.get("GameData/PortMilePosts.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(path.toFile());
        portMilePosts = newHashMap();
        for (JsonNode childNode : node) {
            int x = childNode.get("x").asInt();
            int z = childNode.get("z").asInt();
            PortMilePost portMilePost = new PortMilePost(x, z);
            HexagonCubeCoordinate coord = portMilePost.getCubeCoordinate();
            mapCoordinates.add(coord);
            portMilePosts.put(coord, portMilePost);
        }
    }

    private void buildCityMilePosts() {
        cityMilePosts = newHashMap();
        for (City city : City.values()) {
            if (city.getTerrainType().equals(TerrainType.MAJOR)) {
                HexagonCubeCoordinate cityCenter = city.getCubeCoordinate();
                List<HexagonCubeCoordinate> neighbors = PointyTopCubeNeighbors.getNeighbors(cityCenter);
                mapCoordinates.add(cityCenter);
                mapCoordinates.addAll(neighbors);
                for (HexagonCubeCoordinate neighbor : neighbors) {
                    cityMilePosts.put(neighbor, city);
                }
            }
            cityMilePosts.put(city.getCubeCoordinate(), city);
        }
    }

    public Set<HexagonCubeCoordinate> getMapCoordinates() {
        return mapCoordinates;
    }

    public MilePost createMilePost(HexagonCubeCoordinate coordinate) {
        return MilePost
                .builder(coordinate)
                .milePost(basicMilePosts.get(coordinate))
                .cityMilePost(cityMilePosts.get(coordinate))
                .portMilePost(portMilePosts.get(coordinate))
                .build();
    }
}

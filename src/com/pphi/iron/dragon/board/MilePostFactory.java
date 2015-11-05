package com.pphi.iron.dragon.board;

import com.google.common.collect.Multimap;
import com.pphi.iron.dragon.component.BasicMilePost;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.Country;
import com.pphi.iron.dragon.component.TerrainType;
import com.pphi.iron.dragon.util.CoordinateDeserializationUtil;
import edu.uci.ics.jung.graph.util.Pair;
import org.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import org.pphi.hexagon.neighbors.PointyTopCubeNeighbors;

import javax.swing.*;
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

    private IconFactory iconFactory;

    public MilePostFactory() throws IOException {
        iconFactory = new IconFactory();
        mapCoordinates = newHashSet();
        basicMilePosts = newHashMap();
        buildMilePosts(Paths.get("GameData/AlpineMilePosts.json"), TerrainType.ALPINE);
        buildMilePosts(Paths.get("GameData/DesertMilePosts.json"), TerrainType.DESERT);
        buildMilePosts(Paths.get("GameData/ForestMilePosts.json"), TerrainType.FOREST);
        buildMilePosts(Paths.get("GameData/JungleMilePosts.json"), TerrainType.JUNGLE);
        buildMilePosts(Paths.get("GameData/MountainMilePosts.json"), TerrainType.MOUNTAIN);
        buildMilePosts(Paths.get("GameData/OceanMilePosts.json"), TerrainType.SEA_POINT);
        buildMilePosts(Paths.get("GameData/PlainMilePosts.json"), TerrainType.PLAIN);
        buildMilePosts(Paths.get("GameData/PortMilePosts.json"), TerrainType.PORT);
        buildCityMilePosts();
    }

    private void buildMilePosts(Path path, TerrainType terrainType) throws IOException {
        Multimap<Integer, MilePostJson> zAndXCoords = CoordinateDeserializationUtil.getCoords(path);
        for (Map.Entry<Integer, Collection<MilePostJson>> entry : zAndXCoords.asMap().entrySet()) {
            int z = entry.getKey();
            for (MilePostJson milePostJson : entry.getValue()) {
                Pair<Integer> xCords = milePostJson.getPair();
                Country country = milePostJson.getCountry();
                for (int i = xCords.getFirst(); i <= xCords.getSecond(); i++) {
                    BasicMilePost basicMilePost = new BasicMilePost(i, z, terrainType, country);
                    HexagonCubeCoordinate coord = basicMilePost.getCubeCoordinate();
                    mapCoordinates.add(coord);
                    basicMilePosts.put(coord, basicMilePost);
                }
            }
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
        BasicMilePost basicMilePost = basicMilePosts.get(coordinate);
        City city = cityMilePosts.get(coordinate);
        Country country;
        TerrainType terrainType;
        if (city != null && basicMilePost != null) {
            terrainType = TerrainType.CITY_AND_PORT;
            country = city.getCountry();
        } else if (city == null) {
            terrainType = basicMilePost.getTerrainType();
            country = basicMilePost.getCountry();
        } else {
            terrainType = city.getTerrainType();
            country = city.getCountry();
        }
        Icon icon = iconFactory.getIcon(terrainType);
        return MilePost
                .builder(coordinate)
                .milePost(basicMilePost)
                .cityMilePost(city)
                .icon(icon)
                .country(country)
                .build();
    }
}

package com.pphi.iron.dragon.board;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.hexagon.neighbors.PointyTopCubeNeighbors;
import com.pphi.iron.dragon.component.BasicMilePost;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.Country;
import com.pphi.iron.dragon.component.TerrainType;
import com.pphi.iron.dragon.util.CoordinateDeserializationUtil;
import edu.uci.ics.jung.graph.util.Pair;

import javax.swing.Icon;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.pphi.iron.dragon.component.Country.UNDERGROUND;

public class MilePostFactory {

    private Set<HexagonCubeCoordinate> mapCoordinates;

    private Multimap<HexagonCubeCoordinate, BasicMilePost> basicMilePostMultimap;
    private Map<HexagonCubeCoordinate, City> cityMilePosts;

    private IconFactory iconFactory;

    public MilePostFactory() throws IOException {
        iconFactory = new IconFactory();
        mapCoordinates = newHashSet();
        basicMilePostMultimap = ArrayListMultimap.create();
        buildMilePosts(Paths.get("GameData/AlpineMilePosts.json"), TerrainType.ALPINE);
        buildMilePosts(Paths.get("GameData/DesertMilePosts.json"), TerrainType.DESERT);
        buildMilePosts(Paths.get("GameData/ForestMilePosts.json"), TerrainType.FOREST);
        buildMilePosts(Paths.get("GameData/JungleMilePosts.json"), TerrainType.JUNGLE);
        buildMilePosts(Paths.get("GameData/MountainMilePosts.json"), TerrainType.MOUNTAIN);
        buildMilePosts(Paths.get("GameData/OceanMilePosts.json"), TerrainType.SEA_POINT);
        buildMilePosts(Paths.get("GameData/PlainMilePosts.json"), TerrainType.PLAIN);
        buildMilePosts(Paths.get("GameData/PortMilePosts.json"), TerrainType.PORT);
        buildMilePosts(Paths.get("GameData/UnderGroundEntranceMilePosts.json"), TerrainType.UNDERGROUND_ENTRANCE);
        buildMilePosts(Paths.get("GameData/VolcanoMilePosts.json"), TerrainType.VOLCANO);
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
                    basicMilePostMultimap.put(coord, basicMilePost);
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
            mapCoordinates.add(city.getCubeCoordinate());
            cityMilePosts.put(city.getCubeCoordinate(), city);
        }
    }

    public Set<HexagonCubeCoordinate> getMapCoordinates() {
        return mapCoordinates;
    }

    public Collection<MilePost> createMilePost(HexagonCubeCoordinate coordinate) {
        List<MilePost> milePosts = newArrayList();
        Collection<BasicMilePost> basicMilePosts = basicMilePostMultimap.get(coordinate);
        City city = cityMilePosts.get(coordinate);

        Country country;
        TerrainType terrainType;

        BasicMilePost mainMapBasicMilePost = null;
        BasicMilePost undergroundMapMilePost = null;

        for (BasicMilePost basicMilePost : basicMilePosts) {
            if (basicMilePost.getCountry().equals(UNDERGROUND)) {
                undergroundMapMilePost = basicMilePost;
            } else {
                mainMapBasicMilePost = basicMilePost;
            }
        }

        if (city != null && mainMapBasicMilePost != null) {
            terrainType = TerrainType.CITY_AND_PORT;
            country = city.getCountry();
        } else if (mainMapBasicMilePost != null) {
            terrainType = mainMapBasicMilePost.getTerrainType();
            country = mainMapBasicMilePost.getCountry();
        } else if (city != null) {
            terrainType = city.getTerrainType();
            country = city.getCountry();
        } else {
            throw new IllegalStateException("Could not meet the conditions to set the country and terrain " +
                    "type values: Both the City and basic mile post were null for coordinate " + coordinate.toString());
        }

        Icon icon = iconFactory.getIcon(terrainType);
        MilePost mainMapMilePost = MilePost
                .builder(coordinate)
                .terrainType(terrainType)
                .milePost(mainMapBasicMilePost)
                .cityMilePost(city)
                .icon(icon)
                .country(country)
                .build();
        milePosts.add(mainMapMilePost);

        if (undergroundMapMilePost != null || (city != null && city.getCountry().equals(UNDERGROUND))) {
            milePosts.add(createUndergroundMilePost(coordinate, undergroundMapMilePost, city));
        }
        return milePosts;
    }

    private MilePost createUndergroundMilePost(HexagonCubeCoordinate coordinate, BasicMilePost basicMilePost,
            City city) {
        TerrainType terrainType;

        if (basicMilePost != null && city == null) {
            terrainType = basicMilePost.getTerrainType();
        } else {
            terrainType = city.getTerrainType();
        }
        Icon icon = iconFactory.getIcon(terrainType);

        return MilePost
                .builder(coordinate)
                .terrainType(terrainType)
                .milePost(basicMilePost)
                .cityMilePost(city)
                .icon(icon)
                .country(UNDERGROUND)
                .build();
    }
}

package com.pphi.iron.dragon.board.factory;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static com.pphi.iron.dragon.component.Country.UNDERGROUND;

import javax.swing.Icon;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.hexagon.neighbors.PointyTopCubeNeighbors;
import com.pphi.hexagon.util.CoordinateUtil;
import com.pphi.iron.dragon.board.model.MilePost;
import com.pphi.iron.dragon.board.raw.MilePostJson;
import com.pphi.iron.dragon.component.BasicMilePost;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.Country;
import com.pphi.iron.dragon.component.TerrainType;
import com.pphi.iron.dragon.util.CoordinateDeserializationUtil;
import edu.uci.ics.jung.graph.util.Pair;

public class MilePostFactory {

    private static final int SHIFT_FACTOR = -30;

    private static final Logger LOGGER = Logger.getLogger("MilePostFactory");

    private Set<HexagonCubeCoordinate> mapCoordinates;

    private Multimap<HexagonCubeCoordinate, BasicMilePost> basicMilePostMultimap;
    private Multimap<HexagonCubeCoordinate, City> cityMilePosts;

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
        buildMilePosts(Paths.get("GameData/UnderGroundRockMilePosts.json"), TerrainType.UNDERGROUND_ROCK);
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
                    int x = i;
                    if (country.equals(UNDERGROUND)) {
                        x = i + SHIFT_FACTOR;
                    }
                    BasicMilePost basicMilePost = new BasicMilePost(x, z, terrainType, country);
                    HexagonCubeCoordinate coord = basicMilePost.getCubeCoordinate();
                    mapCoordinates.add(coord);
                    basicMilePostMultimap.put(coord, basicMilePost);
                }
            }
        }
    }

    private void buildCityMilePosts() {
        cityMilePosts = HashMultimap.create();
        for (City city : City.values()) {
            HexagonCubeCoordinate cityCenter;
            if (city.getCountry().equals(UNDERGROUND)) {
                cityCenter = shiftXAxisCoordinate(city.getCubeCoordinate());
            } else {
                cityCenter = city.getCubeCoordinate();
            }
            if (city.getTerrainType().equals(TerrainType.MAJOR)) {
                List<HexagonCubeCoordinate> neighbors = PointyTopCubeNeighbors.getNeighbors(cityCenter);
                mapCoordinates.add(cityCenter);
                mapCoordinates.addAll(neighbors);
                for (HexagonCubeCoordinate neighbor : neighbors) {
                    cityMilePosts.put(neighbor, city);
                }
            }
            mapCoordinates.add(cityCenter);
            cityMilePosts.put(cityCenter, city);
        }
    }

    Set<HexagonCubeCoordinate> getMapCoordinates() {
        return mapCoordinates;
    }

    public boolean isCoordinateValid(HexagonCubeCoordinate coordinate) {
        return mapCoordinates.contains(coordinate);
    }

    public Collection<MilePost> createMilePost(HexagonCubeCoordinate coordinate) {
        List<MilePost> milePosts = newArrayList();
        Collection<BasicMilePost> basicMilePosts = basicMilePostMultimap.get(coordinate);
        Collection<City> cities = cityMilePosts.get(coordinate);

        Country country = null;
        TerrainType terrainType = null;

        BasicMilePost mainMapBasicMilePost = null;
        BasicMilePost undergroundMapMilePost = null;
        City mainMapCity = null;
        City underGroundCity = null;

        for (BasicMilePost basicMilePost : basicMilePosts) {
            if (basicMilePost.getCountry().equals(UNDERGROUND)) {
                undergroundMapMilePost = basicMilePost;
            } else {
                mainMapBasicMilePost = basicMilePost;
            }
        }

        for (City city : cities) {
            if (city.getCountry().equals(UNDERGROUND)) {
                underGroundCity = city;
            } else {
                mainMapCity = city;
            }
        }
        if (mainMapCity != null && mainMapBasicMilePost != null) {
            terrainType = TerrainType.CITY_AND_PORT;
            country = mainMapCity.getCountry();
        } else if (mainMapBasicMilePost != null) {
            terrainType = mainMapBasicMilePost.getTerrainType();
            country = mainMapBasicMilePost.getCountry();
        } else if (mainMapCity != null) {
            terrainType = mainMapCity.getTerrainType();
            country = mainMapCity.getCountry();
        } else {
            String message = String.format("%s is not on the main map", coordinate);
            LOGGER.finest(message);
        }

        if (null != terrainType && null != country) {
            Icon icon = iconFactory.getIcon(terrainType);
            MilePost mainMapMilePost = MilePost
                    .builder(coordinate)
                    .terrainType(terrainType)
                    .milePost(mainMapBasicMilePost)
                    .cityMilePost(mainMapCity)
                    .icon(icon)
                    .country(country)
                    .build();
            milePosts.add(mainMapMilePost);

        }
        if (undergroundMapMilePost != null || (underGroundCity != null)) {
            milePosts.add(createUndergroundMilePost(coordinate, undergroundMapMilePost, underGroundCity));
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

    public HexagonCubeCoordinate shiftXAxisCoordinate(HexagonCubeCoordinate hexagonCubeCoordinate) {
        int x = hexagonCubeCoordinate.getX() + SHIFT_FACTOR;
        return createShiftedCoordinate(hexagonCubeCoordinate.getZ(), x);
    }

    public HexagonCubeCoordinate reverseShiftXAxisCoordinate(HexagonCubeCoordinate hexagonCubeCoordinate) {
        int x = hexagonCubeCoordinate.getX() - SHIFT_FACTOR;
        return createShiftedCoordinate(hexagonCubeCoordinate.getZ(), x);
    }

    private HexagonCubeCoordinate createShiftedCoordinate(int z, int x) {
        int y = CoordinateUtil.solveForY(x, z);
        HexagonCubeCoordinate shiftedCoord = new HexagonCubeCoordinate(x, y ,z);
        mapCoordinates.add(shiftedCoord);
        return shiftedCoord;
    }
}

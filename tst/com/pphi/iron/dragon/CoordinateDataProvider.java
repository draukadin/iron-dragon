package com.pphi.iron.dragon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.hexagon.util.CoordinateUtil;
import com.pphi.iron.dragon.board.MilePostJson;
import com.pphi.iron.dragon.util.CoordinateDeserializationUtil;
import edu.uci.ics.jung.graph.util.Pair;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

public class CoordinateDataProvider {

    private static final Path ALPINE_POINTS_PATH = Paths.get("GameData/AlpineMilePosts.json");
    private static final Path DESERT_POINTS_PATH = Paths.get("GameData/DesertMilePosts.json");
    private static final Path FOREST_POINTS_PATH = Paths.get("GameData/ForestMilePosts.json");
    private static final Path JUNGLE_POINTS_PATH = Paths.get("GameData/JungleMilePosts.json");
    private static final Path MOUNTAIN_POINTS_PATH = Paths.get("GameData/MountainMilePosts.json");
    private static final Path PLAIN_POINTS_PATH = Paths.get("GameData/PlainMilePosts.json");
    private static final Path PORTS_PATH = Paths.get("GameData/PortMilePosts.json");
    private static final Path SEA_POINTS_PATH = Paths.get("GameData/OceanMilePosts.json");
    private static final Path UNDERGROUND_ENTRANCE_POINTS_PATH = Paths.get("GameData/UndergroundEntranceMilePosts.json");
    private static final Path UNDERGROUND_POINTS_PATH = Paths.get("GameData/UndergroundRockMilePosts.json");
    private static final Path VOLCANO_POINTS_PATH = Paths.get("GameData/VolcanoMilePosts.json");

    @DataProvider(name = "portCoordinates")
    public static Object[][] portCoordinateDataProvider() throws IOException {
        return createCoordinates(PORTS_PATH);
    }

    @DataProvider(name = "seaPointCoordinates")
    public static Object[][] seaPointCoordinateDataProvider() throws IOException {
        return createCoordinates(SEA_POINTS_PATH);
    }

    @DataProvider(name = "landCoordinates")
    public static Object[][] landCoordinateDataProvider() throws IOException {
        return createCoordinates(
                ALPINE_POINTS_PATH,
                DESERT_POINTS_PATH,
                FOREST_POINTS_PATH,
                JUNGLE_POINTS_PATH,
                MOUNTAIN_POINTS_PATH,
                PLAIN_POINTS_PATH,
                PORTS_PATH,
                UNDERGROUND_ENTRANCE_POINTS_PATH,
                UNDERGROUND_POINTS_PATH,
                VOLCANO_POINTS_PATH);
    }

    @DataProvider(name = "undergroundEntranceCoordinates")
    public static Object[][] undergroundEntranceDataProvider() throws IOException {
        return createCoordinates(UNDERGROUND_ENTRANCE_POINTS_PATH);
    }

    private static Object[][] createCoordinates(Path... paths) throws IOException {
        List<HexagonCubeCoordinate> coordinates = newArrayList();
        Multimap<Integer, MilePostJson> milepostJsonMultimap = HashMultimap.create();
        for (Path path : paths) {
            milepostJsonMultimap.putAll(CoordinateDeserializationUtil.getCoords(path));
        }
        for (Map.Entry<Integer, Collection<MilePostJson>> entry : milepostJsonMultimap.asMap().entrySet()) {
            int z = entry.getKey();
            for (MilePostJson milePostJson : entry.getValue()) {
                Pair<Integer> pair = milePostJson.getPair();
                for (int x = pair.getFirst(); x <= pair.getSecond(); x++) {
                    int y = CoordinateUtil.solveForY(x, z);
                    coordinates.add(new HexagonCubeCoordinate(x, y ,z));
                }
            }
        }
        Object[][] data = new Object[coordinates.size()][1];
        for (int i = 0; i < coordinates.size(); i++) {
            data[i][0] = coordinates.get(i);
        }
        return data;
    }
}

package com.pphi.iron.dragon.component;

import static com.google.common.base.Preconditions.checkArgument;

import com.iron.dragon.exceptions.UnsupportedEnumException;

public enum TerrainType {
    PLAIN(1, "plain.png"),
    DESERT(1, "desert.png"),
    FOREST(2, "forest.png"),
    MOUNTAIN(2, "mountain.png"),
    JUNGLE(3, "jungle.png"),
    ALPINE(5, "alpine.png"),
    VOLCANO(5, "volcano.png"),
    UNDERGROUND_ROCK(5, "underground_rock.png"),
    UNDERGROUND_ENTRANCE(2, "underground_entrance.png"),
    PORT(2, "port.png"),
    SEA_POINT(0, "sea_point.png"),
    SMALL(3, "small_city.png"),
    MEDIUM(3, "medium_city.png"),
    MAJOR(5, "major_city.png"),
    CITY_AND_PORT(5, "port_in_major_city.png");

    private final int buildingCost;
    private final String imageFileName;

    TerrainType(int buildingCost, String imageFileName) {
        this.buildingCost = buildingCost;
        this.imageFileName = imageFileName;
    }

    public int getBuildingCost() {
        return buildingCost;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public static TerrainType getTerrainType(String terrainTypeString) {
        checkArgument(!terrainTypeString.isEmpty(), "Missing pipe in map data terrain type and country pair");
        for (TerrainType terrainType : TerrainType.values()) {
            if(terrainTypeString.equalsIgnoreCase(terrainType.toString())) {
                return terrainType;
            }
        }
        throw new UnsupportedEnumException("Terrain Type " + terrainTypeString + " is not supported");
    }
}

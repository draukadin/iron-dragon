package com.pphi.iron.dragon.component;

import com.google.common.base.Objects;
import org.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import org.pphi.hexagon.util.CoordinateUtil;

public class OceanMilePost {

    private TerrainType terrainType;
    private HexagonCubeCoordinate cubeCoordinate;

    public OceanMilePost(int x, int z) {
        terrainType = TerrainType.SEA_POINT;
        int y = CoordinateUtil.solveForY(x, z);
        cubeCoordinate = new HexagonCubeCoordinate(x, y, z);
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public HexagonCubeCoordinate getCubeCoordinate() {
        return cubeCoordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OceanMilePost oceanMilePost = (OceanMilePost) o;
        return Objects.equal(terrainType, oceanMilePost.terrainType) &&
                Objects.equal(cubeCoordinate, oceanMilePost.cubeCoordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(terrainType, cubeCoordinate);
    }
}

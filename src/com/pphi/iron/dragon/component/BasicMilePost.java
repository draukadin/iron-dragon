package com.pphi.iron.dragon.component;

import com.google.common.base.Objects;
import org.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import org.pphi.hexagon.util.CoordinateUtil;

public class BasicMilePost {

    private Country country;
    private TerrainType terrainType;
    private HexagonCubeCoordinate cubeCoordinate;

    public BasicMilePost(int x, int z, TerrainType terrainType, Country country) {
        this.country = country;
        this.terrainType = terrainType;
        int y = CoordinateUtil.solveForY(x, z);
        cubeCoordinate = new HexagonCubeCoordinate(x, y, z);
    }

    public Country getCountry() {
        return country;
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

        BasicMilePost that = (BasicMilePost) o;

        return Objects.equal(this.terrainType, that.terrainType) &&
                Objects.equal(this.cubeCoordinate, that.cubeCoordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(terrainType, cubeCoordinate);
    }
}

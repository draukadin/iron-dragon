package com.pphi.iron.dragon.component;

import org.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import org.pphi.hexagon.neighbors.PointyTopCubeNeighbors;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;

public class CityMilePosts {

    private Map<HexagonCubeCoordinate, City> cityMilePosts;

    public CityMilePosts() {
        cityMilePosts = newHashMap();
        for (City city : City.values()) {
            if (city.getTerrainType().equals(TerrainType.MAJOR)) {
                HexagonCubeCoordinate cityCenter = city.getCubeCoordinate();
                List<HexagonCubeCoordinate> neighbors = PointyTopCubeNeighbors.getNeighbors(cityCenter);
                for (HexagonCubeCoordinate neighbor : neighbors) {
                    cityMilePosts.put(neighbor, city);
                }
            }
            cityMilePosts.put(city.getCubeCoordinate(), city);
        }
    }

    public Set<HexagonCubeCoordinate> getCoordinates() {
        return cityMilePosts.keySet();
    }

    public City getMilePost(HexagonCubeCoordinate coordinate) {
        return cityMilePosts.get(coordinate);
    }
}

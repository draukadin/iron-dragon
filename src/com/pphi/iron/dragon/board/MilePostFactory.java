package com.pphi.iron.dragon.board;

import com.pphi.iron.dragon.component.CityMilePosts;
import com.pphi.iron.dragon.component.OceanMilePosts;
import com.pphi.iron.dragon.component.PortMilePosts;
import org.pphi.hexagon.coordinates.HexagonCubeCoordinate;

import java.io.IOException;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class MilePostFactory {

    private Set<HexagonCubeCoordinate> mapCoordinates;
    private OceanMilePosts oceanMilePosts;
    private PortMilePosts portMilePosts;
    private CityMilePosts cityMilePosts;

    public MilePostFactory() throws IOException {
        oceanMilePosts = new OceanMilePosts();
        portMilePosts = new PortMilePosts();
        cityMilePosts = new CityMilePosts();
        mapCoordinates = newHashSet();
        mapCoordinates.addAll(oceanMilePosts.getCoordinates());
        mapCoordinates.addAll(portMilePosts.getCoordinates());
        mapCoordinates.addAll(cityMilePosts.getCoordinates());
    }

    public Set<HexagonCubeCoordinate> getMapCoordinates() {
        return mapCoordinates;
    }

    public MilePost createMilePost(HexagonCubeCoordinate coordinate) {
        return MilePost
                .builder(coordinate)
                .oceanMilePost(oceanMilePosts.getMilePost(coordinate))
                .cityMilePost(cityMilePosts.getMilePost(coordinate))
                .portMilePost(portMilePosts.getMilePost(coordinate))
                .build();
    }
}

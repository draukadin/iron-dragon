package com.pphi.iron.dragon.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;

public abstract class HexagonCubeCoordinateMixIn extends HexagonCubeCoordinate {

    @JsonCreator
    HexagonCubeCoordinateMixIn(
            @JsonProperty("x") int x,
            @JsonProperty("y") int y,
            @JsonProperty("z") int z) {
        super(x, y, z);
    }
}

package com.pphi.iron.dragon.component.card.foreman;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pphi.iron.dragon.component.TerrainType;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class UndergroundForeman extends Foreman {

    @JsonCreator
    public UndergroundForeman(
            @JsonProperty("cardNumber") int number,
            @JsonProperty("race") String race,
            @JsonProperty("foremanName") String name,
            @JsonProperty("description") String description) {
        super(number, race, name, description);
        super.isUndergroundForeman = true;
    }

    @Override
    @JsonIgnore
    public boolean playerCollectsBribes() {
        switch (race) {
            case ORC:
                return true;
            default:
                return false;
        }
    }

    @Override
    @JsonIgnore
    public boolean playerCanBuildOnMainMap() {
        switch (race) {
            case TROLL:
                return false;
            default:
                return true;

        }
    }

    @Override
    @JsonIgnore
    public int getUnderGroundCost(TerrainType terrainType) {
        switch (race) {
            case TROLL:
                return 2;
            default:
                return terrainType.getBuildingCost();
        }
    }
}

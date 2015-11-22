package com.pphi.iron.dragon.component.card.foreman;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.Optional;
import com.pphi.iron.dragon.component.TerrainType;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class StandardForeman extends Foreman {

    @JsonCreator
    public StandardForeman(
            @JsonProperty("cardNumber") int number,
            @JsonProperty("race") String race,
            @JsonProperty("foremanName") String name,
            @JsonProperty("description") String description) {
        super(number, race, name, description);
    }

    @Override
    @JsonIgnore
    public Optional<TerrainType> getTerrainType() {
        TerrainType type = null;
        switch(race) {
            case ELF_HALF_ELF:
                type = TerrainType.FOREST;
                break;
            case DWARF:
                type = TerrainType.MOUNTAIN;
                break;
            case CATMAN:
                type = TerrainType.JUNGLE;
                break;
        }
        return Optional.fromNullable(type);
    }

    @Override
    @JsonIgnore
    public int getMilePostCost(TerrainType terrainType) {
        switch (race) {
            case ELF_HALF_ELF:
                if (terrainType.equals(TerrainType.FOREST)) {
                    break;
                }
                else {
                    return super.getMilePostCost(terrainType);
                }
            case DWARF:
                if (terrainType.equals(TerrainType.MOUNTAIN)) {
                    break;
                }
                else {
                    return super.getMilePostCost(terrainType);
                }
            case CATMAN:
                if (terrainType.equals(TerrainType.JUNGLE)) {
                    break;
                }
                else {
                    return super.getMilePostCost(terrainType);
                }
        }
        return 1;
    }
}

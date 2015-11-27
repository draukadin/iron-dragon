package com.pphi.iron.dragon.component.card.foreman;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.pphi.iron.dragon.component.TerrainType;
import com.pphi.iron.dragon.component.card.ship.ShipTypes;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HumanForeman.class),
        @JsonSubTypes.Type(value = StandardForeman.class),
        @JsonSubTypes.Type(value = UndergroundForeman.class),
        @JsonSubTypes.Type(value = WeeFolkForeman.class)
})
public abstract class Foreman {

    protected int number;
    protected ForemanRace race;
    protected String name;
    protected String description;
    protected boolean isUndergroundForeman;

    protected Foreman() {
        isUndergroundForeman = false;
    }

    protected Foreman(int number, String race, String name, String description) {
        this.number = number;
        this.name = name;
        this.race = ForemanRace.valueOf(race);
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public ForemanRace getRace() {
        return race;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getUndergroundForeman() {
        return isUndergroundForeman;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number, race, name, description, isUndergroundForeman);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Foreman other = (Foreman) obj;
        return Objects.equal(this.number, other.number)
                && Objects.equal(this.race, other.race)
                && Objects.equal(this.name, other.name)
                && Objects.equal(this.description, other.description)
                && Objects.equal(this.isUndergroundForeman, other.isUndergroundForeman);
    }

    public int drawShipCards() {
        return 1;
    }

    public boolean playerCanBuildOnMainMap() {
        return true;
    }

    public int getRiverCrossingCost() {
        return 2;
    }

    public int getBoardingFee(ShipTypes shipTypes) {
        return shipTypes.getCost();
    }

    public boolean playerCollectsBribes() {
        return false;
    }

    protected Optional<TerrainType> getTerrainType() {
        return Optional.absent();
    }

    public int getMilePostCost(TerrainType terrainType) {
        return terrainType.getBuildingCost();
    }

    public int getOceanInletCrossingCost() {
        return 3;
    }

    public int getUnderGroundCost(TerrainType terrainType) {
        return terrainType.getBuildingCost();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("number", number)
                .add("race", race)
                .add("name", name)
                .add("description", description)
                .add("isUndergroundForeman", isUndergroundForeman)
                .toString();
    }
}

package com.pphi.iron.dragon.component.card.ship;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class ShipCard {

    private String name;
    private ShipTypes shipType;

    @JsonCreator
    public ShipCard(
            @JsonProperty("name") String name,
            @JsonProperty("shipType") String shipType) {
        this.name = name;
        this.shipType = ShipTypes.valueOf(shipType);
    }

    public String getName() {
        return name;
    }

    public ShipTypes getShipType() {
        return shipType;
    }

    public int getShipSpeed() {
        return shipType.getSpeed();
    }

    public int getShipCost() {
        return shipType.getCost();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("shipType", shipType)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, shipType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ShipCard other = (ShipCard) obj;
        return Objects.equal(this.name, other.name)
                && Objects.equal(this.shipType, other.shipType);
    }
}

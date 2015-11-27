package com.pphi.iron.dragon.component.card.foreman;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class HumanForeman extends Foreman {

    @JsonCreator
    public HumanForeman(
            @JsonProperty("cardNumber") int number,
            @JsonProperty("race") String race,
            @JsonProperty("foremanName") String name,
            @JsonProperty("description") String description) {
        super(number, race, name, description);
    }

    @Override
    @JsonIgnore
    public int getRiverCrossingCost() {
        return 0;
    }

    @Override
    @JsonIgnore
    public int getOceanInletCrossingCost() {
        return 1;
    }
}

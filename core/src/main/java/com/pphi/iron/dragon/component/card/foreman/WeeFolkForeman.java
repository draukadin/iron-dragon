package com.pphi.iron.dragon.component.card.foreman;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pphi.iron.dragon.component.card.ship.ShipTypes;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class WeeFolkForeman extends Foreman {

    @JsonCreator
    public WeeFolkForeman(
            @JsonProperty("cardNumber") int number,
            @JsonProperty("race") String race,
            @JsonProperty("foremanName") String name,
            @JsonProperty("description") String description) {
        super(number, race, name, description);
    }

    @Override
    @JsonIgnore
    public int drawShipCards() {
        return 3;
    }

    @Override
    @JsonIgnore
    public int getBoardingFee(ShipTypes shipTypes) {
        return 0;
    }
}

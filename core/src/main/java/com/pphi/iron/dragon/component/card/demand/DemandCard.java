package com.pphi.iron.dragon.component.card.demand;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ContractCard.class),
        @JsonSubTypes.Type(value = EventCard.class)
})
public abstract class DemandCard {

    protected int cardNumber;

    protected DemandCard(int cardNumber) {
        this.cardNumber = cardNumber;
    }
}

package com.pphi.iron.dragon.component.card.demand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class EventCard extends DemandCard {

    private String title;
    private String description;

    @JsonCreator
    public EventCard(@JsonProperty("cardNumber") int cardNumber,
                     @JsonProperty("title") String title,
                     @JsonProperty("description") String description) {
        super(cardNumber);
        this.title = title;
        this.description = description;
    }

    @JsonProperty("cardNumber")
    public int getCardNumber() {
        return cardNumber;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cardNumber", cardNumber)
                .add("title", title)
                .add("description", description)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventCard that = (EventCard) o;

        return Objects.equal(this.title, that.title)
                && Objects.equal(this.description, that.description)
                && Objects.equal(this.cardNumber, that.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, description, cardNumber);
    }
}

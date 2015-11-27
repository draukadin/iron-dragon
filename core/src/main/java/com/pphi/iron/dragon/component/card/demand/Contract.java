package com.pphi.iron.dragon.component.card.demand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.Load;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Contract {

    private final City city;
    private final Load load;
    private final int payment;

    @JsonCreator
    public Contract(
            @JsonProperty("city") String city,
            @JsonProperty("load") Load load,
            @JsonProperty("payment") int payment) {
        this.city = City.valueOf(city);
        this.load = load;
        this.payment = payment;
    }

    public Contract(City city, Load load, int payment) {
        this.city = city;
        this.load = load;
        this.payment = payment;
    }

    @JsonProperty("city")
    public City getCity() {
        return city;
    }

    @JsonProperty("load")
    public Load getLoad() {
        return load;
    }

    @JsonProperty("payment")
    public int getPayment() {
        return payment;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("city", city)
                .add("load", load)
                .add("payment", payment)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(city, load, payment);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Contract other = (Contract) obj;
        return Objects.equal(this.city, other.city)
                && Objects.equal(this.load, other.load)
                && Objects.equal(this.payment, other.payment);
    }
}

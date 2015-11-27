package com.pphi.iron.dragon.component.card.train;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class TrainsSpecifications {

    private final int level;
    private final int speed;
    private final int capacity;

    @JsonCreator
    public TrainsSpecifications(@JsonProperty("level") int level,
            @JsonProperty("speed") int speed,
            @JsonProperty("capacity") int capacity) {
        this.level = level;
        this.speed = speed;
        this.capacity = capacity;
    }

    @JsonProperty("level")
    public int getLevel() {
        return level;
    }

    @JsonProperty("speed")
    public int getSpeed() {
        return speed;
    }

    @JsonProperty("capacity")
    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("level", level)
                .add("speed", speed)
                .add("capacity", capacity)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(level, speed, capacity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TrainsSpecifications other = (TrainsSpecifications) obj;
        return Objects.equal(this.level, other.level)
                && Objects.equal(this.speed, other.speed)
                && Objects.equal(this.capacity, other.capacity);
    }
}

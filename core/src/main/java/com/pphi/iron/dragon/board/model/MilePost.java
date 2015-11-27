package com.pphi.iron.dragon.board.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ComparisonChain;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.iron.dragon.component.BasicMilePost;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.Country;
import com.pphi.iron.dragon.component.TerrainType;

public class MilePost implements Comparable<MilePost> {

    private HexagonCubeCoordinate cubeCoordinate;
    private Optional<City> cityMilePost;
    private Optional<BasicMilePost> milePost;
    private TerrainType terrainType;
    private Country country;
    private int radius;

    @JsonCreator
    public MilePost(@JsonProperty("cubeCoordinate") HexagonCubeCoordinate cubeCoordinate,
                    @JsonProperty("cityMilePost") String cityMilePost,
                    @JsonProperty("terrainType") String terrainType,
                    @JsonProperty("country") String country,
                    @JsonProperty("radius") int radius) {
        this.cubeCoordinate = cubeCoordinate;
        if (cityMilePost == null) {
            this.cityMilePost = Optional.absent();
        } else {
            this.cityMilePost = Optional.fromNullable(City.valueOf(cityMilePost));
        }
        this.terrainType = TerrainType.valueOf(terrainType);
        this.country = Country.valueOf(country);
        this.radius = radius;
    }

    private MilePost(Builder builder) {
        cubeCoordinate = builder.cubeCoordinate;
        cityMilePost = builder.cityMilePost;
        milePost = builder.milePost;
        country = builder.country;
        terrainType = builder.terrainType;
        radius = builder.radius;
    }

    public HexagonCubeCoordinate getCubeCoordinate() {
        return cubeCoordinate;
    }

    public Optional<City> getCityMilePost() {
        return cityMilePost;
    }

    @JsonIgnore
    public Optional<BasicMilePost> getMilePost() {
        return milePost;
    }

    public Country getCountry() {
        return country;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MilePost milePost = (MilePost) o;
        return Objects.equal(cubeCoordinate, milePost.cubeCoordinate)
                && Objects.equal(terrainType, milePost.terrainType)
                && Objects.equal(country, milePost.country);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cubeCoordinate, terrainType, country);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cubeCoordinate", cubeCoordinate)
                .add("terrainType", terrainType)
                .add("country", country)
                .toString();
    }

    public static Builder builder(HexagonCubeCoordinate coordinate) {
        return new Builder(coordinate);
    }

    @Override
    public int compareTo(MilePost o) {
        return ComparisonChain.start()
                .compare(cubeCoordinate, o.cubeCoordinate)
                .result();
    }

    public static final class Builder {

        private HexagonCubeCoordinate cubeCoordinate;
        private Optional<City> cityMilePost = Optional.absent();
        private Optional<BasicMilePost> milePost = Optional.absent();
        private Country country;
        private TerrainType terrainType;
        private int radius = 15;

        private Builder(HexagonCubeCoordinate cubeCoordinate) {
            this.cubeCoordinate = cubeCoordinate;
        }

        public Builder cityMilePost(City val) {
            cityMilePost = Optional.fromNullable(val);
            return this;
        }

        public Builder milePost(BasicMilePost val) {
            milePost = Optional.fromNullable(val);
            return this;
        }

        public Builder country(Country val) {
            country = val;
            return this;
        }

        public Builder terrainType(TerrainType val) {
            terrainType = val;
            return this;
        }

        public Builder radius(int val) {
            radius = val;
            return this;
        }

        public MilePost build() {
            return new MilePost(this);
        }
    }
}

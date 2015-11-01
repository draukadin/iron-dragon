package com.pphi.iron.dragon.board;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.OceanMilePost;
import com.pphi.iron.dragon.component.PortMilePost;
import org.pphi.hexagon.coordinates.HexagonCubeCoordinate;

public class MilePost {

    private HexagonCubeCoordinate cubeCoordinate;
    private Optional<PortMilePost> portMilePost;
    private Optional<City> cityMilePost;
    private Optional<OceanMilePost> oceanMilePost;

    public MilePost(Builder builder) {
        cubeCoordinate = builder.cubeCoordinate;
        portMilePost = builder.portMilePost;
        cityMilePost = builder.cityMilePost;
        oceanMilePost = builder.oceanMilePost;
    }

    public HexagonCubeCoordinate getCubeCoordinate() {
        return cubeCoordinate;
    }

    public Optional<PortMilePost> getPortMilePost() {
        return portMilePost;
    }

    public Optional<City> getCityMilePost() {
        return cityMilePost;
    }

    public Optional<OceanMilePost> getOceanMilePost() {
        return oceanMilePost;
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
        return Objects.equal(cubeCoordinate, milePost.cubeCoordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cubeCoordinate);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cubeCoordinate", cubeCoordinate)
                .toString();
    }

    public static Builder builder(HexagonCubeCoordinate coordinate) {
        return new Builder(coordinate);
    }

    public static final class Builder {

        private HexagonCubeCoordinate cubeCoordinate;
        private Optional<PortMilePost> portMilePost = Optional.absent();
        private Optional<City> cityMilePost = Optional.absent();
        private Optional<OceanMilePost> oceanMilePost = Optional.absent();

        private Builder(HexagonCubeCoordinate cubeCoordinate) {
            this.cubeCoordinate = cubeCoordinate;
        }

        public Builder portMilePost(PortMilePost val) {
            portMilePost = Optional.fromNullable(val);
            return this;
        }

        public Builder cityMilePost(City val) {
            cityMilePost = Optional.fromNullable(val);
            return this;
        }

        public Builder oceanMilePost(OceanMilePost val) {
            oceanMilePost = Optional.fromNullable(val);
            return this;
        }

        public MilePost build() {
            return new MilePost(this);
        }
    }
}

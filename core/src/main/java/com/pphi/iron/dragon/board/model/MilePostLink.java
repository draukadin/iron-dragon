package com.pphi.iron.dragon.board.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ComparisonChain;

public class MilePostLink implements Comparable<MilePostLink> {

    private String uuid;
    private boolean border;
    private boolean inlet;
    private boolean river;

    @JsonCreator
    public MilePostLink(
            @JsonProperty("uuid") String uuid,
            @JsonProperty("border") boolean border,
            @JsonProperty("inlet") boolean inlet,
            @JsonProperty("river") boolean river) {
        this.uuid = uuid;
        this.border = border;
        this.inlet = inlet;
        this.river = river;
    }

    private MilePostLink(Builder builder) {
        uuid = builder.uuid;
        border = builder.border;
        inlet = builder.inlet;
        river = builder.river;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean isBorder() {
        return border;
    }

    public boolean isInlet() {
        return inlet;
    }

    public boolean isRiver() {
        return river;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String uuid;
        private boolean border = false;
        private boolean inlet = false;
        private boolean river = false;

        private Builder() {
            uuid = UUID.randomUUID().toString();
        }

        public Builder border(boolean val) {
            border = val;
            return this;
        }

        public Builder inlet(boolean val) {
            inlet = val;
            return this;
        }

        public Builder river(boolean val) {
            river = val;
            return this;
        }

        public Optional<MilePostLink> build() {
            return Optional.of(new MilePostLink(this));
        }
    }
    @Override
    public int compareTo(MilePostLink o) {
        return ComparisonChain
                .start()
                .compare(uuid, o.uuid)
                .compare(border, o.border)
                .compare(inlet, o.inlet)
                .compare(river, o.river)
                .result();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MilePostLink that = (MilePostLink) o;

        return Objects.equal(this.uuid, that.uuid)
                && Objects.equal(this.border, that.border)
                && Objects.equal(this.inlet, that.inlet)
                && Objects.equal(this.river, that.river);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid, border, inlet, river);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uuid", uuid)
                .add("border", border)
                .add("inlet", inlet)
                .add("river", river)
                .toString();
    }
}
